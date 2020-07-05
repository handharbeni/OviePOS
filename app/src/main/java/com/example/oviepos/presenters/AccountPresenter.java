package com.example.oviepos.presenters;

import android.app.Activity;

import androidx.lifecycle.LifecycleOwner;

import com.example.oviepos.callbacks.AccountCallback;
import com.example.oviepos.utils.AppPreferences;
import com.example.oviepos.utils.Constants;
import com.example.oviepos.views.AccountUIView;
import com.manishkprboilerplate.base.BasePresenter;

import rx.Subscription;

public class AccountPresenter extends BasePresenter<AccountUIView> {
    Activity activity;
    private LifecycleOwner lifecycleOwner;
    private Subscription subscription;
    final String TAG = AccountPresenter.class.getSimpleName().toString();

    public AccountPresenter(Activity activity, LifecycleOwner lifecycleOwner) {
        this.activity = activity;
        this.lifecycleOwner = lifecycleOwner;
    }

    @Override
    public void attachView(AccountUIView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void init() {
        String username = AppPreferences.getInstance(activity.getApplicationContext())
                .getPref(Constants.USERNAME, "KOSONG");
        getMvpView().fillProfile(username);
    }

    public void doLogout() {
        AppPreferences.getInstance(activity.getApplicationContext())
                .setPref(Constants.STATE_LOGIN, false);
        getMvpView().logoutSuccess();
    }
}
