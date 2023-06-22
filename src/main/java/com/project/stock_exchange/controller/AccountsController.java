package com.project.stock_exchange.controller;

import com.project.stock_exchange.entity.*;
import com.project.stock_exchange.service.StockService;
import com.project.stock_exchange.service.UserService;
import jakarta.persistence.Tuple;
import org.antlr.v4.runtime.misc.Triple;
import org.javatuples.Sextet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Controller
@RequestMapping("/accounts")
public class AccountsController
{
    @Value("${apiKey}")
    private String apiKey;
    private final UserService userService;
    private final StockService stockService;
    private SessionID sessionID;

    public AccountsController(UserService userService, StockService stockService, SessionID sessionID) {
        this.userService = userService;
        this.stockService = stockService;
        this.sessionID = sessionID;
    }

    @GetMapping("/list")
    public String listAccountData(Model theModel)
    {
        User curr_user = sessionID.getUser();
        theModel.addAttribute("user", curr_user);

        List<UserInvestedStocks> stocksList = userService.getAlluserInvestedStocks(curr_user.getId());
        List<Sextet> tuple = new ArrayList<>();

        for(UserInvestedStocks user_stock : stocksList)
        {
            Stock stock = stockService.findById(user_stock.getStockId());
            String url = "https://financialmodelingprep.com/api/v3/quote-short/{symbol}?apikey={apiKey}";

            Map<String, String> uriVariables = new HashMap<>();
            uriVariables.put("symbol", stock.getSymbol());
            uriVariables.put("apiKey", apiKey);

            List<StockPriceApiAccess> stockPriceDetails = new ArrayList<>();
            try
            {
                RestTemplate restTemplate = new RestTemplate();
                StockPriceApiAccess[] stockPriceData = restTemplate.getForObject(url, StockPriceApiAccess[].class, uriVariables);
                stockPriceDetails = Arrays.asList(stockPriceData);
            }
            catch(Exception ex)
            {
                throw ex;
            }
            BigDecimal stockQuantity = BigDecimal.valueOf(user_stock.getQuantity());
            BigDecimal currStockPrice = stockPriceDetails.get(0).getPrice();

            BigDecimal investedValue = user_stock.getTotalPrice();
            BigDecimal currentValue = currStockPrice.multiply(stockQuantity);

            BigDecimal profit = currentValue.subtract(investedValue);
            BigDecimal profit_cent = (profit.divide(investedValue, 5, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));

            Sextet<String, Integer, BigDecimal, BigDecimal, BigDecimal, BigDecimal> sextet = Sextet.with
                    (stock.getName(), user_stock.getQuantity(), investedValue, currentValue, profit, profit_cent);
            tuple.add(sextet);
        }
        theModel.addAttribute("tuple", tuple);
        return "accounts/list-account-data";
    }
}
