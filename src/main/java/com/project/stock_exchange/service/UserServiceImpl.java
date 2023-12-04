package com.project.stock_exchange.service;

import com.project.stock_exchange.dao.UserDAO;
import com.project.stock_exchange.dao.UserInvestedStocksDAO;
import com.project.stock_exchange.entity.User;
import com.project.stock_exchange.entity.dto.UserInvestedStocksDTO;
import com.project.stock_exchange.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    private UserDAO userDAO;
    public UserServiceImpl () {};

    @Autowired
    public UserServiceImpl(UserDAO userDAO)
    {
        this.userDAO = userDAO;
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
}
