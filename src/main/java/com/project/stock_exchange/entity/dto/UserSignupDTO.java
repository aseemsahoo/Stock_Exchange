package com.project.stock_exchange.entity.DTO;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="users")
public class UserSignupDTO
{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email_address")
    private String email;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="enabled")
    private Boolean enabled;
    @Column(name="amt_balance", columnDefinition = "NUMERIC(10,3)")
    private BigDecimal balance;

    @Column(name="amt_invested", columnDefinition = "NUMERIC(10,3)")
    private BigDecimal invested;

    public UserSignupDTO() {};

    public UserSignupDTO(int id, String firstName, String lastName, String email, String username, String password, Boolean enabled, BigDecimal balance, BigDecimal invested) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.balance = balance;
        this.invested = invested;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getInvested() {
        return invested;
    }

    public void setInvested(BigDecimal invested) {
        this.invested = invested;
    }
}
