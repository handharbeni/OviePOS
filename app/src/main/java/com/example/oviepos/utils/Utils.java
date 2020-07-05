package com.example.oviepos.utils;

import java.util.Calendar;

public class Utils {
    public static String getTimeFromMillis(long millis){
        Calendar calendarOut = Calendar.getInstance();
        calendarOut.setTimeInMillis(millis);

        int hour = calendarOut.get(Calendar.HOUR);
        int minute = calendarOut.get(Calendar.MINUTE);

        String sHour = String.format("%s%s", String.valueOf(hour).length()<2?"0":"",
                String.valueOf(hour));
        String sMinute = String.format("%s%s", String.valueOf(minute).length()<2?"0":"",
                String.valueOf(minute));

        return String.format("%s:%s", sHour, sMinute);
    }


    public static String getDateFromMillis(long millis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        String sYear = String.format("%s%s", String.valueOf(mYear).length()<2?"0":"",
                String.valueOf(mYear));
        String sMonth = String.format("%s%s", String.valueOf(mMonth).length()<2?"0":"",
                String.valueOf(mMonth));
        String sDay = String.format("%s%s", String.valueOf(mDay).length()<2?"0":"",
                String.valueOf(mDay));

        return String.format("%s/%s/%s", sDay, sMonth, sYear);
    }
}