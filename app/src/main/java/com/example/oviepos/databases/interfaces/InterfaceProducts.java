package com.example.oviepos.databases.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.oviepos.databases.models.responses.Products;
import com.example.oviepos.databases.models.responses.ProductsCategory;

import java.util.List;

@Dao
public interface InterfaceProducts {
    @Insert()
    void insertAll(List<Products> productsList);

    @Insert()
    void insert(Products products);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(Products products);

    @Delete
    void delete(Products products);

    @Query("SELECT * FROM Products")
    List<Products> getAll();

    @Query("SELECT * FROM Products WHERE productCategory = :productCategory")
    List<Products> getByCategory(int productCategory);

    @Query("SELECT * FROM Products WHERE id = :id")
    List<Products> getById(int id);

    @Query("SELECT * FROM Products WHERE id = :id")
    Products getProductsById(int id);
}
