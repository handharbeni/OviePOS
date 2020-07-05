package com.example.oviepos.databases.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.oviepos.databases.models.responses.Cart;

import java.util.List;

@Dao
public interface InterfaceCart {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Cart cart);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(Cart cart);

    @Delete
    void delete(Cart cart);

    @Query("SELECT * FROM Cart WHERE productId = :productId")
    Cart getCartByProductId(int productId);

    @Query("SELECT * FROM Cart")
    List<Cart> getAll();

    @Query("SELECT * FROM Cart")
    LiveData<List<Cart>> liveCart();
}
