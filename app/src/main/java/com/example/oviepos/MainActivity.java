package com.example.oviepos;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.oviepos.callbacks.AccountCallback;
import com.example.oviepos.fragments.AccountFragment;
import com.example.oviepos.fragments.CartFragment;
import com.example.oviepos.fragments.CategoryFragment;
import com.example.oviepos.fragments.OrderFragment;
import com.example.oviepos.fragments.ProductFragment;
import com.example.oviepos.fragments.ReportFragment;
import com.example.oviepos.presenters.MainPresenter;
import com.example.oviepos.print_helper.BluetoothHandler;
import com.example.oviepos.print_helper.DeviceActivity;
import com.example.oviepos.utils.BaseActivity;
import com.example.oviepos.views.MainUIView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.zj.btsdk.BluetoothService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainUIView, AccountCallback, BluetoothHandler.HandlerInterface {
    private static String TAG = MainActivity.class.getSimpleName();

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
    @BindView(R.id.iconThermalPrinter)
    ImageView iconThermalPrinter;

    MainPresenter mainPresenter;


    public static final int RC_BLUETOOTH = 0;
    public static final int RC_CONNECT_DEVICE = 1;
    public static final int RC_ENABLE_BLUETOOTH = 2;

    public static BluetoothService mService = null;
    public static boolean isPrinterReady = false;


    private AccountFragment accountFragment;
    private CartFragment cartFragment;
    private CategoryFragment categoryFragment;
    private OrderFragment orderFragment;
    private ProductFragment productFragment;
    private ReportFragment reportFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    void init() {
        ButterKnife.bind(this);
        mainPresenter = new MainPresenter(this, this, btnGroup);
        mainPresenter.attachView(this);

        mainPresenter.init();
        setupBluetooth();
    }

    private void setupBluetooth() {
        mService = new BluetoothService(this, new BluetoothHandler(this));
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
        switch (view.getId()) {
            case R.id.btnLogin:
                mainPresenter.doLogin(txtUsername, txtPassword);
                break;
            case R.id.btnRegister:
//                mainPresenter.doLogin();
                break;
            case R.id.fabOrder:
                btnGroup.clearChecked();
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

        txtTitle.setText(R.string.label_cart);
        fillFragment(OrderFragment.getInstance());
    }

    @Override
    public void fragmentCategory() {
        txtTitle.setText(R.string.label_category);
        categoryFragment = CategoryFragment.getInstance();
        fillFragment(categoryFragment);
    }

    @Override
    public void fragmentProduct() {
        txtTitle.setText(R.string.label_product);
        productFragment = ProductFragment.getInstance(false);
        fillFragment(productFragment);
    }

    @Override
    public void fragmentReport() {
        txtTitle.setText(R.string.label_report);
        reportFragment = ReportFragment.getInstance();
        fillFragment(reportFragment);
    }

    @Override
    public void fragmentAccount() {
        txtTitle.setText(R.string.label_account);
        accountFragment = AccountFragment.getInstance();
        fillFragment(accountFragment);
    }

    @Override
    public void fragmentCart() {
        txtTitle.setText(R.string.label_cart);
        orderFragment = OrderFragment.getInstance();
        fillFragment(orderFragment);
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

    @OnClick(R.id.iconThermalPrinter)
    public void searchPrinter(){
        if (!isPrinterReady){
            startActivityForResult(new Intent(this, DeviceActivity.class), RC_CONNECT_DEVICE);
        }
    }

    @Override
    public void onDeviceConnected() {
        isPrinterReady = true;
        iconThermalPrinter.setImageResource(R.drawable.ic_print_connected);
        if (accountFragment != null){
            accountFragment.updateButton(AccountFragment.PAIRED_DEVICE);
        }
    }

    @Override
    public void onDeviceConnecting() {
        isPrinterReady = false;
        iconThermalPrinter.setImageResource(R.drawable.ic_print_disconnected);
        if (accountFragment != null){
            accountFragment.updateButton(AccountFragment.UNPAIRED_DEVICE);
        }
    }

    @Override
    public void onDeviceConnectionLost() {
        isPrinterReady = false;
        iconThermalPrinter.setImageResource(R.drawable.ic_print_disconnected);
        if (accountFragment != null){
            accountFragment.updateButton(AccountFragment.UNPAIRED_DEVICE);
        }
    }

    @Override
    public void onDeviceUnableToConnect() {
        isPrinterReady = false;
        iconThermalPrinter.setImageResource(R.drawable.ic_print_disconnected);
        if (accountFragment != null){
            accountFragment.updateButton(AccountFragment.UNPAIRED_DEVICE);
        }
    }

    void requestBluetooth() {
        if (mService != null) {
            if (!mService.isBTopen()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, RC_ENABLE_BLUETOOTH);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_ENABLE_BLUETOOTH:
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "onActivityResult: bluetooth aktif");
                } else
                    Log.i(TAG, "onActivityResult: bluetooth harus aktif untuk menggunakan fitur ini");
                requestBluetooth();
                break;
            case RC_CONNECT_DEVICE:
                if (resultCode == RESULT_OK) {
                    String address = data.getExtras().getString(DeviceActivity.EXTRA_DEVICE_ADDRESS);
                    BluetoothDevice mDevice = mService.getDevByMac(address);
                    mService.connect(mDevice);
                }
                break;
        }
    }
}