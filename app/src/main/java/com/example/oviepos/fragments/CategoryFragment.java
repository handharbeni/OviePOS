package com.example.oviepos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oviepos.R;
import com.example.oviepos.adapters.CategoryAdapter;
import com.example.oviepos.adapters.ProductsAdapter;
import com.example.oviepos.callbacks.CategoryBottomsheetCallback;
import com.example.oviepos.databases.models.responses.ProductsCategory;
import com.example.oviepos.fragments.bottomsheets.CategoryBottomsheet;
import com.example.oviepos.presenters.CategoryPresenter;
import com.example.oviepos.utils.BaseFragments;
import com.example.oviepos.views.CategoryUIView;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryFragment extends BaseFragments implements
        CategoryUIView,
        CategoryAdapter.InterfaceCategoryAdapter,
        CategoryBottomsheetCallback {
    private CategoryPresenter categoryPresenter;
    private View view;

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.txtSearch)
    SearchView txtSearch;
    @BindView(R.id.btnAdd)
    Button btnAdd;

    private CategoryAdapter categoryAdapter;
    private LinearLayoutManager linearLayoutManager;

    public static CategoryFragment getInstance() {
        return new CategoryFragment();
    }

    public CategoryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);
        init();
        return view;
    }

    void init() {
        ButterKnife.bind(this, view);
        categoryPresenter = new CategoryPresenter(getActivity(), this);
        categoryPresenter.attachView(this);
        categoryPresenter.init();
    }

    void showForm(ProductsCategory productsCategory) {
        CategoryBottomsheet categoryBottomsheet = CategoryBottomsheet.getInstance(
                Objects.requireNonNull(getActivity()).getApplicationContext(),
                productsCategory,
                this
        );
        categoryBottomsheet.showNow(getChildFragmentManager(), categoryBottomsheet.getTag());
    }

    @OnClick(R.id.btnAdd)
    public void createNew() {
        showForm(null);
    }

    @Override
    public void showAllData(List<ProductsCategory> listCategory) {
        categoryAdapter = new CategoryAdapter(
                Objects.requireNonNull(getActivity()).getApplicationContext(),
                listCategory,
                this
        );
        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        list.setLayoutManager(linearLayoutManager);
        list.setAdapter(categoryAdapter);
    }

    @Override
    public void updateData(List<ProductsCategory> listCategory) {
        if (categoryAdapter != null) {
            categoryAdapter.updateData(listCategory);
        }
    }

    @Override
    public void processSuccess() {
        categoryPresenter.updateData();
    }

    @Override
    public void processFailed() {
        categoryPresenter.updateData();
    }

    @Override
    public void onCategoryClick(ProductsCategory productsCategory) {
        showForm(productsCategory);
    }

    @Override
    public void insert(ProductsCategory productsCategory) {
        categoryPresenter.createCategory(productsCategory);
    }

    @Override
    public void update(ProductsCategory productsCategory) {
        categoryPresenter.updateCategory(productsCategory);
    }

    @Override
    public void delete(ProductsCategory productsCategory) {
        categoryPresenter.deleteCategory(productsCategory);
    }
}
