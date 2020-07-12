package com.example.oviepos.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oviepos.R;
import com.example.oviepos.adapters.CartAdapter;
import com.example.oviepos.databases.models.responses.Cart;
import com.example.oviepos.presenters.CartPresenter;
import com.example.oviepos.utils.BaseFragments;
import com.example.oviepos.utils.Utils;
import com.example.oviepos.views.CartUIView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class CartFragment extends BaseFragments implements CartUIView, CartAdapter.CartAdapterInterface {
    private View view;
    private CartPresenter cartPresenter;
    private CartAdapter cartAdapter;
    private LinearLayoutManager linearLayoutManager;

    CartCallback cartCallback;


    @BindView(R.id.listCart)
    RecyclerView listCart;
    @BindView(R.id.txtSubTotal)
    AppCompatTextView txtSubTotal;

    @BindView(R.id.txtCustomer)
    SearchView txtCustomer;

    String customerName = "";

    public static CartFragment getInstance(String customerName, CartCallback cartCallback) {
        return new CartFragment(customerName, cartCallback);
    }

    public CartFragment(String customerName, CartCallback cartCallback) {
        this.customerName = customerName;
        this.cartCallback = cartCallback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        init();
        return view;
    }

    void init() {
        ButterKnife.bind(this, view);
        cartPresenter = new CartPresenter(getActivity(), this);
        cartPresenter.attachView(this);

        cartPresenter.init();

        txtCustomer.setQuery(customerName, false);
        txtCustomer.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                cartCallback.onCustomerNameChange(newText);
                return false;
            }
        });
    }


    @Override
    public void showAllCart(List<Cart> listCarts) {
        if (listCarts != null) {
            cartAdapter = new CartAdapter(getActivity().getApplicationContext(), listCarts, this, false);
            linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

            listCart.setLayoutManager(linearLayoutManager);
            listCart.setAdapter(cartAdapter);
        }
    }

    @Override
    public void updateData(List<Cart> listCart) {
        if (listCart != null) {
            int total = 0;
            for (Cart cart : listCart) {
                total += cart.getQty() * Integer.parseInt(cart.getProductPrice());
            }
            txtSubTotal.setText(Utils.formatRupiah(total));
            cartAdapter.update(listCart);
        }
    }

    @Override
    public void onAddClick(Cart cart) {
        cartPresenter.addQty(cart);
    }

    @Override
    public void onRemoveClick(Cart cart) {
        cartPresenter.removeQty(cart);
    }

    @Override
    public void onDestroy() {
        cartPresenter.detachView();
        super.onDestroy();
    }
}
