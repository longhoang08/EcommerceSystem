package com.example.mobile_ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mobile_ui.Model.CartShop;
import com.example.mobile_ui.Model.OrderProduct;
import com.example.mobile_ui.R;
import com.example.mobile_ui.View.ExpandHeightGridView;

import java.util.List;

public class CartProductShopAdapter extends BaseAdapter {
    private List<CartShop> listCartShop;

    public CartProductShopAdapter(List<CartShop> listCartShop) {
        this.listCartShop = listCartShop;
    }

    @Override
    public int getCount() {
        return listCartShop.size();
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product_shop_item, null);
            // anh xa
            final CheckBox checkBoxShopProduct = view.findViewById(R.id.checkBoxShopProduct);
            TextView textViewNameShop = view.findViewById(R.id.textViewNameShop);
            final ExpandHeightGridView expandHeightGridViewProductOfShop = view.findViewById(R.id.expandHeightGridViewProductOfShop);
            // gan gia tri
            textViewNameShop.setText(listCartShop.get(position).getName());
            final List<OrderProduct> listOrderProduct = listCartShop.get(position).getListOrderProduct();
            CartProductAdapter cartProductAdapter = new CartProductAdapter(listOrderProduct);
            expandHeightGridViewProductOfShop.setAdapter(cartProductAdapter);
            // set sự kiện chọn vào tên shop
            checkBoxShopProduct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    for (int i = 0; i < listOrderProduct.size(); i++) {
                        CheckBox checkBoxProduct = expandHeightGridViewProductOfShop.getViewByPosition(i).findViewById(R.id.checkBoxProduct);
                        if (checkBoxProduct.isChecked() != checkBoxShopProduct.isChecked()) {
                            checkBoxProduct.performClick();
                        }
                    }
                }
            });
        } else {
            view = convertView;
        }
        return view;
    }
}
