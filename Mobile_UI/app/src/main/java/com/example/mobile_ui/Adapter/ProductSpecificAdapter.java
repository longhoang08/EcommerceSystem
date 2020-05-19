package com.example.mobile_ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mobile_ui.Model.ProductSpecific;
import com.example.mobile_ui.R;

import java.util.List;

public class ProductSpecificAdapter extends BaseAdapter {
    private List<ProductSpecific> listProductSpecific;

    public ProductSpecificAdapter(List<ProductSpecific> listProductSpecific) {
        this.listProductSpecific = listProductSpecific;
    }

    @Override
    public int getCount() {
        return listProductSpecific.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_specific_item, null);
            // anh xa
            TextView textViewFeatureName = view.findViewById(R.id.textViewFeatureName);
            TextView textViewFeautureValue = view.findViewById(R.id.textViewFeautureValue);
            // gan gia tri
            textViewFeatureName.setText(listProductSpecific.get(position).getFeatureName());
            textViewFeautureValue.setText(listProductSpecific.get(position).getFeatureValue());
        } else {
            view = convertView;
        }
        return view;
    }
}
