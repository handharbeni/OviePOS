package com.example.oviepos.presenters;

import android.app.Activity;
import android.os.CountDownTimer;

import androidx.lifecycle.LifecycleOwner;

import com.example.oviepos.R;
import com.example.oviepos.utils.AppPreferences;
import com.example.oviepos.utils.Constants;
import com.example.oviepos.views.MainUIView;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.manishkprboilerplate.base.BasePresenter;

import java.util.List;
import java.util.Objects;

import rx.Subscription;

public class MainPresenter extends BasePresenter<MainUIView> {
    Activity activity;
    private Subscription subscription;
    private LifecycleOwner lifecycleOwner;
    private MaterialButtonToggleGroup buttonToggleGroup;
    final String TAG = MainPresenter.class.getSimpleName();

    public MainPresenter(Activity activity, LifecycleOwner lifecycleOwner, MaterialButtonToggleGroup buttonToggleGroup) {
        this.activity = activity;
        this.lifecycleOwner = lifecycleOwner;
        this.buttonToggleGroup = buttonToggleGroup;
    }

    @Override
    public void attachView(MainUIView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void init() {
        getMvpView().splashScreen();
        requestPermission();
    }

    public void countDownDecision() {
        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                decisionScreen();
            }
        }.start();
    }

    public void decisionScreen() {
        boolean stateLogin = AppPreferences.getInstance(activity.getApplicationContext())
                .getPref(Constants.STATE_LOGIN, false);
        if (stateLogin) {
            getMvpView().mainScreen();
            buttonToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
                if (group.getCheckedButtonId() != -1) {
                    switch (group.getCheckedButtonId()) {
                        case R.id.productCategory:
                            getMvpView().fragmentCategory();
                            break;
                        case R.id.product:
                            getMvpView().fragmentProduct();
                            break;
                        case R.id.report:
                            getMvpView().fragmentReport();
                            break;
                        case R.id.account:
                            getMvpView().fragmentAccount();
                            break;
                    }
                } else {
                    getMvpView().fragmentCart();
                }

            });
        } else {
            getMvpView().loginScreen();
        }

    }

    public void requestPermission() {
        Dexter.withActivity(activity)
                .withPermissions(Constants.PERMISSIONS)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            getMvpView().permissionGranted();
                        } else {
                            getMvpView().permissionDenied();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions
                            , PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(error -> {

                })
                .check();
    }

    public void doLogin(TextInputLayout txtUsername, TextInputLayout txtPassword) {
        if (isValidated(txtUsername) || isValidated(txtPassword)) {
            return;
        }
        AppPreferences.getInstance(activity.getApplicationContext())
                .setPref(Constants.USERNAME, Objects.requireNonNull(txtUsername.getEditText()).getText().toString());
        AppPreferences.getInstance(activity.getApplicationContext())
                .setPref(Constants.STATE_LOGIN, true);
        getMvpView().loginSuccess();
    }

    boolean isValidated(TextInputLayout textInputLayout) {
        textInputLayout.setError(null);
        if (Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().isEmpty()) {
            textInputLayout.setError("CANNOT BE NULL");
            return false;
        }
        return true;
    }

    public void doRegister() {
        getMvpView().registerSuccess();
    }
}
