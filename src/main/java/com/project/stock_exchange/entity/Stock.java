package com.project.stock_exchange.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="stocks")
public class Stock 
{
    
}

@Entity
// mapping to table in database
@Table(name="sampletable")
public class Users
{
    // define fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="phone_number")
    private String phonenumber;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;
