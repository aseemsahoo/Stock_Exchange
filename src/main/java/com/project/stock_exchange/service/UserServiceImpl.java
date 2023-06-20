package com.project.stock_exchange.service;

import com.project.stock_exchange.dao.UserDAO;
import com.project.stock_exchange.dao.UserInvestedStocksDAO;
import com.project.stock_exchange.entity.User;
import com.project.stock_exchange.entity.UserInvestedStocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements  UserService
{
    private UserDAO userDAO;
    private UserInvestedStocksDAO userInvestedStocksDAO;
    public UserServiceImpl () {};

    @Autowired
    public UserServiceImpl(UserDAO userDAO, UserInvestedStocksDAO userInvestedStocksDAO)
    {
        this.userDAO = userDAO;
        this.userInvestedStocksDAO = userInvestedStocksDAO;
    }

    @Override
    public int getUserID(String username)
    {
        try
        {
            return userDAO.getUserID(username);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public User getAccountDetails(int userId)
    {
        try
        {
            return userDAO.getAccountDetails(userId);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public UserInvestedStocks getUserInvestedStocks(int userId, int stockId)
    {
        try
        {
            return userInvestedStocksDAO.getUserInvestedStocks(userId, stockId);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public void updateUserBalance(User user)
    {
        try
        {
            userDAO.save(user);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public void updateUserStockData(UserInvestedStocks currStockData)
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
}
