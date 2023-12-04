package com.project.stock_exchange.controller;

import com.project.stock_exchange.entity.*;
import com.project.stock_exchange.entity.apiAccess.StockPriceApiAccess;
import com.project.stock_exchange.entity.dto.UserInvestedStocksDTO;
import com.project.stock_exchange.service.UserInvestedStocksServiceImpl;
import com.project.stock_exchange.service.interfaces.StockService;
import com.project.stock_exchange.service.interfaces.UserInvestedStocksService;
import com.project.stock_exchange.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/stocks")
public class StocksController
{
    @Value("${apiKey}")
    private String apiKey;
    private final StockService stockService;
    // remove this, we fetch this from JWT Token
    private final UserService userService;
    private final UserInvestedStocksService userInvestedStocksService;
    private List<StockPriceApiAccess> chartArray;

    @Autowired
    public StocksController(StockService stockService, UserService userService, List<StockPriceApiAccess> chartArray, UserInvestedStocksService userInvestedStocksService) {
        this.stockService = stockService;
        this.userService = userService;
        this.chartArray = chartArray;
        this.userInvestedStocksService = userInvestedStocksService;
    }

    @PostMapping("/buy")
    // TO-DO nomenclature change, DTO classes should not be returned?? maybe
    public UserInvestedStocksDTO buy_stocks(@RequestBody Map<String, String> requestBody)
    {
        Integer buy_quantity = Integer.parseInt(requestBody.get("buy_quantity"));
        Integer stockId = Integer.parseInt(requestBody.get("stockId"));
        String stockSymbol = requestBody.get("symbol");

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getAccountDetails(username);

        // get the latest stock price
        try {
            String url = "https://financialmodelingprep.com/api/v3/quote-short/{symbol}?apikey={apiKey}";

            WebClient webClient = WebClient.create();
            Mono<StockPriceApiAccess[]> stockPriceDataMono = webClient
                    .get()
                    .uri(url, stockSymbol, apiKey)
                    .retrieve()
                    .bodyToMono(StockPriceApiAccess[].class);

            StockPriceApiAccess[] stockPriceData = stockPriceDataMono.block();
            chartArray = Arrays.asList(stockPriceData);
        }
        catch(Exception ex) {
            throw ex;
        }

        BigDecimal balance = user.getBalance();
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
        UserInvestedStocksDTO currStockData = userInvestedStocksService.getUserInvestedStock(user.getId(), stockId);
        if(currStockData == null){
            currStockData = new UserInvestedStocksDTO(user.getId(), stockId, 0, new BigDecimal(String.valueOf(0)));
        }
        BigInteger curr_quantity = BigInteger.valueOf(currStockData.getQuantity());

        currStockData.setQuantity(curr_quantity.add(BigInteger.valueOf(buy_quantity)).intValue());
        currStockData.setTotalPrice(buyPrice.add(currStockData.getTotalPrice()));
        userInvestedStocksService.updateUserStockData(currStockData);

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

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getAccountDetails(username);

        // get the latest stock price
        try{
            String url = "https://financialmodelingprep.com/api/v3/quote-short/{symbol}?apikey={apiKey}";

            WebClient webClient = WebClient.create();
            Mono<StockPriceApiAccess[]> stockPriceDataMono = webClient
                    .get()
                    .uri(url, stockSymbol, apiKey)
                    .retrieve()
                    .bodyToMono(StockPriceApiAccess[].class);

            StockPriceApiAccess[] stockPriceData = stockPriceDataMono.block();
            chartArray = Arrays.asList(stockPriceData);
        }
        catch(Exception ex){
            throw ex;
        }

        UserInvestedStocksDTO currStockData = userInvestedStocksService.getUserInvestedStock(user.getId(), stockId);
        if(currStockData == null){
            return null;
        }
        int curr_quantity = currStockData.getQuantity();

        // TEMPORARY
        if(sell_quantity > curr_quantity){
            sell_quantity = curr_quantity;
        }

        BigDecimal stockPrice = chartArray.get(0).getPrice();
        BigDecimal sellPrice = stockPrice.multiply(BigDecimal.valueOf(sell_quantity));

        currStockData.setQuantity(currStockData.getQuantity() - sell_quantity);
        currStockData.setTotalPrice(currStockData.getTotalPrice().subtract(sellPrice));
        if(currStockData.getQuantity() == 0){
            userInvestedStocksService.deleteUserStockData(currStockData);
        }
        else{
            userInvestedStocksService.updateUserStockData(currStockData);
        }
        user.setBalance(user.getBalance().add(sellPrice));
        user.setInvested(user.getInvested().subtract(sellPrice));
        userService.updateUserBalance(user);

        return currStockData;
    }
}