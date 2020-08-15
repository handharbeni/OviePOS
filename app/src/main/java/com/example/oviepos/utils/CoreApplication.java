package com.example.oviepos.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import org.jetbrains.annotations.Contract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CoreApplication extends MultiDexApplication {
    private static Application instances;
    public static boolean isConnected = false;

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        StrictModeManager.enableStrictMode();
        StrictModeManager.allowDiskReads(CoreApplication.super::onCreate);

        Stetho.initializeWithDefaults(this);
        checkingNetwork();
    }

    @Contract(pure = true)
    public static Application getInstance() {
        return instances;
    }

    void checkingNetwork(){
        ReactiveNetwork
                .observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnectedToInternet -> {
                    // do something with isConnectedToInternet value
                    Log.d("TAG", "checkingNetwork: "+isConnectedToInternet);
                    isConnected = isConnectedToInternet;
                });
    }
}