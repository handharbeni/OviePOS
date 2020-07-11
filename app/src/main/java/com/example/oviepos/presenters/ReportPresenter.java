package com.example.oviepos.presenters;

import android.app.Activity;

import androidx.lifecycle.LifecycleOwner;

import com.example.oviepos.databases.AppDB;
import com.example.oviepos.databases.models.responses.Products;
import com.example.oviepos.databases.models.responses.TransactionItems;
import com.example.oviepos.utils.Constants;
import com.example.oviepos.views.ReportUIView;
import com.manishkprboilerplate.base.BasePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        List<TransactionItems> transactionItems = appDB.transactions().getAllTransactionItems();
        List<Products> listProduct = appDB.products().getAll();
        if (Constants.REPORT_TYPE.TRANSACTION.equalsName(report)) {
            List<HashMap<String, List<TransactionItems>>> listReport = new ArrayList<>();
            for (Products products : listProduct){
                HashMap<String, List<TransactionItems>> listHashMap = new HashMap<>();
                listHashMap.put(String.valueOf(products.getProductName()), appDB.transactions().getItems(products.getId()));
                listReport.add(listHashMap);
            }
            getMvpView().onGenerateReportTransactionSucess(listReport);
        } else if (Constants.REPORT_TYPE.CUSTOMER.equalsName(report)){

        }
    }
}
