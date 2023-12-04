package com.project.stock_exchange.controller;

import com.project.stock_exchange.entity.Stock;
import com.project.stock_exchange.service.interfaces.StockService;
import com.project.stock_exchange.service.interfaces.UserService;
import com.project.stock_exchange.util.exception.RestException;
//import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/dashboard")
public class HomeController
{
    private List<Stock> stock_list;
    private final StockService stockService;
    private final UserService userService;

    public HomeController(StockService stockService, UserService userService) {
        this.stockService = stockService;
        this.userService = userService;
    }

    @GetMapping("/list")
    public List<Stock> getStocksList(@RequestParam("keyword") Optional<String> keyword)
    {
        // TO-DO -> the keyword search should be done in frontend
        String paramValue = keyword.orElse("");
        stock_list = stockService.findContainingName(paramValue);

        return stock_list;
    }

    @GetMapping("/stock/{id}")
    public ResponseEntity<?> getStockData (@PathVariable("id") Integer id) throws Exception
    {
        try{
            Stock stock = stockService.findById(id);
            return ResponseEntity.ok(stock);
        }
        catch(NoSuchElementException ex){
            throw ex;
        }
    }
}