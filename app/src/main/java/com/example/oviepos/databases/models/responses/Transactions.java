package com.example.oviepos.databases.models.responses;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = { @Index(value = {"id"}, unique = true) })
public class Transactions {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String productId;
    private String customerName;
    private String transactionsType;
    private String paymentType;
    private String productPrice;
    private String productName;
    private Long timeIn;
    private Long timeOut;
    private Long dateNow;

    public Transactions() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTransactionsType() {
        return transactionsType;
    }

    public void setTransactionsType(String transactionsType) {
        this.transactionsType = transactionsType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Long timeIn) {
        this.timeIn = timeIn;
    }

    public Long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Long timeOut) {
        this.timeOut = timeOut;
    }

    public Long getDateNow() {
        return dateNow;
    }

    public void setDateNow(Long dateNow) {
        this.dateNow = dateNow;
    }
}
