package com.project.stock_exchange.controller;

import com.project.stock_exchange.entity.SessionID;
import com.project.stock_exchange.entity.Stock;
import com.project.stock_exchange.service.StockService;
import com.project.stock_exchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//@org.springframework.web.bind.annotation.RestController
@Controller
@RequestMapping("/dashboard")
public class RestController
{
    // injecting values from application.properties
    @Value("${owner.name}")
    private String name;
    @Value("${company.name}")
    private String company;

    private List<Stock> stock_list;

    private final StockService stockService;
    private final UserService userService;

    @Autowired
    private SessionID sessionID;

    public RestController(StockService stockService, UserService userService) {
        this.stockService = stockService;
        this.userService = userService;
    }


    @GetMapping("/")
    public String welcome_display(Model model)
    {
        model.addAttribute("something", "this is coming from controller");
        return "helo";
    }
    @GetMapping("/list")
    public String list_users(Model theModel, @Param("keyword")String keyword, Authentication auth)
    {
        if(keyword == null)
            keyword = "";
        stock_list = stockService.findByName(keyword);

        String username = auth.getName();
        int userId = userService.getUserID(username);
        sessionID.setId(userId);

        theModel.addAttribute("userId", userId);
        theModel.addAttribute("stocks", stock_list);
        theModel.addAttribute("keyword", keyword);
        return "stocks/list-stocks";
    }

    @GetMapping("/showCharts")
    public String show_charts(@RequestParam("stockId") int stock_id,
                              Model theModel)
    {
        Stock curr_stock = stockService.findById(stock_id);
        theModel.addAttribute("stock", curr_stock);
//
//        // TBC
//        return "stocks/list-stock-chart";

        Map<String, Integer> surveyMap = new LinkedHashMap<>();
        surveyMap.put("Java", 40);
        surveyMap.put("Dev oops", 25);
        surveyMap.put("Python", 20);
        surveyMap.put(".Net", 15);
        theModel.addAttribute("surveyMap", surveyMap);
        return "stocks/list-stock-chart";
    }

}