package com.project.stock_exchange.service.interfaces;

import com.project.stock_exchange.entity.User;
//import com.project.stock_exchange.entity.dto.UserSignupDTO;

public interface UserSignupService
{
    void saveUser(User userSignup);
    User getUser(String username, String password);
    User findByUsername(String username);
}
