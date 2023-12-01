package com.project.stock_exchange.controller;

import com.project.stock_exchange.entity.*;
import com.project.stock_exchange.entity.ApiAccess.StockPriceApiAccess;
import com.project.stock_exchange.entity.DTO.UserAccountStocksDTO;
import com.project.stock_exchange.entity.DTO.UserInvestedStocksDTO;
import com.project.stock_exchange.entity.singleton.SessionID;
import com.project.stock_exchange.service.Interfaces.StockService;
import com.project.stock_exchange.service.Interfaces.UserService;
import com.project.stock_exchange.util.ApiException;
import org.javatuples.Sextet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
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

    // To be deprecated in FUTURE
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserDetails(@PathVariable("username") String username) throws Exception
    {
        // inject
        try{
            User user = sessionID.getUser();
            User currUser = userService.getAccountDetails(username);
            return ResponseEntity.ok(currUser);
        }
        catch(Exception ex){
            ApiException errorResponse = new ApiException(HttpStatus.NOT_FOUND, ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/list")
    // no return of DTO classes. Change it
    public List<UserAccountStocksDTO> listAccountStocksData()
    {
        if(sessionID.getUser() == null) { }
//            return "redirect:/";
//        User curr_user = sessionID.getUser();

        User curr_user = userService.getAccountDetails("aseemsahoo");

                    List<UserInvestedStocksDTO> stocksList = userService.getAlluserInvestedStocks(curr_user.getId());
        List<UserAccountStocksDTO> portfolioList = new ArrayList<>();

        // for every stock invested, calcuate P&L
        // DO THIS IN THE FRONTEND !!!
        for(UserInvestedStocksDTO user_stock : stocksList)
        {
            Stock currStock = stockService.findById(user_stock.getStockId());
            String url = "https://financialmodelingprep.com/api/v3/quote-short/{symbol}?apikey={apiKey}";

            Map<String, String> uriVariables = new HashMap<>();
            uriVariables.put("symbol", currStock.getSymbol());
            uriVariables.put("apiKey", apiKey);

            List<StockPriceApiAccess> stockPriceDetails = new ArrayList<>();

            // get the latest stock price
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

            portfolioList.add(new UserAccountStocksDTO(
                    curr_user.getId(), currStock.getId(), user_stock.getQuantity(),
                    investedValue, currStock.getName(), currentValue));

//            BigDecimal profit = currentValue.subtract(investedValue);
//            BigDecimal profit_cent = (profit.divide(investedValue, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));

//            Sextet<String, Integer, BigDecimal, BigDecimal, BigDecimal, BigDecimal> sextet = Sextet.with
//                    (currStock.getName(), user_stock.getQuantity(), investedValue, currentValue, profit, profit_cent);
        }
        return portfolioList;
    }
}
