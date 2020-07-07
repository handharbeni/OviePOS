package com.example.oviepos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.oviepos.R;
import com.example.oviepos.fragments.bottomsheets.PaymentBottomsheet;
import com.example.oviepos.utils.BaseFragments;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderFragment extends BaseFragments {
    private View view;

    @BindView(R.id.frameProduct)
    FrameLayout frameProduct;
    @BindView(R.id.frameCart)
    FrameLayout frameCart;
    @BindView(R.id.btnNextPayment)
    MaterialButton btnNextPayment;

    public static OrderFragment getInstance() {
        return new OrderFragment();
    }

    public OrderFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        init();
        return view;
    }

    void init() {
        ButterKnife.bind(this, view);
        fillFragment(R.id.frameProduct, ProductFragment.getInstance(true));
        fillFragment(R.id.frameCart, CartFragment.getInstance());
    }

    private void fillFragment(int frame, Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(frame, fragment);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.btnNextPayment)
    public void nextToPayment(){
        PaymentBottomsheet paymentBottomsheet = PaymentBottomsheet.getInstance(
                getActivity().getApplicationContext(),
                ""
                );
        paymentBottomsheet.showNow(getChildFragmentManager(), paymentBottomsheet.getTag());
    }

}
