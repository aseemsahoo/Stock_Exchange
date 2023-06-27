package com.project.stock_exchange.service.Interfaces;

import com.project.stock_exchange.entity.User;
import com.project.stock_exchange.entity.DTO.UserInvestedStocksDTO;

import java.util.List;

public interface UserService
{
    User getAccountDetails(String username);
    UserInvestedStocksDTO getUserInvestedStock(int userId, int stockId);
    List<UserInvestedStocksDTO> getAlluserInvestedStocks(int userId);
    void updateUserBalance(User user);
    void updateUserStockData(UserInvestedStocksDTO currStockData);
    void deleteUserStockData(UserInvestedStocksDTO currStockData);
}
