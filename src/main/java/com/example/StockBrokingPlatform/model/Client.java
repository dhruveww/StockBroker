package com.example.StockBrokingPlatform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(String kycStatus) {
        this.kycStatus = kycStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<WatchList> getWatchLists() {
        return watchLists;
    }

    public void setWatchLists(List<WatchList> watchLists) {
        this.watchLists = watchLists;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @NotBlank
    private String clientCode;

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String phone;

    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}")
    private String pan;

    private String kycStatus; // COMPLETED or NOT_COMPLETED

    private String status; // ACTIVE or INACTIVE

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<WatchList> watchLists;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Order> orders;
}
