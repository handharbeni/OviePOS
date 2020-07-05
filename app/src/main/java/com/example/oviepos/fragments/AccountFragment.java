package com.example.oviepos.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.oviepos.R;
import com.example.oviepos.presenters.AccountPresenter;
import com.example.oviepos.utils.BaseFragments;
import com.example.oviepos.callbacks.AccountCallback;
import com.example.oviepos.views.AccountUIView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountFragment extends BaseFragments implements AccountUIView {
    private AccountCallback accountCallback;
    private AccountPresenter accountPresenter;
    private View view;

    @BindView(R.id.txtUsername)
    TextView txtUsername;
    @BindView(R.id.btnLogout)
    Button btnLogout;

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

    @Override
    public void fillProfile(String username) {
        txtUsername.setText(username);
    }

    @Override
    public void logoutSuccess() {
        accountCallback.logoutSuccess();
    }
}
