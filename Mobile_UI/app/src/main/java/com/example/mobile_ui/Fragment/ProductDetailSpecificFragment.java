package com.example.mobile_ui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile_ui.Adapter.ProductSpecificAdapter;
import com.example.mobile_ui.Model.ProductSpecific;
import com.example.mobile_ui.R;
import com.example.mobile_ui.View.ExpandHeightGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailSpecificFragment extends Fragment {

    public ProductDetailSpecificFragment() {
        // Required empty public constructor
    }


    private ExpandHeightGridView expandHeightGridViewProduct;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_product_detail_specific, container, false);
        // load cac dac diem san pham
        expandHeightGridViewProduct = root.findViewById(R.id.expandHeightGridViewProduct);
        List<ProductSpecific> listProductSpecific = new ArrayList<>();
        loadProductSpecific(listProductSpecific);
        ProductSpecificAdapter productSpecificAdapter = new ProductSpecificAdapter(listProductSpecific);
        expandHeightGridViewProduct.setAdapter(productSpecificAdapter);
        return root;
    }

    private void loadProductSpecific(List<ProductSpecific> listProductSpecific) {
        listProductSpecific.add(new ProductSpecific("RAM", "16G"));
        listProductSpecific.add(new ProductSpecific("RAM", "16G"));
        listProductSpecific.add(new ProductSpecific("RAM", "16G"));
        listProductSpecific.add(new ProductSpecific("RAM", "16G"));
        listProductSpecific.add(new ProductSpecific("RAM", "16G"));
        listProductSpecific.add(new ProductSpecific("RAM", "16G"));
        listProductSpecific.add(new ProductSpecific("RAM", "16G"));
        listProductSpecific.add(new ProductSpecific("RAM", "16G"));
    }
}
