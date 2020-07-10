package com.example.oviepos.fragments.bottomsheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oviepos.R;
import com.example.oviepos.adapters.CartAdapter;
import com.example.oviepos.databases.models.responses.Cart;
import com.example.oviepos.databases.models.responses.TransactionItems;
import com.example.oviepos.databases.models.responses.Transactions;
import com.example.oviepos.presenters.PaymentPresenter;
import com.example.oviepos.utils.BaseBottomFragments;
import com.example.oviepos.utils.Constants;
import com.example.oviepos.utils.Utils;
import com.example.oviepos.views.PaymentUIView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class PaymentBottomsheet extends BaseBottomFragments implements PaymentUIView.paymentUIView, CartAdapter.CartAdapterInterface {
    private Context context;
    private String customerName;

    private PaymentPresenter paymentPresenter;

    private CartAdapter cartAdapter;
    private int total = 0;

    @BindView(R.id.listCart)
    RecyclerView listCart;

    @BindView(R.id.panelTransactionType)
    LinearLayout panelTransactionType;
    @BindView(R.id.transactionType)
    AppCompatSpinner transactionType;

    @BindView(R.id.panelPaymentType)
    LinearLayout panelPaymentType;
    @BindView(R.id.paymentType)
    AppCompatSpinner paymentType;

    @BindView(R.id.panelPaymentCashless)
    LinearLayout panelPaymentCashless;
    @BindView(R.id.paymentCashless)
    AppCompatSpinner paymentCashless;

    @BindView(R.id.panelTotal)
    LinearLayout panelTotal;
    @BindView(R.id.paymentTotal)
    MaterialTextView paymentTotal;

    @BindView(R.id.panelPaymentInput)
    LinearLayout panelPaymentInput;
    @BindView(R.id.paymentInput)
    AppCompatEditText paymentInput;

    @BindView(R.id.panelKembalian)
    LinearLayout panelKembalian;
    @BindView(R.id.paymentComeback)
    MaterialTextView paymentComeback;


    @BindView(R.id.btnPay)
    AppCompatButton btnPay;

    private View view;
    private List<Cart> listCarts = new ArrayList<>();

    public static PaymentBottomsheet getInstance(Context context, String customerName) {
        return new PaymentBottomsheet(context, customerName);
    }

    public PaymentBottomsheet(Context context, String customerName) {
        this.context = context;
        this.customerName = customerName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bottomsheet_payment, container, false);
        init();
        return view;
    }

    void init() {
        ButterKnife.bind(this, view);
        paymentPresenter = new PaymentPresenter(getActivity(), this);
        paymentPresenter.attachView(this);

        paymentPresenter.init();
    }

    @Override
    public void initOrderType(List<Constants.TRANSACTION_TYPE> orderType) {
        ArrayAdapter adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item,
                orderType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transactionType.setAdapter(adapter);
    }

    @Override
    public void initPaymentType(List<Constants.PAYMENT_TYPE> paymentsType) {
        ArrayAdapter adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item,
                paymentsType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentType.setAdapter(adapter);
    }

    @OnItemSelected(R.id.paymentType)
    public void onPaymentTypeSelected(AppCompatSpinner spinner, int position) {
        switch (position) {
            case 0:
                panelPaymentCashless.setVisibility(View.GONE);
                break;
            case 1:
                panelPaymentCashless.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnTextChanged(value = R.id.paymentInput, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onInputChange(CharSequence charSequence) {
        int kembalian = 0;
        try {
            String input = charSequence.toString();
            int priceInput = Integer.parseInt(input);

            kembalian = priceInput - total;
        } catch (NumberFormatException e) {
            kembalian = 0;
        }
        paymentComeback.setText(Utils.formatRupiah(kembalian));
    }

    @OnClick(R.id.btnPay)
    public void onPayClick() {
        List<TransactionItems> listItem = new ArrayList<>();
        for (Cart cart : listCarts) {
            TransactionItems items = new TransactionItems();
            items.setProductId(cart.getProductId());
            items.setProductName(cart.getProductName());
            items.setProductPrice(cart.getProductPrice());
            items.setQty(cart.getQty());
            listItem.add(items);
        }
        Transactions transactions = new Transactions();
        transactions.setPaymentType(paymentType.getSelectedItem().toString());
        transactions.setTransactionsType(transactionType.getSelectedItem().toString());
        transactions.setCustomerName(customerName);
        transactions.setDateNow(System.currentTimeMillis());
        transactions.setTimeIn(System.currentTimeMillis());
        transactions.setTimeOut(System.currentTimeMillis());

        paymentPresenter.doPayment(transactions, listItem);
    }

    @Override
    public void initPaymentCashless(List<Constants.PAYMENT_CASHLESS> paymentCashsless) {
        ArrayAdapter adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item,
                paymentCashsless);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentCashless.setAdapter(adapter);
    }

    @Override
    public void initListCart(List<Cart> listCarts) {
        this.listCarts = listCarts;
        cartAdapter = new CartAdapter(getActivity().getApplicationContext(),
                listCarts,
                this,
                true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        listCart.setLayoutManager(linearLayoutManager);
        listCart.setAdapter(cartAdapter);

        for (Cart cart : listCarts) {
            total += (cart.getQty() * Integer.parseInt(cart.getProductPrice()));
        }
        paymentTotal.setText(Utils.formatRupiah(total));
    }

    @Override
    public void onPaymentSuccess() {
        dismiss();
    }

    @Override
    public void onPaymentFailed() {

    }

    @Override
    public void onAddClick(Cart cart) {

    }

    @Override
    public void onRemoveClick(Cart cart) {

    }
}
