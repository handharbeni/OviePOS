package com.example.oviepos.utils;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

import org.jetbrains.annotations.Contract;

public class CoreApplication  extends MultiDexApplication {
    private static Application instances;

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        StrictModeManager.enableStrictMode();
    }

    @Contract(pure = true)
    public static Application getInstance(){
        return instances;
    }
}