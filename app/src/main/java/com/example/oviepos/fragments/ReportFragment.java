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
import com.example.oviepos.adapters.ReportTransactionAdapter;
import com.example.oviepos.databases.models.responses.TransactionItems;
import com.example.oviepos.presenters.ReportPresenter;
import com.example.oviepos.utils.BaseFragments;
import com.example.oviepos.utils.Constants;
import com.example.oviepos.views.ReportUIView;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

public class ReportFragment extends BaseFragments implements ReportUIView.ReportView, ReportTransactionAdapter.ReportTransactionListener {

    private ReportPresenter reportPresenter;
    private View view;

    @BindView(R.id.typeReport)
    AppCompatSpinner typeReport;
    @BindView(R.id.rvReport)
    RecyclerView rvReport;

    private ReportTransactionAdapter reportTransactionAdapter;

    public static ReportFragment getInstance(){
        return new ReportFragment();
    }

    public ReportFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        reportTransactionAdapter = new ReportTransactionAdapter(getActivity().getApplicationContext(),
                listReportItems, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rvReport.setLayoutManager(linearLayoutManager);
        rvReport.setAdapter(reportTransactionAdapter);
    }

    @Override
    public void onGenerateReportFailed(String message) {

    }

    @Override
    public void onTransactionClick() {

    }
}
