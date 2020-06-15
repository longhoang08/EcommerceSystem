package com.example.mobile_ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_ui.DetailProductActivity;
import com.example.mobile_ui.Model.BuyRecord;
import com.example.mobile_ui.Model.MyBuyRecord;
import com.example.mobile_ui.R;

import java.util.ArrayList;

public class ListOfMyBuyrecordAdapter extends BaseAdapter {

    ArrayList<MyBuyRecord> data;
    public ListOfMyBuyrecordAdapter(ArrayList<MyBuyRecord> dataList){
        data = dataList;
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mybuyrecord_item, null);

            //anh xa
            TextView nameOfShop = view.findViewById(R.id.nameOfShop);
            TextView feedback = view.findViewById(R.id.feedback);
            ImageView proImg = view.findViewById(R.id.proImg);
            TextView proName = view.findViewById(R.id.proName);
            TextView buyNum = view.findViewById(R.id.buyNum);
            TextView buyNum1 = view.findViewById(R.id.buyNum1);
            TextView price = view.findViewById(R.id.price);
            TextView money = view.findViewById(R.id.money);
            Button buyAgain = view.findViewById(R.id.buyAgain);

            nameOfShop.setText(data.get(position).getNameOfShop());
            proImg.setImageResource(data.get(position).getBuyProduct().get(0).getImageRepresent());
            proName.setText(data.get(position).getBuyProduct().get(0).getName());
            buyNum.setText("x" + data.get(position).getBuyNum().get(0));
            buyNum1.setText(data.get(position).getBuyNum().get(0) + " sản phẩm");
            price.setText("đ"+data.get(position).getBuyProduct().get(0).getPrice());
            money.setText("Thành tiền : đ"+data.get(position).getMoney());
        }else{
            view=convertView;
        }

        return view;
    }


}
