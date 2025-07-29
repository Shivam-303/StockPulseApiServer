package com.stockinsight.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity// when we declare entity a table is created in Db
@Data
public class MostActiveStock {

    @Id
    private String id;


    private String symbol;
    private String name;
    private String exchange;
    private double price;
    private double change;
    private double changePercent;
    private long volume;

}
