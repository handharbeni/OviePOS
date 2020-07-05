package com.example.oviepos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.oviepos.R;
import com.example.oviepos.databases.models.responses.Products;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private Context context;
    private List<Products> listProducts = new ArrayList<>();
    private ProductsAdapterCallback productsAdapterCallback;

    public ProductsAdapter(Context context, List<Products> listProducts, ProductsAdapterCallback productsAdapterCallback) {
        this.context = context;
        this.listProducts = listProducts;
        this.productsAdapterCallback = productsAdapterCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext())
                .inflate(
                        R.layout.item_product,
                        parent,
                        false
                );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products products = listProducts.get(position);
        holder.bindData(products);
        holder.itemView.setOnClickListener(view -> {
            productsAdapterCallback.onProductsClick(products);
        });
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.productImage)
        ImageView productImage;
        @BindView(R.id.productTitle)
        TextView productTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Products products){
            Glide.with(itemView).load(products.getProductImage()).into(productImage);
            productTitle.setText(products.getProductName());
        }
    }

    public void update(List<Products> listProducts){
        this.listProducts.clear();
        this.listProducts = listProducts;
        notifyDataSetChanged();
    }

    public interface ProductsAdapterCallback{
        void onProductsClick(Products products);
    }
}
