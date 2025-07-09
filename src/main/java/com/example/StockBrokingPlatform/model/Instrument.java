package com.example.StockBrokingPlatform.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "instrument")
public class Instrument {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Integer getLotSize() {
        return lotSize;
    }

    public void setLotSize(Integer lotSize) {
        this.lotSize = lotSize;
    }

    public List<WatchListItem> getWatchListItems() {
        return watchListItems;
    }

    public void setWatchListItems(List<WatchListItem> watchListItems) {
        this.watchListItems = watchListItems;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;
    private String companyName;
    private String exchange;
    private String exchangeType;
    private Double currentPrice;
    private Integer lotSize;

    @OneToMany(mappedBy = "instrument")
    private List<WatchListItem> watchListItems;

    @OneToMany(mappedBy = "instrument")
    private List<Order> orders;
}
