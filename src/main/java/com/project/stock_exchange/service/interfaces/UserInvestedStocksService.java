package com.project.stock_exchange.service.interfaces;

import com.project.stock_exchange.entity.dto.UserInvestedStocksDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInvestedStocksService {
    public UserInvestedStocksDTO getUserInvestedStock(int userId, int stockId);
    public List<UserInvestedStocksDTO> getAllUserInvestedStocks(int userId);
    public void updateUserStockData(UserInvestedStocksDTO currStockData);
    public void deleteUserStockData(UserInvestedStocksDTO currStockData);

}
