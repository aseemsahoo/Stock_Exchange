package com.project.stock_exchange.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockPriceApiAccess
{
    private String date;

    private Double open;
    private Double low;
    private Double high;
    private Double close;
    private int volume;
    public StockPriceApiAccess() {};

    public StockPriceApiAccess(String date, Double open, Double low, Double high, Double close, int volume) {
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

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
