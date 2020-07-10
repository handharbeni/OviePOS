package com.example.oviepos.views;

import com.example.oviepos.databases.models.responses.Cart;
import com.manishkprboilerplate.base.UiView;

import java.util.List;

public interface CartUIView extends UiView {
    void showAllCart(List<Cart> listCart);

    void updateData(List<Cart> listCart);

    interface CartCallback{
        void onCustomerNameChange(String text);
    }
}
