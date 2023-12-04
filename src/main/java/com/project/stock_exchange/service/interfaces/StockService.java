package com.project.stock_exchange.service.interfaces;

import com.project.stock_exchange.entity.Stock;

import java.util.List;

public interface StockService
{
    List<Stock> findContainingName(String keyword);
    Stock findById(int stockId);
}
