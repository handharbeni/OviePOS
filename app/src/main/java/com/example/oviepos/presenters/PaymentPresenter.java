package com.example.oviepos.presenters;

import android.app.Activity;

import androidx.lifecycle.LifecycleOwner;

import com.example.oviepos.databases.AppDB;
import com.example.oviepos.databases.models.responses.Cart;
import com.example.oviepos.databases.models.responses.TransactionItems;
import com.example.oviepos.databases.models.responses.Transactions;
import com.example.oviepos.utils.Constants;
import com.example.oviepos.views.PaymentUIView;
import com.manishkprboilerplate.base.BasePresenter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import rx.Subscription;

public class PaymentPresenter extends BasePresenter<PaymentUIView.paymentUIView>
        implements PaymentUIView.paymentPresenter {

    Activity activity;
    private Subscription subscription;
    private LifecycleOwner lifecycleOwner;
    final String TAG = MainPresenter.class.getSimpleName();
    private AppDB appDB;

    public PaymentPresenter(Activity activity, LifecycleOwner lifecycleOwner) {
        this.activity = activity;
        this.lifecycleOwner = lifecycleOwner;
        appDB = AppDB.getInstance(activity.getApplicationContext());
    }

    @Override
    public void attachView(PaymentUIView.paymentUIView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void init() {
        List<Constants.TRANSACTION_TYPE> orderType = Arrays.asList(Constants.TRANSACTION_TYPE.values());
        getMvpView().initOrderType(orderType);

        List<Constants.PAYMENT_TYPE> paymentType = Arrays.asList(Constants.PAYMENT_TYPE.values());
        getMvpView().initPaymentType(paymentType);

        List<Constants.PAYMENT_CASHLESS> paymentCashless = Arrays.asList(Constants.PAYMENT_CASHLESS.values());
        getMvpView().initPaymentCashless(paymentCashless);

        List<Cart> listCart = appDB.cart().getAll();
        getMvpView().initListCart(listCart);
    }

    @Override
    public void doPayment(Transactions transactions, List<TransactionItems> transactionItems) {
        try {
            appDB.transactions().insertTransaction(transactions, transactionItems);
            appDB.cart().delete(appDB.cart().getAll());
            getMvpView().onPaymentSuccess();
        } catch (Exception e) {
            getMvpView().onPaymentFailed();
        }
    }
}
