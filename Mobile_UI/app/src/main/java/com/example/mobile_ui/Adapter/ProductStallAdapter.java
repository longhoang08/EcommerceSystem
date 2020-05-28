package com.example.mobile_ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_ui.Model.Product;
import com.example.mobile_ui.R;

import java.util.ArrayList;

public class ProductStallAdapter extends BaseAdapter {
    private ArrayList<Product> products = new ArrayList<Product>();
    Context myContext;
    int myLayout;
    public ProductStallAdapter(Context context,int layout, ArrayList<Product> ImgList){
        myContext = context;
        myLayout = layout;
        products = ImgList;
    }
    @Override
    public int getCount() {
        return products.size();
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
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout,null);

        //anh xa gan giai tri
        ImageView imgPro = (ImageView) convertView.findViewById(R.id.stallProImg);
        TextView name = (TextView) convertView.findViewById(R.id.stallProName);
        TextView price = (TextView) convertView.findViewById(R.id.stallProPrice);
        TextView number = (TextView) convertView.findViewById(R.id.stallProNumber);

        imgPro.setImageResource(products.get(position).getImageRepresent());
        name.setText("Tên sản phẩm : " + products.get(position).getName());
        price.setText("Giá : " + products.get(position).getPrice());
        number.setText("Kho : "+products.get(position).getNumber());
        return convertView;
    }
}
