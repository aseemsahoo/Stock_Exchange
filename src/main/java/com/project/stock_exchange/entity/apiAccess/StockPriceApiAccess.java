package com.project.stock_exchange.entity.apiAccess;

import java.math.BigDecimal;

public class StockPriceApiAccess
{
    private String symbol;
    private BigDecimal price;
    private long volume;
    public StockPriceApiAccess () {};

    public StockPriceApiAccess(String symbol, BigDecimal price, long volume) {
        this.symbol = symbol;
        this.price = price;
        this.volume = volume;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }
}
