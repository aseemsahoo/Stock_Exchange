package com.project.stock_exchange.dao;

import com.project.stock_exchange.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDAO extends JpaRepository<User, Integer>
{
    @Query("SELECT u.id FROM User u WHERE u.username = ?1")
    int getUserID(String username);

    @Query("SELECT " +
            "new User(u.id, u.firstName, u.lastName, u.username, u.balance, u.invested) " +
            "FROM User u " +
            "WHERE u.id = ?1")
    User getAccountDetails(int userId);
}
