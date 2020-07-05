package com.example.oviepos.views;

import com.example.oviepos.databases.models.responses.ProductsCategory;
import com.manishkprboilerplate.base.UiView;

import java.util.List;

public interface CategoryUIView extends UiView {
    void showAllData(List<ProductsCategory> listCategory);

    void updateData(List<ProductsCategory> listCategory);

    void processSuccess();

    void processFailed();
}
