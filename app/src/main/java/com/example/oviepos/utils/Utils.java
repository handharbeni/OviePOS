package com.example.oviepos.utils;

import android.annotation.SuppressLint;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Utils {
    public static String getTimeFromMillis(long millis) {
        Calendar calendarOut = Calendar.getInstance();
        calendarOut.setTimeInMillis(millis);

        int hour = calendarOut.get(Calendar.HOUR);
        int minute = calendarOut.get(Calendar.MINUTE);

        String sHour = String.format("%s%s", String.valueOf(hour).length() < 2 ? "0" : "",
                String.valueOf(hour));
        String sMinute = String.format("%s%s", String.valueOf(minute).length() < 2 ? "0" : "",
                String.valueOf(minute));

        return String.format("%s:%s", sHour, sMinute);
    }


    public static String getDateFromMillis(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        String sYear = String.format("%s%s", String.valueOf(mYear).length() < 2 ? "0" : "",
                String.valueOf(mYear));
        String sMonth = String.format("%s%s", String.valueOf(mMonth).length() < 2 ? "0" : "",
                String.valueOf(mMonth));
        String sDay = String.format("%s%s", String.valueOf(mDay).length() < 2 ? "0" : "",
                String.valueOf(mDay));

        return String.format("%s/%s/%s", sDay, sMonth, sYear);
    }

    public static String formatRupiah(long rupiah) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        return numberFormat.format(rupiah);
    }

    public static String justifyPrintLine(String sLeft, String sRight) {
        int iLeft = sLeft.length();
        int iRight = sRight.length();
        int iTotal = iLeft + iRight;
        int iWhiteSpace = 30 - iTotal;
        StringBuilder whiteSpace = new StringBuilder(" ");
        if (iTotal >= 0) {
            for (int i = 0; i < iWhiteSpace; i++) {
                whiteSpace.append(" ");
            }
        }
        return sLeft + whiteSpace + sRight;
    }

    public static String getDate(long milliSeconds, String dateFormat) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}