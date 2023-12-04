package com.project.stock_exchange.entity.dto;

import java.io.Serializable;

public class UserInvestedStocksID implements Serializable
{
    private int id;
    private int stockId;

    public UserInvestedStocksID () {};

    public UserInvestedStocksID(int id, int stockId) {
        this.id = id;
        this.stockId = stockId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }
}
