package com.project.stock_exchange.service;

import com.project.stock_exchange.entity.Stock;

import java.util.List;
import java.util.Optional;

public interface StockService
{
    List<Stock> find_all_stocks();

    List<Stock> findByName(String keyword);

    Stock findById(int stockId);
}
