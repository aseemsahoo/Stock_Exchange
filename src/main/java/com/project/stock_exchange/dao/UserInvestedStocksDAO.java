package com.project.stock_exchange.dao;

import com.project.stock_exchange.entity.User;
import com.project.stock_exchange.entity.UserInvestedStocks;
import com.project.stock_exchange.entity.UserInvestedStocksID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserInvestedStocksDAO extends JpaRepository<UserInvestedStocks, UserInvestedStocksID>
{
    @Query("SELECT " +
            "new UserInvestedStocks(u.id, u.stockId, u.quantity, u.totalPrice) " +
            "FROM UserInvestedStocks u " +
            "WHERE u.id = ?1 AND u.stockId = ?2")
    UserInvestedStocks getUserInvestedStock(int userId, int stockId);
    List<UserInvestedStocks> findById(int id);
}
