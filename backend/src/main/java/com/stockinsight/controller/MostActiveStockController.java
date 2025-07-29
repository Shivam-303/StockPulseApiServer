package com.stockinsight.controller;

import com.stockinsight.model.MostActiveStock;
import com.stockinsight.service.MostActiveStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/most-active")
public class MostActiveStockController {

    private final MostActiveStockService service;

    public MostActiveStockController(MostActiveStockService service) {
        this.service = service;
    }
//    @PostMapping("/refresh/{exchange}")//testing only
//    public ResponseEntity<?> manualUpdate(@PathVariable String exchange) {
//        service.updateMostActive(exchange.toUpperCase());
//        return ResponseEntity.ok("Updated " + exchange);
//    }

    @GetMapping("/{exchange}")
    public List<MostActiveStock> getMostActive(@PathVariable String exchange) {
        return service.getStocksByExchange(exchange.toUpperCase());
    }

    // ✅ Unified endpoint for front page -- personal endpoint of the project
    @GetMapping("/all")
    public Map<String, List<MostActiveStock>> getAllMostActive() {
        return service.getAllMostActive();
    }
}
