package com.example.oviepos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oviepos.R;
import com.example.oviepos.databases.models.responses.TransactionItems;
import com.example.oviepos.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.internal.Util;

public class ReportTransactionAdapter extends RecyclerView.Adapter<ReportTransactionAdapter.ViewHolder> {
    private Context context;
    private List<HashMap<String, List<TransactionItems>>> listReport;
    private ReportTransactionListener reportTransactionListener;

    public ReportTransactionAdapter(Context context,
                                    List<HashMap<String, List<TransactionItems>>> listReport,
                                    ReportTransactionListener reportTransactionListener) {
        this.context = context;
        this.listReport = listReport;
        this.reportTransactionListener = reportTransactionListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.fragment_report_item_product,
                parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<String, List<TransactionItems>> currentItem = listReport.get(position);
        holder.bindData(currentItem);
        holder.itemView.setOnClickListener(view -> {
            reportTransactionListener.onTransactionClick();
        });
    }

    @Override
    public int getItemCount() {
        return listReport.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtNamaProduk)
        TextView txtNamaProduk;
        @BindView(R.id.txtSellQTY)
        TextView txtSellQTY;
        @BindView(R.id.txtSellPrice)
        TextView txtSellPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData(HashMap<String, List<TransactionItems>> listItems){
            for (Map.Entry<String, List<TransactionItems>> hmListItems : listItems.entrySet()){
                txtNamaProduk.setText(hmListItems.getKey());
                int totalPrice = 0;
                int totalQty = 0;
                for (TransactionItems itm : hmListItems.getValue()){
                    totalPrice += Integer.parseInt(itm.getProductPrice()) * itm.getQty();
                    totalQty += itm.getQty();
                }

                txtSellQTY.setText(String.valueOf(totalQty));
                txtSellPrice.setText(Utils.formatRupiah(totalPrice));
            }
        }
    }

    public interface ReportTransactionListener{
        void onTransactionClick();
    }
}
