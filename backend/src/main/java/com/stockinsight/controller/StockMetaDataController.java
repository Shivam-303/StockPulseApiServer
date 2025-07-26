package com.stockinsight.controller;

import com.stockinsight.loader.StockMetaDataLoader;
import com.stockinsight.model.StockMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks/meta")
public class StockMetaDataController {

    @Autowired
    private StockMetaDataLoader loader;

    @GetMapping("/all")
    public List<StockMetaData> getAll() {
        return loader.getAllStocks();
    }

    @GetMapping("/filter")
    public List<StockMetaData> filterBySectorAndCap(
            @RequestParam String sector, //
            @RequestParam List<String> caps // Accepts ?caps=Small&caps=Mid
    ) {
        return loader.filterBySectorAndCap(sector, caps);
    }
}
