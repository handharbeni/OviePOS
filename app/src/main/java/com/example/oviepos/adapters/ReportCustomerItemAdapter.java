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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportCustomerItemAdapter extends RecyclerView.Adapter<ReportCustomerItemAdapter.ViewHolder> {
    private Context context;
    private List<TransactionItems> listItems;

    public ReportCustomerItemAdapter(Context context, List<TransactionItems> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_report_item_customer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionItems transactionItems = listItems.get(position);
        holder.bindData(transactionItems);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtProductName)
        TextView txtProductName;
        @BindView(R.id.txtProductPrice)
        TextView txtProductPrice;
        @BindView(R.id.txtProductQty)
        TextView txtProductQty;
        @BindView(R.id.txtSubTotal)
        TextView txtSubTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData(TransactionItems transactionItems){
            txtProductName.setText(transactionItems.getProductName());
            txtProductPrice.setText(
                    Utils.formatRupiah(
                            Integer.parseInt(
                                    transactionItems.getProductPrice()
                            )
                    )
            );
            txtProductQty.setText(String.valueOf(transactionItems.getQty()));
            txtSubTotal.setText(
                    Utils.formatRupiah(
                            Integer.parseInt(
                                    transactionItems.getProductPrice()
                            ) * transactionItems.getQty()
                    )
            );
        }
    }
}
