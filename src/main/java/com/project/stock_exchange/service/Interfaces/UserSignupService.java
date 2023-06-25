package com.project.stock_exchange.service;

import com.project.stock_exchange.entity.DTO.UserSignupDTO;

public interface UserSignupService
{
    void saveUser(UserSignupDTO userSignup);
    UserSignupDTO getUser(String username, String password);
}
