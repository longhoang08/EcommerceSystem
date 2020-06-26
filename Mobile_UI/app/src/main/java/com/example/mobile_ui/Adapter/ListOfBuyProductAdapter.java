package com.example.mobile_ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_ui.Model.MyBuyRecord;
import com.example.mobile_ui.Model.Product;
import com.example.mobile_ui.R;

import java.util.ArrayList;

public class ListOfBuyProductAdapter extends BaseAdapter {
    ArrayList<Product> data;
    ArrayList<Integer> dataBuyNum;
    public ListOfBuyProductAdapter(ArrayList<Product> listPro,ArrayList<Integer> buyNum){
        data = listPro;
        dataBuyNum = buyNum;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // moi 1 item se hien theo setting o day
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView==null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyproduct_item, null);

            //anh xa
            ImageView proImg = view.findViewById(R.id.proImg);
            TextView proName = view.findViewById(R.id.proName);
            TextView buyNum = view.findViewById(R.id.buyNum);
            TextView price = view.findViewById(R.id.price);

            proImg.setImageResource(Integer.parseInt(data.get(position).getImageRepresent()));
            proName.setText(data.get(position).getName());
            buyNum.setText("x" + dataBuyNum.get(position));
            price.setText("đ"+data.get(position).getPrice());
        }else{
            view=convertView;
        }
        return view;
    }


}
