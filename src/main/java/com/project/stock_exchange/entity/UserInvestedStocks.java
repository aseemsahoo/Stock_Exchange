package com.project.stock_exchange.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user_portfolio")
public class UserInvestedStocks
{
    @Id
    @Column(name="user_id")
    private int id;
    @Column(name="stock_id")
    private int stockId;
    @Column(name="quantity")
    private int quantity;
    @Column(name="total_price")
    private double totalPrice;

    public UserInvestedStocks() {};
    public UserInvestedStocks(int id, int stockId, int quantity, double totalPrice) {
        this.id = id;
        this.stockId = stockId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
