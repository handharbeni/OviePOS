package com.example.oviepos.databases.models.responses;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(indices = {@Index(value = {"id"}, unique = true)})
//,
//        foreignKeys = @ForeignKey(entity = Transactions.class,
//        parentColumns = "id",
//        childColumns = "transactionId",
//        onDelete = ForeignKey.RESTRICT))
public class TransactionItems implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    long transactionId;
    int productId;
    String productName;
    String productPrice;
    int qty;

    public TransactionItems() {
    }

    @Ignore
    public TransactionItems(int id, int transactionId, int productId, String productName, String productPrice, int qty) {
        this.id = id;
        this.transactionId = transactionId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "TransactionItems{" +
                "id=" + id +
                ", transactionId=" + transactionId +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", qty=" + qty +
                '}';
    }
}
