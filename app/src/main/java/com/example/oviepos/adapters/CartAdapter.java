package com.example.oviepos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oviepos.R;
import com.example.oviepos.databases.models.responses.Cart;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private List<Cart> listCart;
    private CartAdapterInterface cartAdapterInterface;

    public CartAdapter(Context context, List<Cart> listCart, CartAdapterInterface cartAdapterInterface) {
        this.context = context;
        this.listCart = listCart;
        this.cartAdapterInterface = cartAdapterInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = listCart.get(position);
        holder.bindData(cart);
        holder.btnAddQty.setOnClickListener(view -> cartAdapterInterface.onAddClick(cart));
        holder.btnRemoveQty.setOnClickListener(view -> cartAdapterInterface.onRemoveClick(cart));
    }

    @Override
    public int getItemCount() {
        return listCart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtProductImage)
        AppCompatImageView txtProductImage;
        @BindView(R.id.txtProductName)
        AppCompatTextView txtProductName;
        @BindView(R.id.txtProductPrice)
        AppCompatTextView txtProductPrice;
        @BindView(R.id.btnAddQty)
        AppCompatImageButton btnAddQty;
        @BindView(R.id.btnRemoveQty)
        AppCompatImageButton btnRemoveQty;
        @BindView(R.id.txtQty)
        AppCompatEditText txtQty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Cart cart) {
            txtProductName.setText(cart.getProductName());
            txtProductPrice.setText(cart.getProductPrice());
            txtQty.setText(String.valueOf(cart.getQty()));
        }
    }

    public void update(List<Cart> listCart) {
        this.listCart.clear();
        this.listCart = listCart;
        notifyDataSetChanged();
    }

    public interface CartAdapterInterface {
        void onAddClick(Cart cart);

        void onRemoveClick(Cart cart);
    }
}
