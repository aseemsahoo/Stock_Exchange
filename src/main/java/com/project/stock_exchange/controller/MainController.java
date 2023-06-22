package com.project.stock_exchange.controller;

import com.project.stock_exchange.entity.SessionID;
import com.project.stock_exchange.entity.Stock;
import com.project.stock_exchange.entity.User;
import com.project.stock_exchange.service.StockService;
import com.project.stock_exchange.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
@RequestMapping("/dashboard")
public class MainController
{
    @Value("${owner.name}")
    private String name;
    @Value("${company.name}")
    private String company;

    private List<Stock> stock_list;
    private final StockService stockService;
    private final UserService userService;
    private SessionID sessionID;

    public MainController(StockService stockService, UserService userService, SessionID sessionID) {
        this.stockService = stockService;
        this.userService = userService;
        this.sessionID = sessionID;
    }

    private void setUser(Authentication auth)
    {
        String username = auth.getName();
        User user = userService.getAccountDetails(username);
//        int userId = userService.getUserID(username);
//        User user = userService.getAccountDetails(userId);
        sessionID.setUser(user);
    }
    @GetMapping("/list")
    public String list_users(Model theModel, @Param("keyword")String keyword, Authentication auth)
    {
        if(keyword == null)
            keyword = "";
        stock_list = stockService.findContainingName(keyword);

        if(sessionID.getUser() == null)
        {
            setUser(auth);
        }

        theModel.addAttribute("userId", sessionID.getUser().getId());
        theModel.addAttribute("stocks", stock_list);
        theModel.addAttribute("keyword", keyword);
        return "stocks/list-stocks";
    }
}