package com.example.oviepos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oviepos.R;
import com.example.oviepos.databases.models.responses.TransactionItems;
import com.example.oviepos.databases.models.responses.Transactions;
import com.example.oviepos.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportCustomerAdapter extends RecyclerView.Adapter<ReportCustomerAdapter.ViewHolder> {
    private Context context;
    private List<HashMap<Transactions, List<TransactionItems>>> listReport;
    private ReportCustomerInterface reportCustomerInterface;

    public ReportCustomerAdapter(
            Context context,
            List<HashMap<Transactions, List<TransactionItems>>> listReport,
            ReportCustomerInterface reportCustomerInterface) {
        this.context = context;
        this.listReport = listReport;
        this.reportCustomerInterface = reportCustomerInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_report_item_customer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<Transactions, List<TransactionItems>> currentItem = listReport.get(position);
        holder.bindData(currentItem);
        holder.btnPrint.setOnClickListener(view -> {
            reportCustomerInterface.onPrintClick(currentItem);
        });
    }

    @Override
    public int getItemCount() {
        return listReport.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtCustomerName)
        TextView txtCustomerName;
        @BindView(R.id.txtCustomerDate)
        TextView txtCustomerDate;
        @BindView(R.id.rvItemTransaction)
        RecyclerView rvItemTransaction;
        @BindView(R.id.txtTotal)
        TextView txtTotal;
        @BindView(R.id.btnPrint)
        Button btnPrint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(HashMap<Transactions, List<TransactionItems>> listItems) {
            for (Map.Entry<Transactions, List<TransactionItems>> hmListItems : listItems.entrySet()) {
                Transactions transactions = hmListItems.getKey();
                txtCustomerName.setText(transactions.getCustomerName());
                txtCustomerDate.setText(Utils.getDateFromMillis(transactions.getDateNow()));
                int totalPrice = 0;

                List<TransactionItems> listItem = hmListItems.getValue();
                for (TransactionItems itm : listItem) {
                    totalPrice += Integer.parseInt(itm.getProductPrice()) * itm.getQty();
                }
                ReportCustomerItemAdapter reportCustomerItemAdapter =
                        new ReportCustomerItemAdapter(context, listItem);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                rvItemTransaction.setLayoutManager(linearLayoutManager);
                rvItemTransaction.setAdapter(reportCustomerItemAdapter);

                txtTotal.setText(Utils.formatRupiah(totalPrice));
            }
        }
    }

    public interface ReportCustomerInterface {
        void onItemViewClick(HashMap<Transactions, List<TransactionItems>> currentItem);

        void onPrintClick(HashMap<Transactions, List<TransactionItems>> currentItem);
    }
}
