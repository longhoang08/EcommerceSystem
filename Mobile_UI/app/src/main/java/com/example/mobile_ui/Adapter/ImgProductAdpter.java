package com.example.mobile_ui.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Context;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_ui.R;

import java.util.ArrayList;
import java.util.List;

public class ImgProductAdpter extends RecyclerView.Adapter<ImgProductAdpter.MyViewHolder> {
    //if là true, có sự kiện xóa trên ảnh, nếu ko thì không có
    private boolean stateOfEventItem = true;
    private ArrayList<Bitmap> imgList;
    private Context context;
    public ImgProductAdpter(ArrayList<Bitmap> List,Context context,boolean stateOfEventItem) {
        this.imgList = List;
        this.context=context;
        this.stateOfEventItem=stateOfEventItem;
    }

    public boolean isStateOfEventItem() {
        return stateOfEventItem;
    }

    public void setStateOfEventItem(boolean stateOfEventItem) {
        this.stateOfEventItem = stateOfEventItem;
    }

    //class
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        ItemImgClickListener itemImgClickListener;
        public void setItemImgClickListener(ItemImgClickListener itemImgClickListener) {
            this.itemImgClickListener = itemImgClickListener;
        }

        MyViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.imgPro);
            view.setOnClickListener(this);// quan trọng
        }

        @Override
        public void onClick(View v) {
            itemImgClickListener.onClick(v,getAdapterPosition());
        }
    }
    //end class

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

        holder.setItemImgClickListener(new ItemImgClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(!stateOfEventItem) return;
                //xóa và cập nhật ở đây
                xacnhanxoa(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return imgList.size();
    }

    private void xacnhanxoa(final int position){
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Xác nhận xóa");
        alert.setMessage("bạn chắc chắn muốn xóa ảnh này chứ ?");
        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteItem(position);
            }
        });
        alert.setNegativeButton("Không", null);
        alert.show();
    }

    private void deleteItem(int position){
        imgList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, imgList.size());
    }
}