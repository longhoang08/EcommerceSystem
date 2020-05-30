package com.example.mobile_ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_ui.Model.BuyRecord;
import com.example.mobile_ui.Model.Product;
import com.example.mobile_ui.R;

import java.util.ArrayList;

public class ListOfBuyrecordAdapter extends BaseAdapter {

    ArrayList<BuyRecord> data;
    Context myContext;
    int myLayout;
    public ListOfBuyrecordAdapter(Context context, int layout, ArrayList<BuyRecord> dataList){
        myContext = context;
        myLayout = layout;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout,null);

        //anh xa
        TextView state = (TextView) convertView.findViewById(R.id.state);
        ImageView proImg = convertView.findViewById(R.id.proImg);
        TextView proName = convertView.findViewById(R.id.proName);
        TextView buyNum = convertView.findViewById(R.id.buyNum);
        TextView price = convertView.findViewById(R.id.price);
        TextView money = convertView.findViewById(R.id.money);

        state.setText(convertState(data.get(position).getState()));
        proImg.setImageResource(data.get(position).getBuyProduct().getImageRepresent());
        proName.setText(data.get(position).getBuyProduct().getName());
        buyNum.setText("x" + data.get(position).getBuyNum());
        price.setText("đ"+data.get(position).getBuyProduct().getPrice());
        money.setText("Thành tiền : đ"+data.get(position).getMoney());
        return convertView;
    }

    private String convertState(String state){
        String kq;
        switch (state){
            case "choxacnhan":  kq=" Chờ xác nhận";break;
            case "cholayhang":  kq=" Chờ lấy hàng";break;
            case "danggiao":    kq=" Đang giao";break;
            case "dagiao":      kq=" Đã giao";break;
            case "dahuy":       kq=" Đã hủy";break;
            case "trahang":     kq=" Trả hàng";break;
            default:            kq="Không xác định";break;
        }
        return kq;
    }
}
