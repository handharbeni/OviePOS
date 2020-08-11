package com.example.oviepos.presenters;

import android.app.Activity;

import androidx.lifecycle.LifecycleOwner;

import com.example.oviepos.MainActivity;
import com.example.oviepos.R;
import com.example.oviepos.databases.AppDB;
import com.example.oviepos.databases.models.responses.Cart;
import com.example.oviepos.databases.models.responses.TransactionItems;
import com.example.oviepos.databases.models.responses.Transactions;
import com.example.oviepos.print_helper.PrinterCommands;
import com.example.oviepos.utils.Constants;
import com.example.oviepos.utils.Utils;
import com.example.oviepos.views.PaymentUIView;
import com.manishkprboilerplate.base.BasePresenter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import okhttp3.internal.Util;
import rx.Subscription;

import static com.example.oviepos.MainActivity.isPrinterReady;
import static com.example.oviepos.MainActivity.mService;

public class PaymentPresenter extends BasePresenter<PaymentUIView.paymentUIView>
        implements PaymentUIView.paymentPresenter {

    Activity activity;
    private Subscription subscription;
    private LifecycleOwner lifecycleOwner;
    final String TAG = MainPresenter.class.getSimpleName();
    private AppDB appDB;

    public PaymentPresenter(Activity activity, LifecycleOwner lifecycleOwner) {
        this.activity = activity;
        this.lifecycleOwner = lifecycleOwner;
        appDB = AppDB.getInstance(activity.getApplicationContext());
    }

    @Override
    public void attachView(PaymentUIView.paymentUIView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void init() {
        List<Constants.TRANSACTION_TYPE> orderType = Arrays.asList(Constants.TRANSACTION_TYPE.values());
        getMvpView().initOrderType(orderType);

        List<Constants.PAYMENT_TYPE> paymentType = Arrays.asList(Constants.PAYMENT_TYPE.values());
        getMvpView().initPaymentType(paymentType);

        List<Constants.PAYMENT_CASHLESS> paymentCashless = Arrays.asList(Constants.PAYMENT_CASHLESS.values());
        getMvpView().initPaymentCashless(paymentCashless);

        List<Cart> listCart = appDB.cart().getAll();
        getMvpView().initListCart(listCart);
    }

    @Override
    public void doPayment(Transactions transactions, List<TransactionItems> transactionItems, String discount, String ppn) {
        try {
            appDB.transactions().insertTransaction(transactions, transactionItems);
            if (isPrinterReady) {
                mService.write(PrinterCommands.ESC_ALIGN_CENTER);
                mService.sendMessage(activity.getApplicationContext().getString(R.string.app_name), "");
                mService.write(PrinterCommands.ESC_ALIGN_LEFT);
                mService.sendMessage("Customer : " + transactions.getCustomerName(), "");
                mService.sendMessage("Date : " + Utils.getDateFromMillis(transactions.getDateNow()), "");
                mService.sendMessage(PrinterCommands.dashLine, "");
                int total = 0;
                for (TransactionItems items : transactionItems) {
                    mService.sendMessage(items.getProductName() + " (" + Utils.formatRupiah(Integer.parseInt(items.getProductPrice())) + ")", "");
                    int subTotal = Integer.parseInt(items.getProductPrice()) * items.getQty();
                    total += subTotal;
                    mService.sendMessage(Utils.justifyPrintLine("x " + items.getQty(), Utils.formatRupiah(subTotal)), "");
                }

                total += Integer.parseInt(ppn);
                total -= Integer.parseInt(discount);

                mService.sendMessage(PrinterCommands.dashLine, "");
                mService.sendMessage(Utils.justifyPrintLine("PPN (10%)", Utils.formatRupiah(Long.parseLong(ppn))), "");
                mService.sendMessage(Utils.justifyPrintLine("DISCOUNT", Utils.formatRupiah(Long.parseLong(discount))), "");
                mService.sendMessage(Utils.justifyPrintLine("TOTAL", Utils.formatRupiah(total)), "");
                mService.write(PrinterCommands.ESC_ALIGN_CENTER);
                mService.sendMessage("TERIMA KASIH", "");
                mService.write(PrinterCommands.ESC_ENTER);
                mService.write(PrinterCommands.FEED_PAPER_AND_CUT);
            }
            appDB.cart().delete(appDB.cart().getAll());
            getMvpView().onPaymentSuccess();
        } catch (Exception e) {
            getMvpView().onPaymentFailed();
        }
    }
}
