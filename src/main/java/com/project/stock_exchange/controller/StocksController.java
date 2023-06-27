package com.project.stock_exchange.controller;

import com.project.stock_exchange.entity.*;
import com.project.stock_exchange.entity.ApiAccess.StockIntradayPriceApiAccess;
import com.project.stock_exchange.entity.DTO.UserInvestedStocksDTO;
import com.project.stock_exchange.entity.singleton.SessionID;
import com.project.stock_exchange.service.Interfaces.StockService;
import com.project.stock_exchange.service.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;
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
    private List<StockIntradayPriceApiAccess> chartArray;

    @Autowired
    public StocksController(StockService stockService, UserService userService, SessionID sessionID, List<StockIntradayPriceApiAccess> chartArray) {
        this.stockService = stockService;
        this.userService = userService;
        this.sessionID = sessionID;
        this.chartArray = chartArray;
    }

    @GetMapping("/showCharts")
    public String show_charts(@RequestParam("stockId") int stock_id, Model theModel)
    {
        if(sessionID.getUser() == null)
            return "redirect:/";
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
            StockIntradayPriceApiAccess[] chartData = restTemplate.getForObject(url, StockIntradayPriceApiAccess[].class, uriVariables);
            chartArray = Arrays.asList(chartData);
        }
        catch(Exception ex)
        {
            throw ex;
        }
        Map<String, BigDecimal> surveyMap = new LinkedHashMap<>();
        for(int i=chartArray.size()-1; i>=0; i--)
        {
            StockIntradayPriceApiAccess item = chartArray.get(i);
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

        BigDecimal balance = user.getBalance();

        BigDecimal stockPrice = chartArray.get(0).getClose();
        BigDecimal buyPrice = stockPrice.multiply(BigDecimal.valueOf(buy_quantity));

        if(buyPrice.compareTo(balance) == 1)
        {
            // TEMPORARY
            buy_quantity = balance.divide(stockPrice).intValue() - 1;
            buyPrice = stockPrice.multiply(BigDecimal.valueOf(buy_quantity));
        }
        if(buy_quantity < 1)
        {
            return "stocks/list-stocks";
        }
        UserInvestedStocksDTO currStockData = userService.getUserInvestedStock(user.getId(), stockId);
        if(currStockData == null)
        {
            currStockData = new UserInvestedStocksDTO(user.getId(), stockId, 0, new BigDecimal(String.valueOf(0)));
        }
        BigInteger curr_quantity = BigInteger.valueOf(currStockData.getQuantity());

        currStockData.setQuantity(curr_quantity.add(BigInteger.valueOf(buy_quantity)).intValue());
        currStockData.setTotalPrice(buyPrice.add(currStockData.getTotalPrice()));
        userService.updateUserStockData(currStockData);

        user.setBalance(user.getBalance().subtract(buyPrice));
        user.setInvested(user.getInvested().add(buyPrice));
        userService.updateUserBalance(user);
        return "redirect:/dashboard/list";
    }

    @PostMapping("/sell")
    public String sell_stocks(@RequestParam Integer sell_quantity,
                              @RequestParam("id") Integer stockId,  Model theModel)
    {
        User user = sessionID.getUser();

        UserInvestedStocksDTO currStockData = userService.getUserInvestedStock(user.getId(), stockId);
        if(currStockData == null)
        {
            return "accounts/list-account-data";
        }
        int curr_quantity = currStockData.getQuantity();
        if(sell_quantity > curr_quantity)
        {
            // TEMPORARY
            sell_quantity = curr_quantity;
        }
        BigDecimal stockPrice = chartArray.get(0).getClose();
        BigDecimal buyPrice = (currStockData.getTotalPrice().multiply(BigDecimal.valueOf(sell_quantity))).divide(BigDecimal.valueOf(curr_quantity));
        BigDecimal sellPrice = stockPrice.multiply(BigDecimal.valueOf(sell_quantity));

        currStockData.setQuantity(currStockData.getQuantity() - sell_quantity);
        currStockData.setTotalPrice(currStockData.getTotalPrice().subtract(sellPrice));
        if(currStockData.getQuantity() == 0)
        {
            userService.deleteUserStockData(currStockData);
        }
        else
        {
            userService.updateUserStockData(currStockData);
        }

        user.setBalance(user.getBalance().add(sellPrice));
        user.setInvested(user.getInvested().subtract(buyPrice));
        userService.updateUserBalance(user);

        return "redirect:/dashboard/list";
//        return "stocks/list-stocks";
    }
}