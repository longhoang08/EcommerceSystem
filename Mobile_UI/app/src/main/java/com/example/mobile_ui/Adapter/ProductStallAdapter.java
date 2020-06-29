package com.example.mobile_ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

//        imgPro.setImageResource(Integer.parseInt(products.get(position).getImageRepresent()));
        if (products.get(position).getImageRepresent() != null) {
            Glide.with(parent.getContext())
                    .load(products.get(position).getImageRepresent()).override(80, 80).centerCrop()
                    .into(imgPro);
        }
        String namePro = products.get(position).getName();
        if (namePro.length() > 30) {
            namePro = namePro.substring(0, 30)+"...";
        }
        name.setText(namePro);
        price.setText(products.get(position).getPrice()+ " đ");
        number.setText("Kho : "+products.get(position).getNumber());
        return convertView;
    }
}
