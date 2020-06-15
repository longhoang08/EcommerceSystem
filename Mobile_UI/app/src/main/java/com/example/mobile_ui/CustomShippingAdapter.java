package com.example.mobile_ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class CustomShippingAdapter extends BaseAdapter {
    Context context;
    double totalPrice;
    LayoutInflater inflter;

    public CustomShippingAdapter(Context applicationContext, double totalPrice){
        this.context = context;
        this.totalPrice = totalPrice;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return 0;
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
        view = inflter.inflate(R.layout.shipping, null);
        TextView price = (TextView) view.findViewById(R.id.shipMoney);
        return view;
    }
}
