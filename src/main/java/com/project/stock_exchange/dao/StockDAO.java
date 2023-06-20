package com.project.stock_exchange.dao;

import com.project.stock_exchange.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockDAO extends JpaRepository<Stock, Integer>
{
    @Query("SELECT s FROM Stock s WHERE " +
            "CONCAT(s.id, s.name, s.symbol, s.sector) " +
            "LIKE %?1%")
    List<Stock> findByName(String keyword);

    @Query("SELECT s FROM Stock s WHERE " +
            "s.id = ?1")
    Stock findByStockID(int stockId);
}
