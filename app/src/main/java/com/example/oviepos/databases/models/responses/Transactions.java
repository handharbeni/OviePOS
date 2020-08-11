package com.example.oviepos.databases.models.responses;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import androidx.room.TypeConverters;

import com.example.oviepos.databases.helpers.DataConverter;

import java.io.Serializable;
import java.util.List;

@Entity(indices = {@Index(value = {"id"}, unique = true)})
public class Transactions implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String productId;
    private String customerName;
    private String transactionsType;
    private String paymentType;
    private String productPrice;
    private String productName;
    private String discountValue;
    private String discountType;
    private String pajakValue;
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

    public String getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(String discountValue) {
        this.discountValue = discountValue;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getPajakValue() {
        return pajakValue;
    }

    public void setPajakValue(String pajakValue) {
        this.pajakValue = pajakValue;
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

    @Override
    public String toString() {
        return "Transactions\n{" +
                "id=" + id +
                ", productId='" + productId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", transactionsType='" + transactionsType + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productName='" + productName + '\'' +
                ", discountValue='" + discountValue + '\'' +
                ", discountType='" + discountType + '\'' +
                ", pajakValue='" + pajakValue + '\'' +
                ", timeIn=" + timeIn +
                ", timeOut=" + timeOut +
                ", dateNow=" + dateNow +
                '}';
    }
}
