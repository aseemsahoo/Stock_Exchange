package com.project.stock_exchange.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class StockIntradayObj
{
    private String date;

    @JsonProperty("open")
    private Double myopen;
    private Double low;
    private Double high;
    private Double close;
    private int volume;
    public StockIntradayObj () {};

    public StockIntradayObj(String date, Double open, Double low, Double high, Double close, int volume) {
        this.date = date;
        this.myopen = open;
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
        return myopen;
    }

    public void setOpen(Double open) {
        this.myopen = open;
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
