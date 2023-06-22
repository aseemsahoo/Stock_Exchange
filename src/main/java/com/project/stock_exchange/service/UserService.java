package com.project.stock_exchange.service;

import com.project.stock_exchange.entity.User;
import com.project.stock_exchange.entity.UserInvestedStocks;

import java.util.List;

public interface UserService
{
    User getAccountDetails(String username);
    UserInvestedStocks getUserInvestedStock(int userId, int stockId);
    List<UserInvestedStocks> getAlluserInvestedStocks(int userId);
    void updateUserBalance(User user);
    void updateUserStockData(UserInvestedStocks currStockData);
    void deleteUserStockData(UserInvestedStocks currStockData);
}
