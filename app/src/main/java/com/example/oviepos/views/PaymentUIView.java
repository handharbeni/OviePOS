package com.example.oviepos.views;

import com.example.oviepos.databases.models.responses.Cart;
import com.example.oviepos.utils.Constants;
import com.manishkprboilerplate.base.UiView;

import java.util.List;

public interface PaymentUIView {
    interface paymentPresenter{
        void init();
        void doPayment();
    }
    interface paymentUIView extends UiView{
        void initOrderType(List<Constants.TRANSACTION_TYPE> orderType);
        void initPaymentType(List<Constants.PAYMENT_TYPE> paymentType);
        void initPaymentCashless(List<Constants.PAYMENT_CASHLESS> paymentCashless);
        void initListCart(List<Cart> listCart);
        void onPaymentSuccess();
        void onPaymentFailed();
    }
}
