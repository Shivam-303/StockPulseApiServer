package com.stockinsight.model;

import lombok.Data;

@Data
public class StockMetaData {
    private String symbol;
    private String name;
    private String sector;
    private long marketCap;
    private String capSize;
}

