package com.example.oviepos.databases.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.oviepos.databases.models.responses.ProductsCategory;

import java.util.List;

@Dao
public interface InterfaceProductCategory {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertAll(List<ProductsCategory> listCategory);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(ProductsCategory productsCategory);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(ProductsCategory productsCategory);

    @Delete
    void delete(ProductsCategory productsCategory);

    @Query("SELECT * FROM ProductsCategory")
    List<ProductsCategory> getAll();

    @Query("SELECT * FROM ProductsCategory WHERE id = :id")
    List<ProductsCategory> getById(int id);

    @Query("SELECT * FROM ProductsCategory WHERE categoryName = :categoryName")
    ProductsCategory getId(String categoryName);
}
