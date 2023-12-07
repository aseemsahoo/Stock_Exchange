package com.project.stock_exchange.entity.dto;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="user_portfolio")
@IdClass(UserInvestedStocksID.class)
public class UserInvestedStocksDTO extends UserInvestedStocksID
{
    @Id
    @Column(name="user_id")
    private int id;
    @Id
    @Column(name="stock_id")
    private int stockId;
    @Column(name="quantity")
    private int quantity;
    @Column(name="total_price", columnDefinition = "NUMERIC(10,3)")
    private BigDecimal totalPrice;

    public UserInvestedStocksDTO() {};
    public UserInvestedStocksDTO(int id, int stockId, int quantity, BigDecimal totalPrice) {
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
