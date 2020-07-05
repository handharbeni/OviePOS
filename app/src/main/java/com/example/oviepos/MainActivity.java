package com.example.oviepos;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.oviepos.fragments.AccountFragment;
import com.example.oviepos.fragments.CartFragment;
import com.example.oviepos.fragments.CategoryFragment;
import com.example.oviepos.fragments.OrderFragment;
import com.example.oviepos.fragments.ProductFragment;
import com.example.oviepos.presenters.MainPresenter;
import com.example.oviepos.utils.BaseActivity;
import com.example.oviepos.callbacks.AccountCallback;
import com.example.oviepos.views.MainUIView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainUIView, AccountCallback {
    @BindView(R.id.splashLayout)
    ConstraintLayout splashLayout;
    @BindView(R.id.loginLayout)
    ConstraintLayout loginLayout;
    @BindView(R.id.registerLayout)
    ConstraintLayout registerLayout;
    @BindView(R.id.mainLayout)
    CoordinatorLayout mainLayout;

    //login & register
    @BindView(R.id.txtUsername)
    TextInputLayout txtUsername;
    @BindView(R.id.txtPassword)
    TextInputLayout txtPassword;
    @BindView(R.id.txtPasswordRepeat)
    TextInputLayout txtPasswordRepeat;
    @BindView(R.id.btnLogin)
    MaterialButton btnLogin;
    @BindView(R.id.btnRegister)
    MaterialButton btnRegister;

    @BindView(R.id.btnGroup)
    MaterialButtonToggleGroup btnGroup;

    //main
    @BindView(R.id.productCategory)
    MaterialButton btnCategory;
    @BindView(R.id.product)
    MaterialButton btnProduct;
    @BindView(R.id.report)
    MaterialButton btnReport;
    @BindView(R.id.account)
    MaterialButton btnAccount;
    @BindView(R.id.fabOrder)
    FloatingActionButton fabOrder;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.txtTitle)
    AppCompatTextView txtTitle;

    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(savedInstanceState);
    }

    void init(Bundle saveInstanceState) {
        ButterKnife.bind(this);
        mainPresenter = new MainPresenter(this, this);
        mainPresenter.attachView(this);

        mainPresenter.init();

        btnGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            Fragment fragment;
            if (group.getCheckedButtonId() != -1){
                switch (group.getCheckedButtonId()){
                    case R.id.productCategory:
                        txtTitle.setText("Kategori Produk");
                        fragment = CategoryFragment.getInstance();
                        fillFragment(fragment);
                        break;
                    case R.id.product:
                        txtTitle.setText("Produk");
                        fragment = ProductFragment.getInstance(false);
                        fillFragment(fragment);
                        break;
                    case R.id.report:
                        txtTitle.setText("Laporan");
                        break;
                    case R.id.account:
                        txtTitle.setText("Akun");
                        fragment = AccountFragment.getInstance();
                        fillFragment(fragment);
                        break;
                }
            }else{
                txtTitle.setText("Keranjang");
                fragment = OrderFragment.getInstance();
                fillFragment(fragment);
            }
        });
    }

    @OnClick({
            R.id.btnLogin,
            R.id.btnRegister,
            R.id.productCategory,
            R.id.product,
            R.id.report,
            R.id.account,
            R.id.fabOrder
    })
    public void onButtonClick(View view) {
        Fragment fragment;
        switch (view.getId()) {
            case R.id.btnLogin:
                mainPresenter.doLogin(txtUsername, txtPassword);
                break;
            case R.id.btnRegister:
//                mainPresenter.doLogin();
                break;
            case R.id.fabOrder:
                btnGroup.clearChecked();
//                txtTitle.setText("Keranjang");
//                fragment = OrderFragment.getInstance();
//                fillFragment(fragment);
                break;
        }
    }

    private void fillFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void splashScreen() {
        splashLayout.setVisibility(View.VISIBLE);
        loginLayout.setVisibility(View.GONE);
        registerLayout.setVisibility(View.GONE);
        mainLayout.setVisibility(View.GONE);
    }

    @Override
    public void loginScreen() {
        splashLayout.setVisibility(View.GONE);
        loginLayout.setVisibility(View.VISIBLE);
        registerLayout.setVisibility(View.GONE);
        mainLayout.setVisibility(View.GONE);
    }

    @Override
    public void registerScreen() {
        splashLayout.setVisibility(View.GONE);
        loginLayout.setVisibility(View.GONE);
        registerLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.GONE);
    }

    @Override
    public void mainScreen() {
        splashLayout.setVisibility(View.GONE);
        loginLayout.setVisibility(View.GONE);
        registerLayout.setVisibility(View.GONE);
        mainLayout.setVisibility(View.VISIBLE);

        fillFragment(OrderFragment.getInstance());
    }

    @Override
    public void fragmentCategory() {

    }

    @Override
    public void fragmentProduct() {

    }

    @Override
    public void fragmentReport() {

    }

    @Override
    public void fragmentAccount() {

    }

    @Override
    public void loginSuccess() {
        mainPresenter.decisionScreen();
    }

    @Override
    public void loginFailed() {

    }

    @Override
    public void registerSuccess() {

    }

    @Override
    public void registerFailed() {

    }

    @Override
    public void permissionGranted() {
        mainPresenter.countDownDecision();
    }

    @Override
    public void permissionDenied() {
        Toast.makeText(this, "some permission are denied, please accept in the future", Toast.LENGTH_SHORT).show();
        mainPresenter.decisionScreen();
    }

    @Override
    public void logoutSuccess() {
        mainPresenter.decisionScreen();
    }
}