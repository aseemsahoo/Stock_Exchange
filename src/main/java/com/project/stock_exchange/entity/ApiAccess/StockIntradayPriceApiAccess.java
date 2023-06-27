package com.project.stock_exchange.entity.ApiAccess;

import java.math.BigDecimal;
import java.math.BigInteger;

public class StockIntradayPriceApiAccess
{
    private String date;
    private Double open;
    private Double low;
    private Double high;
    private BigDecimal close;
    private int volume;
    public StockIntradayPriceApiAccess() {};

    public StockIntradayPriceApiAccess(String date, Double open, Double low, Double high, BigDecimal close, int volume) {
        this.date = date;
        this.open = open;
        this.low = low;
        this.high = high;
        this.close = close;
        this.volume = volume;
    }

    public String getDate() {
        return String.valueOf(date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
