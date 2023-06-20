package com.project.stock_exchange.service;

import com.project.stock_exchange.entity.User;
import com.project.stock_exchange.entity.UserInvestedStocks;

import java.util.List;

public interface UserService
{
    int getUserID(String username);

    User getAccountDetails(int userId);
    UserInvestedStocks getUserInvestedStocks(int userId, int stockId);

    void updateUserBalance(User user);

    void updateUserStockData(UserInvestedStocks currStockData);
}
