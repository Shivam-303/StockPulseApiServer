//package com.stockinsight.service;
//
//import com.stockinsight.model.Stock;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class InsightService {
//
//    @Value("${openrouter.api.key}")
//    private String apiKey;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    public String getInsight(Stock stock, String newsSummary) {
////        stock = stock + ".NS";
//
//        //remove if conditions for debugging only
//
//        if (stock == null) {
//            return "Error: Stock data is missing.";
//        }
//        if (newsSummary == null || newsSummary.trim().isEmpty()) {
//            return "Error: News summary is missing.";
//        }
//
//
//        String prompt = "Stock: " + stock.getName() +
//                ", Price: " + stock.getPrice() +
//                ", Change: " + stock.getChange() +
//                ", % Change: " + stock.getChangePercent() +
//                ", Market Cap: " + stock.getMarketCap() +
//                ".\nRecent news: " + newsSummary +
//                "\n\nBased on this, give a sharp, 20-35 word summary on whether it's a good time to buy/sell/hold.";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer " + apiKey);
//
//        JSONObject payload = new JSONObject();
//        payload.put("model", "mistralai/mistral-small-3.2-24b-instruct:free"); // or another OpenRouter-supported model
//
//        JSONArray messages = new JSONArray();
//        messages.put(new JSONObject().put("role", "user").put("content", prompt));
//        payload.put("messages", messages);
//
//        HttpEntity<String> request = new HttpEntity<>(payload.toString(), headers);
//
//        try {
//            ResponseEntity<String> response = restTemplate.postForEntity(
//                    "https://openrouter.ai/api/v1/chat/completions",
//                    request,
//                    String.class
//            );
//
//            JSONObject responseBody = new JSONObject(response.getBody());
//            return responseBody
//                    .getJSONArray("choices")
//                    .getJSONObject(0)
//                    .getJSONObject("message")
//                    .getString("content");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Insight generation failed.";
//        }
//    }
//}
//
package com.stockinsight.service;

import com.stockinsight.model.Stock;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InsightService {

    @Value("${openrouter.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public String getInsight(Stock stock, String newsSummary) {

        if (stock == null) {
            return "Error: Stock data is missing.";
        }

        if (newsSummary == null || newsSummary.trim().isEmpty()) {
            return "Error: News summary is missing.";
        }

        // Prepare the content for the prompt
        String promptText = "Stock: " + stock.getName() +
                ", Price: " + stock.getPrice() +
                ", Change: " + stock.getChange() +
                ", % Change: " + stock.getChangePercent() +
                ", Market Cap: " + stock.getMarketCap() +
                ".\nRecent news: " + newsSummary +
                "\n\n Based on this, first give me the reason by quantifying the reason in numbers and then give a sharp, 20-35 word summary on whether it's a good time to buy/sell/hold.";

        // Build the JSON payload as per OpenRouter's format for Gemma
        JSONObject payload = new JSONObject();
        payload.put("model", "google/gemma-3-4b-it:free");

        // Wrap prompt text inside content array with type "text"
        JSONArray contentArray = new JSONArray();
        JSONObject textObj = new JSONObject();
        textObj.put("type", "text");
        textObj.put("text", promptText);
        contentArray.put(textObj);

        // Build the message
        JSONArray messagesArray = new JSONArray();
        JSONObject messageObj = new JSONObject();
        messageObj.put("role", "user");
        messageObj.put("content", contentArray); // Use array of typed content
        messagesArray.put(messageObj);

        payload.put("messages", messagesArray);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("HTTP-Referer", "http://localhost:3000"); // optional but recommended
        headers.set("X-Title", "StockInsight App");            // optional

        HttpEntity<String> request = new HttpEntity<>(payload.toString(), headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://openrouter.ai/api/v1/chat/completions",
                    request,
                    String.class
            );

            JSONObject responseBody = new JSONObject(response.getBody());

            return responseBody
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

        } catch (Exception e) {
            e.printStackTrace();
            return "Insight generation failed.";
        }
    }
}
