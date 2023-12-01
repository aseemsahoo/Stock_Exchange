package com.project.stock_exchange.controller;

import com.project.stock_exchange.entity.singleton.SessionID;
import com.project.stock_exchange.entity.Stock;
import com.project.stock_exchange.service.Interfaces.StockService;
import com.project.stock_exchange.service.Interfaces.UserService;
import com.project.stock_exchange.util.ApiException;
import org.springframework.data.repository.query.Param;
//import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import org.springframework.stereotype.Controller;

import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/dashboard")
public class HomeController
{
    private List<Stock> stock_list;
    private final StockService stockService;
    private final UserService userService;
    private SessionID sessionID;

    public HomeController(StockService stockService, UserService userService, SessionID sessionID) {
        this.stockService = stockService;
        this.userService = userService;
        this.sessionID = sessionID;
    }

//    private void setUser(Authentication auth)
//    {
//        String username = auth.getName();
//        User user = userService.getAccountDetails(username);
////        int userId = userService.getUserID(username);
////        User user = userService.getAccountDetails(userId);
//        sessionID.setUser(user);
//    }

    @GetMapping("/list")
    public List<Stock> getStocksList(@RequestParam("keyword") Optional<String> keyword) //, Authentication auth)
    {
        // TO-DO -> the keyword search should be done in frontend
        String paramValue = keyword.orElse("");
        stock_list = stockService.findContainingName(paramValue);

//        if(sessionID.getUser() == null)
//        {
//            setUser(auth);
//        }
        return stock_list;
    }

    @GetMapping("/stock/{id}")
    public ResponseEntity<?> getStockData (@PathVariable("id") Integer id) throws Exception  //, Authentication auth)
    {
        // TO-DO : ERROR HANDLING --> DONE ???
        try{
            Stock stock = stockService.findById(id);
            return ResponseEntity.ok(stock);
        }
        catch(Exception ex){
            ApiException errorResponse = new ApiException(HttpStatus.NOT_FOUND, ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

//        TO-DO : This local cached user-thing
//        if(sessionID.getUser() == null)
//        {
//            setUser(auth);
//        }
//        return stock;
    }
}