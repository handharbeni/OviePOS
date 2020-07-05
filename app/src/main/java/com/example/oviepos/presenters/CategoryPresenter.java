package com.example.oviepos.presenters;

import android.app.Activity;

import androidx.lifecycle.LifecycleOwner;

import com.example.oviepos.databases.AppDB;
import com.example.oviepos.databases.models.responses.ProductsCategory;
import com.example.oviepos.views.CategoryUIView;
import com.manishkprboilerplate.base.BasePresenter;

import java.util.List;

import rx.Subscription;

public class CategoryPresenter extends BasePresenter<CategoryUIView> {
    Activity activity;
    private LifecycleOwner lifecycleOwner;
    private Subscription subscription;
    final String TAG = CategoryPresenter.class.getSimpleName();

    public CategoryPresenter(Activity activity, LifecycleOwner lifecycleOwner) {
        this.activity = activity;
        this.lifecycleOwner = lifecycleOwner;
    }

    @Override
    public void attachView(CategoryUIView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void init() {
        List<ProductsCategory> listCategory = AppDB.getInstance(activity.getApplicationContext())
                .productCategory()
                .getAll();
        getMvpView().showAllData(listCategory);
    }

    public void updateData() {
        List<ProductsCategory> listCategory = AppDB.getInstance(activity.getApplicationContext())
                .productCategory()
                .getAll();
        getMvpView().updateData(listCategory);
    }

    public void createCategory(ProductsCategory productsCategory) {
        AppDB.getInstance(activity.getApplicationContext())
                .productCategory()
                .insert(productsCategory);
        getMvpView().processSuccess();
    }

    public void updateCategory(ProductsCategory productsCategory) {
        AppDB.getInstance(activity.getApplicationContext())
                .productCategory()
                .update(productsCategory);
        getMvpView().processSuccess();
    }

    public void deleteCategory(ProductsCategory productsCategory) {
        AppDB.getInstance(activity.getApplicationContext())
                .productCategory()
                .delete(productsCategory);
        getMvpView().processSuccess();
    }
}
