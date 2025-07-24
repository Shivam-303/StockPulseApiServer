package com.stockinsight.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//for scalability we will switch later on to RSS
//Why Use RSS for Stock News?
//        Free (no API key limits or cost)
//        Direct from the source (Moneycontrol, ET, etc.)
//        Stock-focused (you can filter URLs by company)
//        Scalable (with proper caching and queuing)
//🔧 How RSS Works
//        RSS feeds are XML documents that news sites expose publicly. You pull these URLs at intervals (e.g., every 15 min), parse the XML, and extract headlines, links, etc.
@Service
public class NewsService {

    @Value("${newsdata.apikey}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public String fetchCompanyNews(String query) {
        String url = "https://newsdata.io/api/1/news?"
                + "apikey=" + apiKey
                + "&q=" + query
                + "&country=in"
                + "&category=technology"
                + "&language=en";

        ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);
        return resp.getBody();
    }
}
