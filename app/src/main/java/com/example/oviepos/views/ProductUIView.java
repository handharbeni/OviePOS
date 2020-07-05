package com.example.oviepos.views;

import com.bumptech.glide.ListPreloader;
import com.example.oviepos.databases.models.responses.Products;
import com.example.oviepos.databases.models.responses.ProductsCategory;
import com.manishkprboilerplate.base.UiView;

import java.util.List;

public interface ProductUIView extends UiView {
    void listAllCategory(List<ProductsCategory> listCategory);

    void listAllProduct(List<Products> listProduct);

    void updateProduct(List<Products> listProducts);

    void processSuccess();

    void processFailed();
}
