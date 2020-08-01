package com.example.oviepos.views;

import com.example.oviepos.databases.models.responses.TransactionItems;
import com.example.oviepos.databases.models.responses.Transactions;
import com.manishkprboilerplate.base.UiView;

import java.util.HashMap;
import java.util.List;

public interface ReportUIView {
    interface ReportView extends UiView {
        void onGenerateReportTransactionSucess(List<HashMap<String, List<TransactionItems>>> listReportItems);

        void onGenerateReportCustomerSuccess(List<HashMap<Transactions, List<TransactionItems>>> listReportItems);

        void onGenerateReportFailed(String message);

        void onUploadSuccess();

        void onUploading();
    }

    interface ReportPresenter {
        void init();

        void doGenerateReport(String report);

        void createTransactionReport(List<HashMap<String, List<TransactionItems>>> listReportItems);

        void createCustomerReport(List<HashMap<Transactions, List<TransactionItems>>> listReportItems);
    }
}
