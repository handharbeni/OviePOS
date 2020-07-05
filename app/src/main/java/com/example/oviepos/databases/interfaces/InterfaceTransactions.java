package com.example.oviepos.databases.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.oviepos.databases.models.responses.Products;
import com.example.oviepos.databases.models.responses.Transactions;

import java.util.List;

@Dao
public interface InterfaceTransactions {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertAll(List<Transactions> transactionsList);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Transactions transactions);

    @Query("SELECT * FROM Transactions")
    List<Products> getAll();

    @Query("SELECT * FROM Transactions WHERE id = :id")
    List<Products> getById(int id);
}
