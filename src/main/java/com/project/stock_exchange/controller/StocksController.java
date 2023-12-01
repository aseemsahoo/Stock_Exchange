package com.project.stock_exchange.controller;

import com.project.stock_exchange.entity.*;
import com.project.stock_exchange.entity.ApiAccess.StockPriceApiAccess;
import com.project.stock_exchange.entity.DTO.UserInvestedStocksDTO;
import com.project.stock_exchange.entity.singleton.SessionID;
import com.project.stock_exchange.service.Interfaces.StockService;
import com.project.stock_exchange.service.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
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

    @PostMapping("/buy")
    // TO-DO nomenclature change, DTO classes should not be returned?? maybe
    public UserInvestedStocksDTO buy_stocks(@RequestBody Map<String, String> requestBody)
    {
        Integer buy_quantity = Integer.parseInt(requestBody.get("buy_quantity"));
        Integer stockId = Integer.parseInt(requestBody.get("stockId"));
        String stockSymbol = requestBody.get("symbol");

//        User user = sessionID.getUser();
        User user = userService.getAccountDetails("aseemsahoo");

        BigDecimal balance = user.getBalance();

        String url = "https://financialmodelingprep.com/api/v3/quote-short/{symbol}?apikey={apiKey}";

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("symbol", stockSymbol);
        uriVariables.put("apiKey", apiKey);

        // get the latest stock price
        try{
            RestTemplate restTemplate = new RestTemplate();
            StockPriceApiAccess[] stockPriceData = restTemplate.getForObject(url, StockPriceApiAccess[].class, uriVariables);
            chartArray = Arrays.asList(stockPriceData);
        }
        catch(Exception ex){
            throw ex;
        }

        BigDecimal stockPrice = chartArray.get(0).getPrice();
        BigDecimal buyPrice = stockPrice.multiply(BigDecimal.valueOf(buy_quantity));

        // TEMPORARY, NEED GOOD VALIDATION IN FRONTEND
        if(buyPrice.compareTo(balance) == 1){
            buy_quantity = balance.divide(stockPrice).intValue() - 1;
            buyPrice = stockPrice.multiply(BigDecimal.valueOf(buy_quantity));
        }
        if(buy_quantity < 1){
            return null;
        }
        UserInvestedStocksDTO currStockData = userService.getUserInvestedStock(user.getId(), stockId);
        if(currStockData == null){
            currStockData = new UserInvestedStocksDTO(user.getId(), stockId, 0, new BigDecimal(String.valueOf(0)));
        }
        BigInteger curr_quantity = BigInteger.valueOf(currStockData.getQuantity());

        currStockData.setQuantity(curr_quantity.add(BigInteger.valueOf(buy_quantity)).intValue());
        currStockData.setTotalPrice(buyPrice.add(currStockData.getTotalPrice()));
        userService.updateUserStockData(currStockData);

        user.setBalance(user.getBalance().subtract(buyPrice));
        user.setInvested(user.getInvested().add(buyPrice));
        userService.updateUserBalance(user);

        return currStockData;
    }

    @PostMapping("/sell")
    public UserInvestedStocksDTO sell_stocks(@RequestBody Map<String, String> requestBody)
    {
        Integer sell_quantity = Integer.parseInt(requestBody.get("sell_quantity"));
        Integer stockId = Integer.parseInt(requestBody.get("stockId"));
        String stockSymbol = requestBody.get("symbol");

        User user = userService.getAccountDetails("aseemsahoo");

        BigDecimal balance = user.getBalance();

        String url = "https://financialmodelingprep.com/api/v3/quote-short/{symbol}?apikey={apiKey}";

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("symbol", stockSymbol);
        uriVariables.put("apiKey", apiKey);

        // get the latest stock price
        try{
            RestTemplate restTemplate = new RestTemplate();
            StockPriceApiAccess[] stockPriceData = restTemplate.getForObject(url, StockPriceApiAccess[].class, uriVariables);
            chartArray = Arrays.asList(stockPriceData);
        }
        catch(Exception ex){
            throw ex;
        }

        UserInvestedStocksDTO currStockData = userService.getUserInvestedStock(user.getId(), stockId);
        if(currStockData == null){
            return null;
        }
        int curr_quantity = currStockData.getQuantity();

        // TEMPORARY
        if(sell_quantity > curr_quantity){
            sell_quantity = curr_quantity;
        }

        BigDecimal stockPrice = chartArray.get(0).getPrice();
//        BigDecimal buyPrice = (currStockData.getTotalPrice().multiply(BigDecimal.valueOf(sell_quantity))).divide(BigDecimal.valueOf(curr_quantity));
        BigDecimal sellPrice = stockPrice.multiply(BigDecimal.valueOf(sell_quantity));

        currStockData.setQuantity(currStockData.getQuantity() - sell_quantity);
        currStockData.setTotalPrice(currStockData.getTotalPrice().subtract(sellPrice));
        if(currStockData.getQuantity() == 0){
            userService.deleteUserStockData(currStockData);
        }
        else{
            userService.updateUserStockData(currStockData);
        }

        user.setBalance(user.getBalance().add(sellPrice));
        user.setInvested(user.getInvested().subtract(sellPrice));
        userService.updateUserBalance(user);

        return currStockData;
    }
}