package com.example.oviepos.views;

import com.manishkprboilerplate.base.UiView;

public interface AccountUIView extends UiView {
    void fillProfile(String username);

    void logoutSuccess();
}
