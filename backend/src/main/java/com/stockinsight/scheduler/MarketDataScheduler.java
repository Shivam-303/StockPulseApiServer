package com.stockinsight.scheduler;

import com.stockinsight.service.MostActiveStockService;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.TimeZone;

@Component
public class MarketDataScheduler {

    private final MostActiveStockService service;

    public MarketDataScheduler(MostActiveStockService service) {
        this.service = service;
    }

//    @PostConstruct
//    public void testSchedulerOnStartup() { //only for testing purposes
//        fetchMorningData();
//    }

    @PostConstruct
    public void setIST() {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Asia/Kolkata")));
    }

    @Scheduled(cron = "0 15 9 * * MON-FRI")
    public void fetchMorningData() {
        try {
            service.updateMostActive("NSE");
            service.updateMostActive("BSE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 30 15 * * MON-FRI")
    public void fetchEveningData() {
        try {
            service.updateMostActive("NSE");
            service.updateMostActive("BSE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
