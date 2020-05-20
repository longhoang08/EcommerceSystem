package com.example.mobile_ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
            TextView textViewStarProduct = view.findViewById(R.id.textViewStarProduct);
            // gan gia tri
            imageViewProduct.setImageResource(listProduct.get(position).getImageRepresent());
            textViewNameProduct.setText(listProduct.get(position).getName());
            textViewPriceProduct.setText(listProduct.get(position).getPrice()+" VND");
            textViewStarProduct.setText(listProduct.get(position).getStar()+"");
        } else {
            view = convertView;
        }
        return view;
    }
}
