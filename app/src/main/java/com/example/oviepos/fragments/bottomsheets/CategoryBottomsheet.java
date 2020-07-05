package com.example.oviepos.fragments.bottomsheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.oviepos.R;
import com.example.oviepos.callbacks.CategoryBottomsheetCallback;
import com.example.oviepos.databases.models.responses.ProductsCategory;
import com.example.oviepos.utils.BaseBottomFragments;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryBottomsheet extends BaseBottomFragments {
    private Context context;
    private ProductsCategory productsCategory;
    private CategoryBottomsheetCallback categoryBottomsheetCallback;

    @BindView(R.id.txtCategoryName)
    EditText txtCategoryName;
    @BindView(R.id.btnCreate)
    Button btnCreate;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    @BindView(R.id.btnDelete)
    Button btnDelete;

    @BindView(R.id.llBtnAction)
    LinearLayout llBtnAction;

    private View view;

    public static CategoryBottomsheet getInstance(Context context,
                                                  ProductsCategory productsCategory,
                                                  CategoryBottomsheetCallback categoryBottomsheetCallback) {
        return new CategoryBottomsheet(context, productsCategory, categoryBottomsheetCallback);
    }

    public CategoryBottomsheet(Context context,
                               ProductsCategory productsCategory,
                               CategoryBottomsheetCallback categoryBottomsheetCallback) {
        this.context = context;
        this.productsCategory = productsCategory;
        this.categoryBottomsheetCallback = categoryBottomsheetCallback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bottomsheet_category, container, false);
        init();
        return view;
    }

    private void init() {
        ButterKnife.bind(this, view);
        if (productsCategory == null) {
            llBtnAction.setVisibility(View.GONE);
            btnCreate.setVisibility(View.VISIBLE);
        } else {
            txtCategoryName.setText(productsCategory.getCategoryName());
            llBtnAction.setVisibility(View.VISIBLE);
            btnCreate.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.btnCreate, R.id.btnUpdate, R.id.btnDelete})
    public void btnActionClick(View view) {
        switch (view.getId()) {
            case R.id.btnCreate:
                productsCategory = new ProductsCategory();
                productsCategory.setCategoryName(txtCategoryName.getText().toString());
                categoryBottomsheetCallback.insert(productsCategory);
                break;
            case R.id.btnUpdate:
                productsCategory.setCategoryName(txtCategoryName.getText().toString());
                categoryBottomsheetCallback.update(productsCategory);
                break;
            case R.id.btnDelete:
                categoryBottomsheetCallback.delete(productsCategory);
                break;
        }
        dismiss();
    }
}
