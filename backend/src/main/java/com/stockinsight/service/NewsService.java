//package com.stockinsight.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class NewsService {
//
//    @Value("${marketaux.apiKey}")
//    private String apiKey;
//
//    // RestTemplate is a synchronous client to perform HTTP requests, which can be used to call external APIs.
//    // It's a convenient way to interact with RESTful web services.
//
//    @Autowired
//    private RestTemplate restTemplate;
////    private final RestTemplate restTemplate = new RestTemplate();
//
//    public String fetchNews(String query) {
//        String url = "https://api.marketaux.com/v1/news/all?"
//                + "api_token=" + apiKey
//                + "&language=en"
//                + "&limit=2"
//                + "&filter_entities=true"
//                + "&entities=" + query;
//
//        try {
//            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//            return response.getBody();
//        } catch (Exception e) {
//            return "{\"error\":\"" + e.getMessage() + "\"}";
//        }
//    }
//}
//

package com.stockinsight.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NewsService {

    @Value("${newsdata.apikey}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

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
