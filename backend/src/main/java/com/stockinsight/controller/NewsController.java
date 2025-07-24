//package com.stockinsight.controller;
//
//
//import com.stockinsight.service.NewsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/news")
//public class NewsController {
//
//    @Autowired
//    private NewsService newsService;
//
//    @GetMapping("/{symbol}")
//    public ResponseEntity<String> getNews(@PathVariable String symbol) {
//        String data = newsService.fetchNews(symbol);
//        return ResponseEntity.ok(data);
//    }
//}
package com.stockinsight.controller;

import com.stockinsight.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/newsdata")
public class NewsController {

    @Autowired
    private NewsService service;

    @GetMapping("/{company}")
    public ResponseEntity<String> getNews(@PathVariable String company) {
        return ResponseEntity.ok(service.fetchCompanyNews(company));
    }
}

