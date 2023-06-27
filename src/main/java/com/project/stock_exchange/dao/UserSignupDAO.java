package com.project.stock_exchange.dao;

import com.project.stock_exchange.entity.DTO.UserSignupDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserSignupDAO extends JpaRepository<UserSignupDTO, Integer>
{

    @Query("SELECT u FROM UserSignupDTO u " +
            "WHERE u.username = ?1 AND u.password = ?2")
    UserSignupDTO getUser(String username, String password);
}
