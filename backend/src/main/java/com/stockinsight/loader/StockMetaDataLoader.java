package com.stockinsight.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockinsight.model.StockMetaData;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

//import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class StockMetaDataLoader {

    private List<StockMetaData> stockList = new ArrayList<>();

    @PostConstruct //happens when application starts' up
    public void loadStockData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream("stocks.json");
            if (inputStream == null) {
                throw new RuntimeException("Could not find stocks.json in resources");
            }

            //here the  stocks.json is loaded into stockList member variabe and stays in memory
            stockList = mapper.readValue(inputStream, new TypeReference<List<StockMetaData>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to load stock data", e);
        }
    }

    public List<StockMetaData> getAllStocks() {
        return stockList;
    }

    public List<StockMetaData> filterBySectorAndCap(String sector, List<String> capSizes) {
        return stockList.stream()
                .filter(s -> s.getSector().equalsIgnoreCase(sector))
                .filter(s -> capSizes.contains(s.getCapSize()))
                .toList();
    }
}
