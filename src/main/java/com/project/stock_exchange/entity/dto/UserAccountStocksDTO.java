package com.project.stock_exchange.entity.dto;

import jakarta.persistence.Id;

import java.math.BigDecimal;

public class UserAccountStocksDTO extends UserInvestedStocksID{
//                    (investedValue, currentValue, profit);
    @Id
    private int id;
    @Id
    private int stockId;
    private int quantity;
    private BigDecimal totalPrice;
    private String name;
    private BigDecimal currValue;
    public UserAccountStocksDTO() {};

    public UserAccountStocksDTO(int id, int stockId, int quantity, BigDecimal totalPrice, String name, BigDecimal currValue) {
        this.id = id;
        this.stockId = stockId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.name = name;
        this.currValue = currValue;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getStockId() {
        return stockId;
    }

    @Override
    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCurrValue() {
        return currValue;
    }

    public void setCurrValue(BigDecimal currValue) {
        this.currValue = currValue;
    }
}
