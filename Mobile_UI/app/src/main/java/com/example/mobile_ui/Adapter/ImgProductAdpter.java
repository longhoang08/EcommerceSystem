package com.example.mobile_ui.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_ui.R;

import java.util.ArrayList;
import java.util.List;

//public class ImgProductAdpter extends BaseAdapter {
//    Context myContext;
//    int myLayout;
//    ArrayList<Bitmap> arrayImg;
//
//    public ImgProductAdpter(Context context,int layout, ArrayList<Bitmap> ImgList){
//        myContext = context;
//        myLayout = layout;
//        arrayImg = ImgList;
//    }
//    @Override
//    public int getCount() {
//        return arrayImg.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        convertView = inflater.inflate(myLayout,null);
//
//        //anh xa gan giai tri
//        ImageView imgPro = (ImageView) convertView.findViewById(R.id.imgPro);
//        imgPro.setImageBitmap(arrayImg.get(position));
//        return convertView;
//    }
//}
public class ImgProductAdpter extends RecyclerView.Adapter<ImgProductAdpter.MyViewHolder> {
    private ArrayList<Bitmap> imgList;
    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        MyViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.imgPro);
        }
    }
    public ImgProductAdpter(ArrayList<Bitmap> List) {
        this.imgList = List;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_img_list_item, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Bitmap img = imgList.get(position);
        holder.img.setImageBitmap(img);
    }
    @Override
    public int getItemCount() {
        return imgList.size();
    }
}