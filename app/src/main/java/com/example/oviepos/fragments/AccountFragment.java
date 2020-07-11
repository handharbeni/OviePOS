package com.example.oviepos.fragments;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.oviepos.MainActivity;
import com.example.oviepos.R;
import com.example.oviepos.presenters.AccountPresenter;
import com.example.oviepos.print_helper.DeviceActivity;
import com.example.oviepos.print_helper.PrinterCommands;
import com.example.oviepos.utils.BaseFragments;
import com.example.oviepos.callbacks.AccountCallback;
import com.example.oviepos.utils.Utils;
import com.example.oviepos.views.AccountUIView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.oviepos.MainActivity.RC_CONNECT_DEVICE;
import static com.example.oviepos.MainActivity.RC_ENABLE_BLUETOOTH;
import static com.example.oviepos.MainActivity.isPrinterReady;
import static com.example.oviepos.MainActivity.mService;

public class AccountFragment extends BaseFragments implements AccountUIView {
    private AccountCallback accountCallback;
    private AccountPresenter accountPresenter;
    private View view;

    public static String PAIRED_DEVICE = "DISCONNECT PRINTER";
    public static String UNPAIRED_DEVICE = "CONNECT PRINTER";

    @BindView(R.id.txtUsername)
    TextView txtUsername;
    @BindView(R.id.btnLogout)
    Button btnLogout;
    @BindView(R.id.btnPairUnpair)
    Button btnPairUnpair;

    public static AccountFragment getInstance() {
        return new AccountFragment();
    }

    public AccountFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);
        init();
        return view;
    }

    void init() {
        ButterKnife.bind(this, view);
        accountPresenter = new AccountPresenter(getActivity(), this);
        accountPresenter.attachView(this);

        accountPresenter.init();
        btnPairUnpair.setText(isPrinterReady?PAIRED_DEVICE:UNPAIRED_DEVICE);
    }

    public void updateButton(String text){
        btnPairUnpair.setText(text);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            accountCallback = (AccountCallback) getActivityInstance(getContext());
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivityInstance(context).toString() + " must implement Callback");
        }
    }

    @OnClick(R.id.btnLogout)
    public void doLogout() {
        accountPresenter.doLogout();
    }

    @OnClick(R.id.btnPairUnpair)
    public void pairUnpairBT() {
        if (!isPrinterReady) {
            getActivity().startActivityForResult(new Intent(getActivity(), DeviceActivity.class), RC_CONNECT_DEVICE);
        }
    }

    @Override
    public void fillProfile(String username) {
        txtUsername.setText(username);
    }

    @Override
    public void logoutSuccess() {
        accountCallback.logoutSuccess();
    }
}
