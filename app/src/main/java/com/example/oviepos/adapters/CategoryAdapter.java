package com.example.oviepos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oviepos.R;
import com.example.oviepos.databases.models.responses.ProductsCategory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context context;
    private List<ProductsCategory> listCategory;
    private InterfaceCategoryAdapter interfaceCategoryAdapter;

    public CategoryAdapter(Context context, List<ProductsCategory> listCategory, InterfaceCategoryAdapter interfaceCategoryAdapter) {
        this.context = context;
        this.listCategory = listCategory;
        this.interfaceCategoryAdapter = interfaceCategoryAdapter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductsCategory productsCategory = listCategory.get(position);
        holder.bindData(productsCategory);
        holder.itemView.setOnClickListener(view -> {
            interfaceCategoryAdapter.onCategoryClick(productsCategory);
        });
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public void updateData(List<ProductsCategory> listCategory){
        this.listCategory.clear();
        this.listCategory = listCategory;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemCategory)
        TextView itemCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ProductsCategory category){
            itemCategory.setText(category.getCategoryName());
        }
    }

    public interface InterfaceCategoryAdapter{
        void onCategoryClick(ProductsCategory productsCategory);
    }
}
