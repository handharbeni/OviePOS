package com.example.oviepos.views;

import com.manishkprboilerplate.base.UiView;

public interface MainUIView extends UiView {
    void splashScreen();

    void loginScreen();

    void registerScreen();

    void mainScreen();

    void fragmentCategory();

    void fragmentProduct();

    void fragmentReport();

    void fragmentAccount();

    void fragmentCart();

    void loginSuccess();

    void loginFailed();

    void registerSuccess();

    void registerFailed();

    void permissionGranted();

    void permissionDenied();
}
