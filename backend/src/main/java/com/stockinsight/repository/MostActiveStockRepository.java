package com.stockinsight.repository;

import com.stockinsight.model.MostActiveStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MostActiveStockRepository extends JpaRepository<MostActiveStock, String> {
    List<MostActiveStock> findByExchange(String exchange);
    void deleteByExchange(String exchange);
}
