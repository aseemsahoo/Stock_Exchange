package com.project.stock_exchange.service;

import com.project.stock_exchange.dao.UserSignupDAO;
import com.project.stock_exchange.entity.DTO.UserSignupDTO;
import com.project.stock_exchange.service.Interfaces.UserSignupService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void saveUser(UserSignupDTO userSignup) {
        try
        {
            userSignupDAO.save(userSignup);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public UserSignupDTO getUser(String username, String password)
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
}
