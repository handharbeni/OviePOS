package com.example.oviepos.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.jibble.simpleftp.SimpleFTP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
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

    public static void generateLogTransaction(Context context, String sFileName, String sBody) {
        File dir = new File(context.getFilesDir(), "Jatim-Pintar");
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            File gpxfile = new File(dir, sFileName);
            if (!gpxfile.exists()) {
                gpxfile.createNewFile();
            }
            Log.d("Utils", "generateLogTransaction: " + gpxfile);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<File> getFiles(Context context) {
        File dir = new File(context.getFilesDir(), "Jatim-Pintar");
        try {
            return Arrays.asList(dir.listFiles());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void uploadToFtp(List<File> listFile) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect("files.000webhost.com");
        ftpClient.login("jatimpintar", "nollimakali86");
        ftpClient.cwd("/public_html/FileLogTransaction/");
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        for (File file : listFile){
            FileInputStream fileInputStream = new FileInputStream(file);
            ftpClient.storeFile(file.getName(), fileInputStream);
//                ftpClient.stor(file.getPath());
            fileInputStream.close();
        }
        ftpClient.logout();
        ftpClient.disconnect();
    }

    public static void uploadToFtp(File file) {
        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect("files.000webhost.com");
            ftpClient.login("jatimpintar", "nollimakali86");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.stor(file.getPath());
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ProgressDialog progressDialog(Activity activity, String message){
        ProgressDialog pd = new ProgressDialog(activity);
        pd.setMessage(message);
        return pd;
    }
}