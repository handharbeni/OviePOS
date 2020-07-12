package com.example.oviepos.databases.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.oviepos.databases.models.responses.Products;
import com.example.oviepos.databases.models.responses.TransactionItems;
import com.example.oviepos.databases.models.responses.Transactions;
import com.example.oviepos.databases.models.responses.TransactionsAndItems;

import java.util.List;

@Dao
public abstract class InterfaceTransactions {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    public abstract long insert(Transactions transactions);

    @Insert
    public abstract long insertItem(TransactionItems transactionItems);

    @Query("SELECT * FROM Transactions")
    public abstract List<Transactions> getAll();

    @Query("SELECT * FROM Transactions WHERE id = :id")
    public abstract List<Transactions> getById(int id);

    @Transaction
//    @Insert(onConflict = OnConflictStrategy.ABORT)
    public void insertTransaction(Transactions transactions, List<TransactionItems> transactionItems){
        final long transactionId = insert(transactions);
        for (TransactionItems transactionItems1 : transactionItems){
            transactionItems1.setTransactionId(transactionId);
            insertItem(transactionItems1);
        }
    }

    @Query("SELECT * FROM TransactionItems")
    public abstract List<TransactionItems> getAllTransactionItems();

    @Query("SELECT * FROM Transactions")
    public abstract List<TransactionsAndItems> listTransactions();

    @Query("SELECT * FROM TransactionItems WHERE productId = :productId")
    public abstract List<TransactionItems> getItems(int productId);

    @Query("SELECT * FROM TransactionItems WHERE transactionId = :transactionId")
    public abstract List<TransactionItems> getItemsByTransaction(int transactionId);
}
