package com.project.stock_exchange.controller;

import com.project.stock_exchange.entity.*;
import com.project.stock_exchange.entity.apiAccess.StockPriceApiAccess;
import com.project.stock_exchange.entity.dto.UserAccountStocksDTO;
import com.project.stock_exchange.entity.dto.UserInvestedStocksDTO;
import com.project.stock_exchange.service.interfaces.StockService;
import com.project.stock_exchange.service.interfaces.UserInvestedStocksService;
import com.project.stock_exchange.service.interfaces.UserService;
import com.project.stock_exchange.util.exception.RestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.*;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/accounts")
public class AccountsController
{
    @Value("${apiKey}")
    private String apiKey;

    // remove THIS
    private final UserService userService;
    private final StockService stockService;
    private final UserInvestedStocksService userInvestedStocksService;

    public AccountsController(UserService userService, StockService stockService, UserInvestedStocksService userInvestedStocksService) {
        this.userService = userService;
        this.stockService = stockService;
        this.userInvestedStocksService = userInvestedStocksService;
    }

    // To be DEPRECATED in FUTURE--> now the buy/sell APIs would return updated user account details.
    // Use it in frontend
    @GetMapping("/user/me")
    public ResponseEntity<?> getUserDetails() throws Exception
    {
        // USE TOKENS
        try{
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User currUser = userService.getAccountDetails(username);
            return ResponseEntity.ok(currUser);
        }
        catch(Exception ex){
            RestException errorResponse = new RestException(HttpStatus.NOT_FOUND, ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/list")
    // no return of DTO classes. Change it ???
    public List<UserAccountStocksDTO> listAccountStocksData()
    {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curr_user = userService.getAccountDetails(username);

        List<UserInvestedStocksDTO> stocksList = userInvestedStocksService.getAllUserInvestedStocks(curr_user.getId());
        List<UserAccountStocksDTO> portfolioList = new ArrayList<>();

        // for every stock invested, calcuate P&L
        // DO THIS IN THE FRONTEND !!!
        for(UserInvestedStocksDTO user_stock : stocksList) {
            Stock currStock = stockService.findById(user_stock.getStockId());
            List<StockPriceApiAccess> stockPriceDetails = new ArrayList<>();

            // get the latest stock price
            try {
                String url = "https://financialmodelingprep.com/api/v3/quote-short/{symbol}?apikey={apiKey}";

                WebClient webClient = WebClient.create();
                Mono<StockPriceApiAccess[]> stockPriceDataMono = webClient
                        .get()
                        .uri(url, currStock.getSymbol(), apiKey)
                        .retrieve()
                        .bodyToMono(StockPriceApiAccess[].class);

                StockPriceApiAccess[] stockPriceData = stockPriceDataMono.block();
                stockPriceDetails = Arrays.asList(stockPriceData);
            }
            catch(Exception ex) {
                throw ex;
            }
            BigDecimal stockQuantity = BigDecimal.valueOf(user_stock.getQuantity());
            BigDecimal currStockPrice = stockPriceDetails.get(0).getPrice();

            BigDecimal investedValue = user_stock.getTotalPrice();
            BigDecimal currentValue = currStockPrice.multiply(stockQuantity);

            portfolioList.add(new UserAccountStocksDTO(
                    curr_user.getId(), currStock.getId(), user_stock.getQuantity(),
                    investedValue, currStock.getName(), currentValue));
        }
        return portfolioList;
    }
}
