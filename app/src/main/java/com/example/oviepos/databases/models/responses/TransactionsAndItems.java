package com.example.oviepos.databases.models.responses;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

public class TransactionsAndItems {
    @Embedded
    public Transactions transactions;
    @Relation(parentColumn = "id", entityColumn = "transactionId", entity = TransactionItems.class)
    public List<TransactionItems> transactionItems;
}
