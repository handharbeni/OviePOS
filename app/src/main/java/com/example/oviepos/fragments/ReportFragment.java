package com.example.oviepos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oviepos.R;
import com.example.oviepos.adapters.ReportCustomerAdapter;
import com.example.oviepos.adapters.ReportTransactionAdapter;
import com.example.oviepos.databases.models.responses.TransactionItems;
import com.example.oviepos.databases.models.responses.Transactions;
import com.example.oviepos.presenters.ReportPresenter;
import com.example.oviepos.print_helper.PrinterCommands;
import com.example.oviepos.utils.BaseFragments;
import com.example.oviepos.utils.Constants;
import com.example.oviepos.utils.Utils;
import com.example.oviepos.views.ReportUIView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

import static com.example.oviepos.MainActivity.isPrinterReady;
import static com.example.oviepos.MainActivity.mService;

public class ReportFragment extends BaseFragments
        implements ReportUIView.ReportView, ReportTransactionAdapter.ReportTransactionListener, ReportCustomerAdapter.ReportCustomerInterface {

    private ReportPresenter reportPresenter;
    private View view;

    @BindView(R.id.typeReport)
    AppCompatSpinner typeReport;
    @BindView(R.id.rvReport)
    RecyclerView rvReport;

    private ReportTransactionAdapter reportTransactionAdapter;
    private ReportCustomerAdapter reportCustomerAdapter;

    public static ReportFragment getInstance(){
        return new ReportFragment();
    }

    public ReportFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report, container, false);
        init();
        return view;
    }

    void init(){
        ButterKnife.bind(this, view);
        reportPresenter = new ReportPresenter(Objects.requireNonNull(getActivity()), this);
        reportPresenter.attachView(this);

        reportPresenter.init();
        typeReport.setSelection(0, true);
    }

    @OnItemSelected(R.id.typeReport)
    public void reportSelected(AppCompatSpinner spinner, int position){
        reportPresenter.doGenerateReport(
                position==0?
                        Constants.REPORT_TYPE.TRANSACTION.toString():
                        Constants.REPORT_TYPE.CUSTOMER.toString()
        );
    }

    @Override
    public void onGenerateReportTransactionSucess(List<HashMap<String, List<TransactionItems>>> listReportItems) {
        rvReport.removeAllViewsInLayout();
        rvReport.requestLayout();
        reportTransactionAdapter = new ReportTransactionAdapter(getActivity().getApplicationContext(),
                listReportItems, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rvReport.setLayoutManager(linearLayoutManager);
        rvReport.setAdapter(reportTransactionAdapter);
    }

    @Override
    public void onGenerateReportCustomerSuccess(List<HashMap<Transactions, List<TransactionItems>>> listReportItems) {
        rvReport.removeAllViewsInLayout();
        rvReport.requestLayout();
        reportCustomerAdapter = new ReportCustomerAdapter(getActivity().getApplicationContext(),
                listReportItems, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rvReport.setLayoutManager(layoutManager);
        rvReport.setAdapter(reportCustomerAdapter);
    }

    @Override
    public void onGenerateReportFailed(String message) {

    }

    @Override
    public void onTransactionClick() {

    }

    @Override
    public void onItemViewClick(HashMap<Transactions, List<TransactionItems>> currentItem) {

    }

    @Override
    public void onPrintClick(HashMap<Transactions, List<TransactionItems>> currentItem) {
        for (Map.Entry<Transactions, List<TransactionItems>> hmListItems : currentItem.entrySet()){
            Transactions transactions = hmListItems.getKey();
            List<TransactionItems> listItems = hmListItems.getValue();
            if (isPrinterReady){
                mService.write(PrinterCommands.ESC_ALIGN_CENTER);
                mService.sendMessage(getActivity().getApplicationContext().getString(R.string.app_name), "");
                mService.write(PrinterCommands.ESC_ALIGN_RIGHT);
                mService.sendMessage("Nota Copy", "");
                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
                mService.sendMessage("Customer : "+transactions.getCustomerName(), "");
                mService.sendMessage("Date : "+ Utils.getDateFromMillis(transactions.getDateNow()), "");
                mService.sendMessage(PrinterCommands.dashLine, "");
                int total = 0;
                for (TransactionItems items : listItems){
                    mService.sendMessage(items.getProductName()+" ("+Utils.formatRupiah(Integer.parseInt(items.getProductPrice()))+")", "");
                    int subTotal = Integer.parseInt(items.getProductPrice()) * items.getQty();
                    total += subTotal;
                    mService.sendMessage(Utils.justifyPrintLine("x "+items.getQty(), Utils.formatRupiah(subTotal)), "");
                }
                mService.sendMessage(PrinterCommands.dashLine, "");
                mService.sendMessage(Utils.justifyPrintLine("TOTAL", Utils.formatRupiah(total)), "");
                mService.write(PrinterCommands.ESC_ALIGN_CENTER);
                mService.sendMessage("TERIMA KASIH", "");
                mService.write(PrinterCommands.ESC_ENTER);
                mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            }

        }
    }
}
