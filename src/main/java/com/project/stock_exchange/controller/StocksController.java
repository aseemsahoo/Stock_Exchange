package com.project.stock_exchange.controller;

import com.project.stock_exchange.entity.*;
import com.project.stock_exchange.service.StockService;
import com.project.stock_exchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Controller
@RequestMapping("/stocks")
public class StocksController
{
    @Value("${apiKey}")
    private String apiKey;
    private final StockService stockService;
    private final UserService userService;
    private SessionID sessionID;
    private List<StockPriceApiAccess> chartArray;

    @Autowired
    public StocksController(StockService stockService, UserService userService, SessionID sessionID, List<StockPriceApiAccess> chartArray) {
        this.stockService = stockService;
        this.userService = userService;
        this.sessionID = sessionID;
        this.chartArray = chartArray;
    }

    @GetMapping("/showCharts")
    public String show_charts(@RequestParam("stockId") int stock_id, Model theModel)
    {
        Stock curr_stock = stockService.findById(stock_id);
        theModel.addAttribute("stock", curr_stock);

        // make api key in applications.properties (FUTURE)
        String url = "https://financialmodelingprep.com/api/v3/historical-chart/15min/{symbol}?apikey={apiKey}";

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("symbol", curr_stock.getSymbol());
        uriVariables.put("apiKey", apiKey);

        try
        {
            RestTemplate restTemplate = new RestTemplate();
            StockPriceApiAccess[] chartData = restTemplate.getForObject(url, StockPriceApiAccess[].class, uriVariables);
            chartArray = Arrays.asList(chartData);
        }
        catch(Exception ex)
        {
            throw ex;
        }
        Map<String, Double> surveyMap = new LinkedHashMap<>();
        for(StockPriceApiAccess item : chartArray)
        {
            surveyMap.put(item.getDate(), item.getClose());
        }
        theModel.addAttribute("surveyMap", surveyMap);
        return "stocks/list-stock-chart";
    }

    @PostMapping("/buy")
    public String buy_stocks(@RequestParam Integer buy_quantity,
                             @RequestParam("id") Integer stockId,  Model theModel)
    {
        User user = sessionID.getUser();

        double balance = user.getBalance();
        double stockPrice = chartArray.get(0).getClose();
        double buyPrice = buy_quantity * stockPrice;

        if(buyPrice > balance)
        {
            // TEMPORARY
            buy_quantity = (int)(Math.floor(balance / Math.ceil(stockPrice)));
            buyPrice = buy_quantity * stockPrice;
        }
        if(buy_quantity < 1)
        {
            return "stocks/list-stocks";
        }
        UserInvestedStocks currStockData = userService.getUserInvestedStocks(user.getId(), stockId);
        if(currStockData == null)
        {
            currStockData = new UserInvestedStocks(user.getId(), stockId, 0, 0.00);
        }
        currStockData.setQuantity(buy_quantity + currStockData.getQuantity());
        currStockData.setTotalPrice(buyPrice + currStockData.getTotalPrice());
        userService.updateUserStockData(currStockData);

        user.setBalance(user.getBalance() - buyPrice);
        user.setInvested(buyPrice + user.getInvested());
        userService.updateUserBalance(user);
        return "stocks/list-stocks";
    }

    @PostMapping("/sell")
    public String sell_stocks(@RequestParam Integer sell_quantity,
                              @RequestParam("id") Integer stockId,  Model theModel)
    {
        User user = sessionID.getUser();

        UserInvestedStocks currStockData = userService.getUserInvestedStocks(user.getId(), stockId);
        if(currStockData == null)
        {
            currStockData = new UserInvestedStocks(user.getId(), stockId, 0, 0.00);
        }
        int curr_quantity = currStockData.getQuantity();
        if(sell_quantity > curr_quantity)
        {
            // TEMPORARY
            sell_quantity = curr_quantity;
        }
        double stockPrice = chartArray.get(0).getClose();
        double sellPrice = sell_quantity * stockPrice;

        currStockData.setQuantity(currStockData.getQuantity() - sell_quantity);
        currStockData.setTotalPrice(currStockData.getTotalPrice() - sellPrice);
        userService.updateUserStockData(currStockData);

        user.setBalance(user.getBalance() + sellPrice);
        user.setInvested(user.getInvested() - sellPrice);
        userService.updateUserBalance(user);

        return "stocks/list-stocks";
    }
}