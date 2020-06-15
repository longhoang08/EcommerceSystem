package com.example.mobile_ui;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomAdapter extends BaseAdapter {
    Context context;
    String countryList[];
    int flags[];
    String productType[];
    double[] productOfPrice;
    int[] quantityProduct;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, String[] countryList, int[] flags, String[] productType, double[] productOfPrice, int[] quantityProduct) {
        this.context = context;
        this.countryList = countryList;
        this.flags = flags;
        this.productType = productType;
        this.productOfPrice = productOfPrice;
        this.quantityProduct = quantityProduct;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return countryList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_list_view, null);
        TextView country = (TextView) view.findViewById(R.id.textView);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        TextView type = (TextView) view.findViewById(R.id.productType);
        TextView price = (TextView) view.findViewById(R.id.price);
        TextView quantity = (TextView) view.findViewById(R.id.amount);

        country.setText("Tên sản phẩm: " + countryList[i]);
        type.setText("Phân loại: " + productType[i]);
        price.setText("Giá: " + productOfPrice[i]);
        quantity.setText("Số lượng: x" + quantityProduct[i]);
        icon.setImageResource(flags[i]);
        return view;
    }

}