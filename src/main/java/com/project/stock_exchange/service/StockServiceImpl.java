package com.project.stock_exchange.service;

import com.project.stock_exchange.dao.StockDAO;
import com.project.stock_exchange.entity.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public List<Stock> find_all_stocks()
    {
        try
        {
            return stockDAO.findAll();
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public List<Stock> findByName(String keyword)
    {
        try
        {
            return stockDAO.findByName(keyword);
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
            return stockDAO.findByStockID(stockId);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
}