package com.example.oviepos.callbacks;

import com.example.oviepos.databases.models.responses.Products;

public interface ProductBottomsheetCallback {
    void insert(Products products);

    void update(Products products);

    void delete(Products products);
}
