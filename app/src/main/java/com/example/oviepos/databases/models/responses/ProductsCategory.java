package com.example.oviepos.databases.models.responses;


import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(indices = {@Index(value = {"id", "categoryName"}, unique = true)})
public class ProductsCategory implements Serializable {
    @PrimaryKey(autoGenerate = true)
    Integer id;
    String categoryName;

    public ProductsCategory() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
