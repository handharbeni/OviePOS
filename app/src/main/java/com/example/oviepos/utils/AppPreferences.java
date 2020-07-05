package com.example.oviepos.utils;

import android.content.Context;

import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.io.Serializable;

public class AppPreferences implements Serializable {
    private static volatile AppPreferences appPreferences;
    private static volatile EncryptedPreferences encryptedPreferences;
    private static Context context;

    private AppPreferences() {
        if (appPreferences != null || encryptedPreferences != null) {
            throw new RuntimeException("Use getInstance()");
        }
    }

    public static AppPreferences getInstance(Context context) {
        if (appPreferences == null) {
            synchronized (AppPreferences.class) {
                if (appPreferences == null) {
                    appPreferences = new AppPreferences();
                    encryptedPreferences = new EncryptedPreferences.Builder(context)
                            .withEncryptionPassword("PASSWORD ENCRYPTED")
                            .build();
                }
            }
        }
        return appPreferences;
    }

    protected AppPreferences readResolve(Context context) {
        return getInstance(context);
    }

    public AppPreferences setPref(String key, String value) {
        encryptedPreferences.edit()
                .putString(key, value)
                .apply();
        return this;
    }

    public AppPreferences setPref(String key, boolean value) {
        encryptedPreferences.edit()
                .putBoolean(key, value)
                .apply();
        return this;
    }

    public AppPreferences setPref(String key, float value) {
        encryptedPreferences.edit()
                .putFloat(key, value)
                .apply();
        return this;
    }

    public AppPreferences setPref(String key, long value) {
        encryptedPreferences.edit()
                .putLong(key, value)
                .apply();
        return this;
    }

    public AppPreferences setPref(String key, int value) {
        encryptedPreferences.edit()
                .putInt(key, value)
                .apply();
        return this;
    }

    public String getPref(String key, String defaultValue) {
        return encryptedPreferences.getString(key, defaultValue);
    }

    public boolean getPref(String key, boolean defaultValue) {
        return encryptedPreferences.getBoolean(key, defaultValue);
    }

    public float getPref(String key, float defaultValue) {
        return encryptedPreferences.getFloat(key, defaultValue);
    }

    public long getPref(String key, long defaultValue) {
        return encryptedPreferences.getLong(key, defaultValue);
    }

    public int getPref(String key, int defaultValue) {
        return encryptedPreferences.getInt(key, defaultValue);
    }

    public EncryptedPreferences getPref() {
        return encryptedPreferences;
    }
}