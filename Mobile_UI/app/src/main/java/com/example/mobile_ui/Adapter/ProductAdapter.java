package com.example.mobile_ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mobile_ui.Model.Product;
import com.example.mobile_ui.R;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private List<Product> listProduct;

    public ProductAdapter(List<Product> listProduct) {
        this.listProduct = listProduct;
    }

    @Override
    public int getCount() {
        return listProduct.size();
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, null);
            // anh xa
            ImageView imageViewProduct = view.findViewById(R.id.imageViewProduct);
            TextView textViewNameProduct = view.findViewById(R.id.textViewNameProduct);
            TextView textViewPriceProduct = view.findViewById(R.id.textViewPriceProduct);
            TextView textViewStockProduct = view.findViewById(R.id.textViewStockProduct);
            // gan gia tri
            if (listProduct.get(position).getImageRepresent() != null) {
                Glide.with(parent.getContext())
                        .load(listProduct.get(position).getImageRepresent()).override(178, 178).centerCrop()
                        .into(imageViewProduct);
            }
            String namePro = listProduct.get(position).getName();
            if (namePro.length() > 50) {
                namePro = namePro.substring(0, 50)+"...";
            }
            textViewNameProduct.setText(namePro);
            textViewPriceProduct.setText(listProduct.get(position).getPrice()+" đ");
            textViewStockProduct.setText("Kho "+listProduct.get(position).getStock());
        } else {
            view = convertView;
        }
        return view;
    }
}
