package com.project.stock_exchange.service;

import com.project.stock_exchange.dao.UserDAO;
import com.project.stock_exchange.dao.UserInvestedStocksDAO;
import com.project.stock_exchange.entity.User;
import com.project.stock_exchange.entity.DTO.UserInvestedStocksDTO;
import com.project.stock_exchange.service.Interfaces.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
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
    @Transactional
    public User getAccountDetails(String username)
    {
        try
        {
            return userDAO.findByUsername(username);
        }
        catch(Exception ex)
        {
            throw ex;
        }
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
    public List<UserInvestedStocksDTO> getAlluserInvestedStocks(int userId)
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
