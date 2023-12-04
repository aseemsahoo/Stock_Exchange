package com.project.stock_exchange.service;

import com.project.stock_exchange.dao.StockDAO;
import com.project.stock_exchange.entity.Stock;
import com.project.stock_exchange.service.interfaces.StockService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

// we use this service class as a delegate for DAO class (remember)
@Service
public class StockServiceImpl implements StockService
{
    private StockDAO stockDAO;

    public StockServiceImpl() {}
    @Autowired
    public StockServiceImpl(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    @Override
    @Transactional
    public List<Stock> findContainingName(String keyword)
    {
        try
        {
            return stockDAO.findContainingName(keyword);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public Stock findById(int stockId)
    {
        try
        {
            return stockDAO.findById(stockId).get();
        }
        catch(NoSuchElementException ex)
        {
            throw ex;
        }
    }
}