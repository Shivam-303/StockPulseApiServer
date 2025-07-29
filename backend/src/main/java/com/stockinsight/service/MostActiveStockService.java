package com.stockinsight.service;

import com.stockinsight.model.MostActiveStock;
import com.stockinsight.repository.MostActiveStockRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class MostActiveStockService {

    private final MostActiveStockRepository repo;
    private final RestTemplate restTemplate;

    @Value("${rapidapi.key}")
    private String rapidApiKey;

    @Value("${rapidapiISEAPI.host}")
    private String rapidApiISEHost;

    @Value("${rapidapi.base-url}")
    private String baseUrl;

    public MostActiveStockService(MostActiveStockRepository repo, RestTemplate restTemplate) {
        this.repo = repo;
        this.restTemplate = restTemplate;
    }
//    Spring Data JPA needs a transaction when you're making changes to the database — including delete, save, update, etc.
//    Your call is happening during @PostConstruct or during the startup via scheduler, and the method is not annotated with @Transactional, so Spring doesn’t know to open a DB transaction.
    @Transactional
    public void updateMostActive(String exchange) {
        String endpoint = exchange.equalsIgnoreCase("NSE") ? "NSE_most_active" : "BSE_most_active";
        String url = baseUrl + endpoint;
        System.out.println("🔗 API URL: " + url); //debug pointer //remove later

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", rapidApiKey);
        headers.set("x-rapidapi-host", rapidApiISEHost);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("Failed to fetch data: " + response.getStatusCode());
        }
        //debug pointer
        System.out.println("✅ API call successful. Response Length: " + response.getBody().length()); //remove later

        JSONArray data = new JSONArray(response.getBody());
        List<MostActiveStock> stocks = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            JSONObject obj = data.getJSONObject(i);

            String symbol = obj.optString("ticker");
            String name = obj.optString("company");
            double price = obj.optDouble("price", 0.0);
            double change = obj.optDouble("net_change", 0.0);
            double changePercent = obj.optDouble("percent_change", 0.0);
            long volume = obj.optLong("volume", 0L);

            if (symbol.isEmpty()) continue;

            MostActiveStock stock = new MostActiveStock();
            stock.setId(symbol + "_" + exchange); // composite key
            stock.setSymbol(symbol);
            stock.setName(name);
            stock.setPrice(price);
            stock.setChange(change);
            stock.setChangePercent(changePercent);
            stock.setVolume(volume); // storing volume here
            stock.setExchange(exchange);

            stocks.add(stock);
        }

        // Clear old data before saving new
        repo.deleteByExchange(exchange);
        repo.saveAll(stocks);
    }

    public List<MostActiveStock> getStocksByExchange(String exchange) {
        return repo.findByExchange(exchange.toUpperCase());
    }

    public Map<String, List<MostActiveStock>> getAllMostActive() {
        Map<String, List<MostActiveStock>> all = new HashMap<>();
        all.put("NSE", getStocksByExchange("NSE"));
        all.put("BSE", getStocksByExchange("BSE"));
        return all;
    }
}
