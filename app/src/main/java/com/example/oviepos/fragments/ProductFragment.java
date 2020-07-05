package com.example.oviepos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oviepos.R;
import com.example.oviepos.adapters.ProductsAdapter;
import com.example.oviepos.callbacks.ProductBottomsheetCallback;
import com.example.oviepos.databases.AppDB;
import com.example.oviepos.databases.models.responses.Cart;
import com.example.oviepos.databases.models.responses.Products;
import com.example.oviepos.databases.models.responses.ProductsCategory;
import com.example.oviepos.fragments.bottomsheets.CategoryBottomsheet;
import com.example.oviepos.fragments.bottomsheets.ProductBottomsheet;
import com.example.oviepos.presenters.ProductsPresenter;
import com.example.oviepos.utils.BaseFragments;
import com.example.oviepos.views.ProductUIView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class ProductFragment extends BaseFragments implements
        ProductUIView,
        ProductsAdapter.ProductsAdapterCallback, ProductBottomsheetCallback {

    private ProductsPresenter productsPresenter;
    private View view;

    private ProductsAdapter productsAdapter;
    private GridLayoutManager gridLayoutManager;

    private List<String> existCategory = new ArrayList<>();
    private List<ProductsCategory> listProductCategory = new ArrayList<>();

    @BindView(R.id.txtSearch)
    SearchView txtSearch;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.chipCategory)
    ChipGroup chipCategory;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.llCari)
    LinearLayout llCari;

    private boolean isToCart = false;

    public static ProductFragment getInstance(boolean isToCart) {
        return new ProductFragment(isToCart);
    }

    public ProductFragment(boolean isToCart) {
        this.isToCart = isToCart;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product, container, false);
        init();
        return view;
    }

    void init() {
        ButterKnife.bind(this, view);
        productsPresenter = new ProductsPresenter(getActivity(), this);
        productsPresenter.attachView(this);
        productsPresenter.init();

        if (isToCart) {
            llCari.setVisibility(View.GONE);
        }

        chipCategory.setOnCheckedChangeListener((group, checkedId) -> {
            if (group.getCheckedChipId() != -1) {
                productsPresenter.listByCategory(group.getCheckedChipId());
            } else {
                productsPresenter.listAll();
            }
        });
    }

    private void addChipView(int id, String chipText) {
        if (!existCategory.contains(chipText)) {
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.item_category_chip, chipCategory, false);
            chip.setId(id);
            chip.setText(chipText);
            chipCategory.addView(chip);
            existCategory.add(chipText);
        }
    }

    void showForm(Products products) {
        if (isToCart) {
            Cart cart = AppDB.getInstance(getActivity().getApplicationContext())
                    .cart().getCartByProductId(products.getId());
            if (cart == null) {
                cart = new Cart();
                cart.setProductId(products.getId());
                cart.setProductName(products.getProductName());
                cart.setProductPrice(products.getProductPrice());
                cart.setQty(1);
                AppDB.getInstance(getActivity().getApplicationContext()).cart().insert(cart);
            } else {
                cart.setQty(cart.getQty() + 1);
                AppDB.getInstance(getActivity().getApplicationContext()).cart().update(cart);
            }
        } else {
            ProductBottomsheet productBottomsheet = ProductBottomsheet.getInstance(
                    Objects.requireNonNull(getActivity()).getApplicationContext(),
                    products,
                    listProductCategory,
                    this
            );
            productBottomsheet.showNow(getChildFragmentManager(), productBottomsheet.getTag());
        }
    }

    @OnClick(R.id.btnAdd)
    public void createProduct() {
        showForm(null);
    }

    @Override
    public void onProductsClick(Products products) {
        showForm(products);
    }

    @Override
    public void listAllCategory(List<ProductsCategory> listCategory) {
        this.listProductCategory = listCategory;
        for (ProductsCategory productsCategory : listCategory) {
            addChipView(productsCategory.getId(), productsCategory.getCategoryName());
        }
    }

    @Override
    public void listAllProduct(List<Products> listProduct) {
        productsAdapter = new ProductsAdapter(getActivity().getApplicationContext(), listProduct, this);
        gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), isToCart ? 2 : 4);
        list.setLayoutManager(gridLayoutManager);
        list.setAdapter(productsAdapter);
    }

    @Override
    public void updateProduct(List<Products> listProducts) {
        if (productsAdapter != null) {
            productsAdapter.update(listProducts);
        }
    }

    @Override
    public void processSuccess() {
        productsPresenter.updateData();
    }

    @Override
    public void processFailed() {
        productsPresenter.updateData();
    }

    @Override
    public void insert(Products products) {
        productsPresenter.createProduct(products);
    }

    @Override
    public void update(Products products) {
        productsPresenter.updateProduct(products);
    }

    @Override
    public void delete(Products products) {
        productsPresenter.deleteProduct(products);
    }
}
