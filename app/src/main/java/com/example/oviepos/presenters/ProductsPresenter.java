package com.example.oviepos.presenters;

import android.app.Activity;

import androidx.lifecycle.LifecycleOwner;

import com.example.oviepos.databases.AppDB;
import com.example.oviepos.databases.models.responses.Products;
import com.example.oviepos.databases.models.responses.ProductsCategory;
import com.example.oviepos.views.ProductUIView;
import com.manishkprboilerplate.base.BasePresenter;

import java.util.List;

import rx.Subscription;

public class ProductsPresenter extends BasePresenter<ProductUIView> {
    Activity activity;
    private LifecycleOwner lifecycleOwner;
    private Subscription subscription;
    final String TAG = CategoryPresenter.class.getSimpleName().toString();
    private AppDB appDB;

    public ProductsPresenter(Activity activity, LifecycleOwner lifecycleOwner) {
        this.activity = activity;
        this.lifecycleOwner = lifecycleOwner;
        appDB = AppDB.getInstance(activity.getApplicationContext());
    }

    @Override
    public void attachView(ProductUIView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void init() {
        List<ProductsCategory> listCategory = appDB.productCategory().getAll();
        getMvpView().listAllCategory(listCategory);

        List<Products> listProducts = appDB.products().getAll();
        getMvpView().listAllProduct(listProducts);
    }

    public void listAll() {
        List<Products> listProducts = appDB.products().getAll();
        getMvpView().listAllProduct(listProducts);
    }

    public void listByCategory(int categoryId) {
        List<Products> listProducts = appDB.products().getByCategory(categoryId);
        getMvpView().updateProduct(listProducts);
    }

    public void updateData(){
        List<Products> listProducts = appDB.products().getAll();
        getMvpView().updateProduct(listProducts);
    }

    public void createProduct(Products products) {
        appDB.products().insert(products);
        getMvpView().processSuccess();
    }

    public void updateProduct(Products products) {
        appDB.products().update(products);
        getMvpView().processSuccess();
    }

    public void deleteProduct(Products products) {
        appDB.products().delete(products);
        getMvpView().processSuccess();
    }
}
