package com.project.stock_exchange.dao;

import com.project.stock_exchange.entity.DTO.UserInvestedStocksDTO;
import com.project.stock_exchange.entity.DTO.UserInvestedStocksID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserInvestedStocksDAO extends JpaRepository<UserInvestedStocksDTO, UserInvestedStocksID>
{
    @Query("SELECT " +
            "new UserInvestedStocksDTO(u.id, u.stockId, u.quantity, u.totalPrice) " +
            "FROM UserInvestedStocksDTO u " +
            "WHERE u.id = ?1 AND u.stockId = ?2")
    UserInvestedStocksDTO getUserInvestedStock(int userId, int stockId);
    List<UserInvestedStocksDTO> findById(int id);
}
