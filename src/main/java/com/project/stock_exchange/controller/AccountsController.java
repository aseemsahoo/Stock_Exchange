package com.project.stock_exchange.controller;

import com.project.stock_exchange.entity.SessionID;
import com.project.stock_exchange.entity.Stock;
import com.project.stock_exchange.entity.User;
import com.project.stock_exchange.service.UserService;
import org.apache.catalina.authenticator.SpnegoAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/accounts")
public class AccountsController
{
    private final UserService userService;

    @Autowired
    private SessionID sessionID;
    public AccountsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String listAccountData(Model theModel)
    {
        int userId = sessionID.getUser().getId();

        User curr_user = userService.getAccountDetails(userId);
        theModel.addAttribute("user", curr_user);

        return "accounts/list-account-data";
    }
}
