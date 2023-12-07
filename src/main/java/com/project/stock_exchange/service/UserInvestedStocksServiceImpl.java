package com.project.stock_exchange.service;

import com.project.stock_exchange.dao.UserInvestedStocksDAO;
import com.project.stock_exchange.entity.dto.UserInvestedStocksDTO;
import com.project.stock_exchange.service.interfaces.UserInvestedStocksService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInvestedStocksServiceImpl implements UserInvestedStocksService {
    private UserInvestedStocksDAO userInvestedStocksDAO;
    public UserInvestedStocksServiceImpl() {};
    @Autowired
    public UserInvestedStocksServiceImpl(UserInvestedStocksDAO userInvestedStocksDAO){
        this.userInvestedStocksDAO = userInvestedStocksDAO;
    }

    @Override
    @Transactional
    public UserInvestedStocksDTO getUserInvestedStock(int userId, int stockId)
    {
        try
        {
            return userInvestedStocksDAO.getUserInvestedStock(userId, stockId);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional
    public List<UserInvestedStocksDTO> getAllUserInvestedStocks(int userId)
    {
        try
        {
            return userInvestedStocksDAO.findById(userId);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional
    public void updateUserStockData(UserInvestedStocksDTO currStockData)
    {
        try
        {
            userInvestedStocksDAO.save(currStockData);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public void deleteUserStockData(UserInvestedStocksDTO currStockData)
    {
        try
        {
            userInvestedStocksDAO.delete(currStockData);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
}
