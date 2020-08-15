package com.example.oviepos.fragments.bottomsheets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;

import com.bumptech.glide.Glide;
import com.example.oviepos.R;
import com.example.oviepos.callbacks.ProductBottomsheetCallback;
import com.example.oviepos.databases.AppDB;
import com.example.oviepos.databases.models.responses.Products;
import com.example.oviepos.databases.models.responses.ProductsCategory;
import com.example.oviepos.utils.BaseBottomFragments;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.ChooserType;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

public class ProductBottomsheet extends BaseBottomFragments {
    private Context context;
    private Products products;
    private ProductBottomsheetCallback productBottomsheetCallback;
    private List<ProductsCategory> listProductCategory;

    @BindView(R.id.txtProductImage)
    AppCompatImageView txtProductImage;
    @BindView(R.id.txtProductCategory)
    AppCompatSpinner txtProductCategory;
    @BindView(R.id.txtProductName)
    AppCompatEditText txtProductName;
    @BindView(R.id.txtProductPrice)
    AppCompatEditText txtProductPrice;
    @BindView(R.id.txtProductSKU)
    AppCompatEditText txtProductSKU;
    @BindView(R.id.btnCreate)
    Button btnCreate;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    @BindView(R.id.btnDelete)
    Button btnDelete;

    @BindView(R.id.llBtnAction)
    LinearLayout llBtnAction;

    private String currentImage;

    private View view;
    EasyImage easyImage;

    public static ProductBottomsheet getInstance(
            Context context,
            Products products,
            List<ProductsCategory> listProductCategory,
            ProductBottomsheetCallback productBottomsheetCallback) {
        return new ProductBottomsheet(
                context,
                products,
                listProductCategory,
                productBottomsheetCallback);
    }

    public ProductBottomsheet(
            Context context,
            Products products,
            List<ProductsCategory> listProductCategory,
            ProductBottomsheetCallback productBottomsheetCallback) {
        this.context = context;
        this.products = products;
        this.listProductCategory = listProductCategory;
        this.productBottomsheetCallback = productBottomsheetCallback;
        easyImage = new EasyImage.Builder(this.context)
                .setCopyImagesToPublicGalleryFolder(false)
                .setChooserTitle(context.getResources().getString(R.string.app_name))
                .setFolderName(context.getResources().getString(R.string.app_name))
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .allowMultiple(false)
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bottomsheet_product, container, false);
        init();
        return view;
    }

    void init() {
        ButterKnife.bind(this, view);
        initSpinner();
        if (products == null) {
            llBtnAction.setVisibility(View.GONE);
            btnCreate.setVisibility(View.VISIBLE);
        } else {
            try {
                Glide.with(context).load(products.getProductImage()).into(txtProductImage);
            } catch (Exception e){
                Glide.with(context).load(R.drawable.aster).into(txtProductImage);
            }
            currentImage = products.getProductImage();
            txtProductName.setText(products.getProductName());
            txtProductPrice.setText(products.getProductPrice());
            txtProductSKU.setText(products.getProductSKU());

            llBtnAction.setVisibility(View.VISIBLE);
            btnCreate.setVisibility(View.GONE);
        }
    }

    void initSpinner() {
        String[] aCategory = new String[listProductCategory.size()];
        int i = 0;
        int selected = 0;
        for (ProductsCategory productsCategory : listProductCategory) {
            aCategory[i] = productsCategory.getCategoryName();
            if (products != null) {
                if (productsCategory.getId().equals(products.getProductCategory())) {
                    selected = i;
                }
            }
            i++;
        }
        ArrayAdapter adapter = new ArrayAdapter<>(
                Objects.requireNonNull(getActivity()).getApplicationContext(),
                android.R.layout.simple_spinner_item,
                aCategory
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtProductCategory.setAdapter(adapter);
        txtProductCategory.setSelection(selected, true);
    }

    @OnClick({R.id.btnCreate, R.id.btnUpdate, R.id.btnDelete})
    public void btnActionClick(View view) {
        switch (view.getId()) {
            case R.id.btnCreate:
                int id = AppDB.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext())
                        .productCategory().getId(txtProductCategory.getSelectedItem().toString())
                        .getId();
                products = new Products();
                products.setProductName(Objects.requireNonNull(txtProductName.getText()).toString());
                products.setProductImage(currentImage);
                products.setProductPrice(Objects.requireNonNull(txtProductPrice.getText()).toString());
                products.setProductSKU(Objects.requireNonNull(txtProductSKU.getText()).toString());
                products.setProductCategory(id);
                productBottomsheetCallback.insert(products);
                break;
            case R.id.btnUpdate:
                int idUpdate = AppDB.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext())
                        .productCategory().getId(txtProductCategory.getSelectedItem().toString())
                        .getId();
                products.setProductImage(currentImage);
                products.setProductName(Objects.requireNonNull(txtProductName.getText()).toString());
                products.setProductPrice(Objects.requireNonNull(txtProductPrice.getText()).toString());
                products.setProductSKU(Objects.requireNonNull(txtProductSKU.getText()).toString());
                products.setProductCategory(idUpdate);
                productBottomsheetCallback.update(products);
                break;
            case R.id.btnDelete:
                productBottomsheetCallback.delete(products);
                break;
        }
        dismiss();
    }

    @OnClick(R.id.txtProductImage)
    public void takeImage(){
        easyImage.openChooser(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        easyImage.handleActivityResult(requestCode, resultCode, data, Objects.requireNonNull(getActivity()), new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(@NotNull MediaFile[] imageFiles, @NotNull MediaSource source) {
//                onPhotosReturned(imageFiles);
                if (imageFiles.length > 0){
                    currentImage = imageFiles[0].getFile().getPath();
                    Glide.with(context).load(imageFiles[0].getFile().getPath()).into(txtProductImage);
                }
            }

            @Override
            public void onImagePickerError(@NonNull Throwable error, @NonNull MediaSource source) {
                //Some error handling
                error.printStackTrace();
            }

            @Override
            public void onCanceled(@NonNull MediaSource source) {
                //Not necessary to remove any files manually anymore
            }
        });
    }
}
