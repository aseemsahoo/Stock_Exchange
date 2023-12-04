package com.project.stock_exchange.service;

import com.project.stock_exchange.dao.UserSignupDAO;
import com.project.stock_exchange.entity.User;
//import com.project.stock_exchange.entity.dto.UserSignupDTO;
import com.project.stock_exchange.service.interfaces.UserSignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserSignupServiceImpl implements UserSignupService
{
    private UserSignupDAO userSignupDAO;

    public UserSignupServiceImpl () {}

    @Autowired
    public UserSignupServiceImpl(UserSignupDAO userSignupDAO) {
        this.userSignupDAO = userSignupDAO;
    }

    @Override
    public void saveUser(User userSignup) {
        try
        {
            userSignupDAO.save(userSignup);
        }
        catch(DataIntegrityViolationException ex)
        {
            throw ex;
        }
    }

    @Override
    public User getUser(String username, String password)
    {
        try
        {
            return userSignupDAO.getUser(username, password);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public User findByUsername(String username)
    {
        try
        {
            User user =  userSignupDAO.findByUsername(username);
            return user;
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
}
