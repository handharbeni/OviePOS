package com.example.oviepos.presenters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.SpannedString;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import com.example.oviepos.databases.AppDB;
import com.example.oviepos.databases.models.responses.Products;
import com.example.oviepos.databases.models.responses.TransactionItems;
import com.example.oviepos.databases.models.responses.Transactions;
import com.example.oviepos.utils.Constants;
import com.example.oviepos.utils.Utils;
import com.example.oviepos.views.ReportUIView;
import com.manishkprboilerplate.base.BasePresenter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportPresenter extends BasePresenter<ReportUIView.ReportView> implements ReportUIView.ReportPresenter {
    private Activity activity;
    private LifecycleOwner lifecycleOwner;
    private AppDB appDB;

    public ReportPresenter(Activity activity, LifecycleOwner lifecycleOwner) {
        this.activity = activity;
        this.lifecycleOwner = lifecycleOwner;
        appDB = AppDB.getInstance(activity.getApplicationContext());
    }

    @Override
    public void attachView(ReportUIView.ReportView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void init() {

    }

    @Override
    public void doGenerateReport(String report) {
        List<Transactions> transactions = appDB.transactions().getAll();
        List<TransactionItems> transactionItems = appDB.transactions().getAllTransactionItems();
        List<Products> listProduct = appDB.products().getAll();
        if (Constants.REPORT_TYPE.TRANSACTION.equalsName(report)) {
            List<HashMap<String, List<TransactionItems>>> listReport = new ArrayList<>();
            for (Products products : listProduct) {
                HashMap<String, List<TransactionItems>> listHashMap = new HashMap<>();
                listHashMap.put(String.valueOf(products.getProductName()), appDB.transactions().getItems(products.getId()));
                listReport.add(listHashMap);
            }
            getMvpView().onGenerateReportTransactionSucess(listReport);
        } else if (Constants.REPORT_TYPE.CUSTOMER.equalsName(report)) {
            List<HashMap<Transactions, List<TransactionItems>>> listReport = new ArrayList<>();
            for (Transactions tr : transactions) {
                HashMap<Transactions, List<TransactionItems>> listHashMap = new HashMap<>();
                listHashMap.put(tr, appDB.transactions().getItemsByTransaction(tr.getId()));
                listReport.add(listHashMap);
            }
            getMvpView().onGenerateReportCustomerSuccess(listReport);
        }
    }

    @Override
    public void createTransactionReport(List<HashMap<String, List<TransactionItems>>> listReportItems) {
        try {
            SpannableStringBuilder sBody = new SpannableStringBuilder();
            for (HashMap<String, List<TransactionItems>> listReportItem : listReportItems) {
                for (Map.Entry<String, List<TransactionItems>> listReport : listReportItem.entrySet()){
                    sBody.append(listReport.getKey()+" : ");
                    sBody.append("\n");
                    for (TransactionItems items : listReport.getValue()){
                        sBody.append(items.toString());
                        sBody.append("\n");
                    }
                }
            }

            long currentMillis = System.currentTimeMillis();
            File file = Utils.generateLogTransaction(
                    activity.getApplicationContext(),
                    "TransactionReport-{"+String.valueOf(currentMillis)+"}.txt",
                    sBody.toString()
            );

            Log.d(TAG, "createTransactionReport: "+file);

            new UploadLog().execute(file);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void createCustomerReport(List<HashMap<Transactions, List<TransactionItems>>> listReportItems) {
        try {
            SpannableStringBuilder sBody = new SpannableStringBuilder();
            for (HashMap<Transactions, List<TransactionItems>> listReportItem : listReportItems){
                for (Map.Entry<Transactions, List<TransactionItems>> listReport : listReportItem.entrySet()){
                    sBody.append(listReport.getKey().toString());
                    sBody.append("\n");
                    for (TransactionItems items : listReport.getValue()){
                        sBody.append(items.toString());
                        sBody.append("\n");
                    }
                }
            }

            long currentMillis = System.currentTimeMillis();
            File file = Utils.generateLogTransaction(
                    activity.getApplicationContext(),
                    "CustomerTransactions-{"+String.valueOf(currentMillis)+"}.txt",
                    sBody.toString());

            Log.d(TAG, "createTransactionReport: "+file);

            new UploadLog().execute(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class UploadLog extends AsyncTask<File, Integer, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPostExecute(String s) {
            // sesudah
            super.onPostExecute(s);
            getMvpView().onUploadSuccess();
        }

        @Override
        protected String doInBackground(File... files) {
            Utils.uploadToFtp(files[0]);
            return null;
        }

        @Override
        protected void onPreExecute() {
            // sebelum
            super.onPreExecute();
            getMvpView().onUploading();
        }
    }
}
