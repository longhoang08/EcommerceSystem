package com.example.mobile_ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_ui.DetailBuyrecordActivity;
import com.example.mobile_ui.Model.BuyRecord;
import com.example.mobile_ui.Model.Product;
import com.example.mobile_ui.R;

import java.util.ArrayList;

public class ListOfBuyrecordAdapter extends BaseAdapter {

    ArrayList<BuyRecord> data;
    public ListOfBuyrecordAdapter(ArrayList<BuyRecord> dataList){
        data = dataList;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // moi 1 item se hien theo setting o day
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;
        if(convertView==null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyrecord_item, null);

            //anh xa
            TextView nameOfCustom = view.findViewById(R.id.nameOfCustom);
            ImageView proImg = view.findViewById(R.id.proImg);
            TextView proName = view.findViewById(R.id.proName);
            TextView buyNum = view.findViewById(R.id.buyNum);
            TextView price = view.findViewById(R.id.price);
            TextView money = view.findViewById(R.id.money);
            Button buttonPro = view.findViewById(R.id.buttonPro);
            Button buttonViewDetail = view.findViewById(R.id.buttonViewDetail);

            nameOfCustom.setText(data.get(position).getCustomer().getUsername());
            proImg.setImageResource(R.drawable.icon_kiwi_fruit);
            proName.setText(data.get(position).getBuyProduct().getName());
            buyNum.setText("x" + data.get(position).getBuyNum());
            price.setText("đ"+data.get(position).getBuyProduct().getPrice());
            money.setText("Thành tiền : đ"+data.get(position).getMoney());
            if (data.get(position).getState() == "danggiao") {
                buttonPro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            } else {
                buttonPro.setVisibility(View.INVISIBLE);
            }
            buttonViewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(parent.getContext(), DetailBuyrecordActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("idOfMybyrecord",data.get(position).getId());
                    intent.putExtras(bundle);
                    parent.getContext().startActivity(intent);
                }
            });
        }else{
            view=convertView;
        }

        return view;
    }
}
