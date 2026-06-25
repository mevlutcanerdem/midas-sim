package com.midassim.portfolio_tracker.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "portfolios")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // "birçok" portföy satırı bir kullanıcıya ait olabilir
    // Ali'nin hem altını hem thy hissesi olabilir
    @ManyToOne
    @JoinColumn(name = "user_id") // veritabanında user_id adında bir sütun aç ve User tablosunu bağla
    private User user;

    // "birçok " portföy satırı bir hisseyi işaret edebilir
    // altını hem ali hem ayşe alabilir
    @ManyToOne
    @JoinColumn(name = "asset_id") // veritabanında "asset_id" isimli bir sütun aç ve asset tablosunu bağla
    private Asset asset;

    // kullanıcının elinde bu varlıklardan kaç tane var
    private BigDecimal quantity;
    //  varlığın ortalama birim maliyetini bilmek gerekir kar zarar hesabı için
    private BigDecimal averageBuyPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
