//package com.stockinsight.service;
//
//import com.stockinsight.model.Stock;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//@Service
//public class StockService {
//
//    @Value("${rapidapi.key}")
//    private String rapidApiKey;
//
//    @Value("${rapidapi.host}")
//    private String rapidApiHost;
//
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    public Stock fetchStock(String symbol) {
//        String url = "https://" + rapidApiHost + "/market/get-quotes?symbols=" + symbol + "&region=IN";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("X-RapidAPI-Key", rapidApiKey);
//        headers.set("X-RapidAPI-Host", rapidApiHost);
//
//        HttpEntity<Void> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//        JSONObject json = new JSONObject(response.getBody());
//
//        JSONArray resultArray = json.getJSONObject("quoteResponse").getJSONArray("result");
//        if (resultArray.isEmpty()) {
//            throw new RuntimeException("No data found for symbol: " + symbol);
//        }
//
//        JSONObject stockData = resultArray.getJSONObject(0);
//
//        Stock stock = new Stock();
//        stock.setName(stockData.optString("shortName", symbol));
//        stock.setPrice(stockData.optDouble("regularMarketPrice", 0));
//        stock.setChange(stockData.optDouble("regularMarketChange", 0));
//        stock.setChangePercent(stockData.optDouble("regularMarketChangePercent", 0));
//        stock.setMarketCap(stockData.optLong("marketCap", 0));
//
//        return stock;
//    }
//}
