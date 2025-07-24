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

