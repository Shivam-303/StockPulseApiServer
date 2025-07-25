package com.stockinsight.controller;

import com.stockinsight.model.Stock;
import com.stockinsight.service.InsightService;
import com.stockinsight.service.NewsService;
import com.stockinsight.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;

@RestController
@RequestMapping("api/insights")
public class InsightController {

    @Autowired
    private InsightService insightService;

    @Autowired
    private StockService stockService;

    @Autowired
    private NewsService newsService;

    @GetMapping("/{symbol}")
    public ResponseEntity<String> getStockInsight(@PathVariable String symbol) {

        try{
            // 1. Fetch stock data
//            String symbol1 = symbol + ".NS";
            Stock stock = stockService.fetchStock(symbol);

//             2. Get company name and clean it
            String queryName = stock.getName().replace(" Ltd", "").trim(); // e.g. "Infosys Ltd" → "Infosys"

            // 3. Fetch related news summary
            String newsSummay = newsService.fetchCompanyNews(symbol);

            // 3. Generate insight using OpenRouter
            String insight = insightService.getInsight(stock, newsSummay);

            return ResponseEntity.ok(insight);
        }

        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to generate insight for symbol: " + symbol + " Error: " + e.getMessage());
        }
    }

}
