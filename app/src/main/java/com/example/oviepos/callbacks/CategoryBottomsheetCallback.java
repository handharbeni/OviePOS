package com.example.oviepos.callbacks;

import com.example.oviepos.databases.models.responses.ProductsCategory;

public interface CategoryBottomsheetCallback {
    void insert(ProductsCategory productsCategory);

    void update(ProductsCategory productsCategory);

    void delete(ProductsCategory productsCategory);
}
