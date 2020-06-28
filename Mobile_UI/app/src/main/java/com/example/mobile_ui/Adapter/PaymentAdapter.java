package com.example.mobile_ui.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mobile_ui.Model.OrderProduct;
import com.example.mobile_ui.Model.Payment;
import com.example.mobile_ui.R;
import com.example.mobile_ui.View.ExpandHeightGridView;

import java.util.List;

public class PaymentAdapter extends BaseAdapter {
    private List<Payment> listPayment;

    public PaymentAdapter(List<Payment> listPayment) {
        this.listPayment = listPayment;
    }

    @Override
    public int getCount() {
        return listPayment.size();
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_item, null);
            // anh xa
            TextView nameOfShop = view.findViewById(R.id.nameOfShop);
            ImageView proImg = view.findViewById(R.id.proImg);
            TextView proName = view.findViewById(R.id.proName);
            TextView price = view.findViewById(R.id.price);
            TextView buyNum = view.findViewById(R.id.buyNum);
            TextView feeTransport = view.findViewById(R.id.feeTransport);
            TextView feeSale = view.findViewById(R.id.feeSale);
            // gan gia tri
            nameOfShop.setText(listPayment.get(position).getNameShop());
            proName.setText(listPayment.get(position).getNameProduct());
            price.setText(listPayment.get(position).getPricePro()+"");
            buyNum.setText(listPayment.get(position).getBuyNumPro()+"");
            feeTransport.setText(listPayment.get(position).getFeeTransport()+"");
            feeSale.setText(listPayment.get(position).getFeeSale()+"");
            Glide.with(parent.getContext()).load(listPayment.get(position).getImageProduct()).override(80, 80).centerCrop()
                    .into(proImg);
            // set sự kiện
        } else {
            view = convertView;
        }
        return view;
    }
}
