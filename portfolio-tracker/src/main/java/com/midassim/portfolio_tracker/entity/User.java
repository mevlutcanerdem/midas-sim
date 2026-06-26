package com.midassim.portfolio_tracker.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity // Bu sınıfın bir veritabanı tablosu olduğunu springe söyler
@Table(name = "users") // tablonun adı users olacak çünkü user is a reserved word
public class User {

    @Id // Bu sütunun birincil anahtar(Primary key ) olduğunu belirtir
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id nin 1,2,3 diye otamatik artmasını sağlar
    private Long id;

    private String userName;

    // Kullanıcının cüzdanındaki nakit para (Küsüratlı olduğu için bigdecimal kullanıyoruz)
    private BigDecimal balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
