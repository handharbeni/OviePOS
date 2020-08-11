package com.example.oviepos.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oviepos.R;
import com.example.oviepos.adapters.CartAdapter;
import com.example.oviepos.databases.models.responses.Cart;
import com.example.oviepos.presenters.CartPresenter;
import com.example.oviepos.utils.BaseFragments;
import com.example.oviepos.utils.Constants;
import com.example.oviepos.utils.Utils;
import com.example.oviepos.views.CartUIView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class CartFragment extends BaseFragments implements CartUIView, CartAdapter.CartAdapterInterface {
    private View view;
    private CartPresenter cartPresenter;
    private CartAdapter cartAdapter;
    private LinearLayoutManager linearLayoutManager;

    CartCallback cartCallback;


    @BindView(R.id.listCart) RecyclerView listCart;
    @BindView(R.id.txtSubTotal) AppCompatTextView txtSubTotal;

    @BindView(R.id.txtCustomer) SearchView txtCustomer;

    @BindView(R.id.txtDiscountType) AppCompatSpinner txtDiscountType;
    @BindView(R.id.txtDiscountValue) AppCompatEditText txtDiscountValue;
    @BindView(R.id.txtTotalPPN) AppCompatTextView txtTotalPPN;

    private List<Cart> listCarts = new ArrayList<>();

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
            this.listCarts = listCarts;
            cartAdapter = new CartAdapter(getActivity().getApplicationContext(), listCarts, this, false);
            linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

            listCart.setLayoutManager(linearLayoutManager);
            listCart.setAdapter(cartAdapter);
        }
    }

    @Override
    public void updateData(List<Cart> listCart) {
        if (listCart != null) {
            this.listCarts = listCart;

            int total = 0;
            Double ppn = 0.0;
            Double discount = 0.0;
            int discountType = 0;
            for (Cart cart : listCart) {
                total += cart.getQty() * Integer.parseInt(cart.getProductPrice());
            }


            // TODO : PPN
            ppn = (((double) Constants.PPN_PERCENT) / 100) * (double) total;
            cartCallback.onPPNChange(String.valueOf(ppn));
            total += ppn.intValue();

            try {
                // TODO : Discount
                discount = Double.valueOf(txtDiscountValue.getText().toString());
                discountType = txtDiscountType.getSelectedItemPosition();
                switch (discountType){
                    case 0 :
                        cartCallback.onDiscountTypeChange(Constants.DISCOUNT_TYPE.NOMINAL_VALUE.toString());
                        cartCallback.onDiscountValueChange(String.valueOf(discount.intValue()));
                        total -= discount.intValue();
                        break;
                    case 1 :
                        cartCallback.onDiscountTypeChange(Constants.DISCOUNT_TYPE.PERCENT_VALUE.toString());
                        double totalDiscount = (discount / 100) * total;
                        cartCallback.onDiscountValueChange(String.valueOf((int) totalDiscount));
                        total -= (int) totalDiscount;
                        break;
                }
            } catch (NumberFormatException | NullPointerException e){
                e.printStackTrace();
            }

            txtTotalPPN.setText(Utils.formatRupiah(ppn.longValue()));
            txtSubTotal.setText(Utils.formatRupiah(total));
            cartAdapter.update(listCart);
        }
    }

    void updateAttribute(List<Cart> listCarts){
        int total = 0;
        Double ppn = 0.0;
        Double discount = 0.0;
        int discountType = 0;
        for (Cart cart : listCarts) {
            total += cart.getQty() * Integer.parseInt(cart.getProductPrice());
        }


        // TODO : PPN
        ppn = (((double) Constants.PPN_PERCENT) / 100) * (double) total;
        cartCallback.onPPNChange(String.valueOf(ppn));
        total += ppn.intValue();

        try {
            // TODO : Discount
            discount = Double.valueOf(txtDiscountValue.getText().toString());
            discountType = txtDiscountType.getSelectedItemPosition();
            switch (discountType){
                case 0 :
                    cartCallback.onDiscountTypeChange(Constants.DISCOUNT_TYPE.NOMINAL_VALUE.toString());
                    cartCallback.onDiscountValueChange(String.valueOf(discount.intValue()));
                    total -= discount.intValue();
                    break;
                case 1 :
                    cartCallback.onDiscountTypeChange(Constants.DISCOUNT_TYPE.PERCENT_VALUE.toString());
                    double totalDiscount = (discount / 100) * total;
                    cartCallback.onDiscountValueChange(String.valueOf((int) totalDiscount));
                    total -= (int) totalDiscount;
                    break;
            }
        } catch (NumberFormatException | NullPointerException e){
            e.printStackTrace();
        }

        txtTotalPPN.setText(Utils.formatRupiah(ppn.longValue()));
        txtSubTotal.setText(Utils.formatRupiah(total));
    }

    @OnTextChanged(R.id.txtDiscountValue)
    public void onDiscountChange(){
        updateAttribute(this.listCarts);
    }

    @OnItemSelected(R.id.txtDiscountType)
    public void onDiscoutTypeSelected(Spinner spinner, int position){
        updateAttribute(this.listCarts);
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
