package com.project.stock_exchange.dao;

import com.project.stock_exchange.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDAO extends JpaRepository<User, Integer>
{
    User findByUsername(String username);
}
