package com.stockinsight.model;
import lombok.Data;
//import org.springframework.stereotype.Service;

//This defines the response we’ll return from our API.

@Data
public class Stock {

    private String name;
    private double price;
    private double change;
    private double changePercent;
    private long marketCap;

//    public String getName() {
//        return name;
//    }
//    public void setName(String name)
//    {
//        this.name  = name;
//    }  getter and setter methods are not needed because we are using Lombok @Data annotation.


//     we are using private because we dont want any other class to access these fields directly and manipulate them.

}
