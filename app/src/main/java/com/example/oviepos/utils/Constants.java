package com.example.oviepos.utils;

import android.Manifest;

public class Constants {
    public static final int versionDb = 2;
    public static final boolean exportSchema = false;
    public static final String nameDb = "ovie-pos";

    public static final String STATE_LOGIN = "STATE_LOGIN";
    public static final String DEVICE_ID = "DEVICES_ID";
    public static final String USERNAME = "USERNAME";

    public static String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET
    };

    public static enum TRANSACTION_TYPE {
        TAKE_AWAY ("TAKE AWAY"),
        DINE_IN ("DINE IN");

        private final String name;

        private TRANSACTION_TYPE(String s){
            name = s;
        }

        public boolean equalsName(String otherName){
            return name.equals(otherName);
        }

        public String toString(){
            return this.name;
        }
    }

    public static enum PAYMENT_TYPE {
        CASH ("CASH"),
        CASHLESS ("CASHLESS");

        private final String name;

        private PAYMENT_TYPE(String s){
            name = s;
        }

        public boolean equalsName(String otherName){
            return name.equals(otherName);
        }

        public String toString(){
            return this.name;
        }
    }

    public static enum PAYMENT_CASHLESS {
        BCA ("BCA"),
        BRI ("BRI"),
        MANDIRI ("MANDIRI"),
        CIMB ("CIMB"),
        OVO ("OVO"),
        DANA ("DANA"),
        GOPAY ("GOPAY");

        private final String name;

        private PAYMENT_CASHLESS(String s){
            name = s;
        }

        public boolean equalsName(String otherName){
            return name.equals(otherName);
        }

        public String toString(){
            return this.name;
        }
    }
}
