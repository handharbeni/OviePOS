package com.example.oviepos.presenters;

import android.app.Activity;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.oviepos.databases.AppDB;
import com.example.oviepos.databases.models.responses.Cart;
import com.example.oviepos.views.CartUIView;
import com.manishkprboilerplate.base.BasePresenter;
import com.manishkprboilerplate.web_services.RxUtil;

import java.util.List;

import rx.Subscription;

public class CartPresenter extends BasePresenter<CartUIView> {
    Activity activity;
    private LifecycleOwner lifecycleOwner;
    private Subscription subscription;
    final String TAG = CategoryPresenter.class.getSimpleName();
    private AppDB appDB;
    private LiveData<List<Cart>> liveCart;

    public CartPresenter(Activity activity, LifecycleOwner lifecycleOwner) {
        this.activity = activity;
        this.lifecycleOwner = lifecycleOwner;
        appDB = AppDB.getInstance(activity.getApplicationContext());
    }

    @Override
    public void attachView(CartUIView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (liveCart.hasActiveObservers()) {
            liveCart.removeObservers(lifecycleOwner);
        }
    }

    public void init() {
        getMvpView().showAllCart(appDB.cart().getAll());
        liveCart = appDB.cart().liveCart();
        liveCart.observe(lifecycleOwner, carts -> getMvpView().updateData(carts));
    }

    public void addQty(Cart cart) {
        cart.setQty(cart.getQty() + 1);
        appDB.cart().update(cart);
    }

    public void removeQty(Cart cart) {
        if (cart.getQty() <= 1) {
            appDB.cart().delete(cart);
        } else {
            cart.setQty(cart.getQty() - 1);
            appDB.cart().update(cart);
        }
    }
}
