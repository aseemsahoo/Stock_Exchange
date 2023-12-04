package com.project.stock_exchange.dao;

import com.project.stock_exchange.entity.User;
//import com.project.stock_exchange.entity.dto.UserSignupDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserSignupDAO extends JpaRepository<User, Integer>
{

    @Query("SELECT u FROM User u " +
            "WHERE u.username = ?1 AND u.password = ?2")
    User getUser(String username, String password);

    User findByUsername(String username);
}
