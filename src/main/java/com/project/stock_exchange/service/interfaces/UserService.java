package com.project.stock_exchange.service.interfaces;

import com.project.stock_exchange.entity.User;
import com.project.stock_exchange.entity.dto.UserInvestedStocksDTO;

import java.util.List;

public interface UserService
{
    User getAccountDetails(String username);
    void updateUserBalance(User user);
}
