package com.example.mobile_ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_ui.Model.Category;
import com.example.mobile_ui.R;

import java.util.List;

public class CategoryProductListAdapter extends BaseAdapter {
    private List<Category> listCategoryProduct;

    public CategoryProductListAdapter(List<Category> listCategoryProduct) {
        this.listCategoryProduct = listCategoryProduct;
    }

    @Override
    public int getCount() {
        return listCategoryProduct.size();
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
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_product_item, null);
            // anh xa
            ImageView imageViewCategoryProduct = view.findViewById(R.id.imageViewCategory);
            TextView textViewNameCategory = view.findViewById(R.id.textViewNameCategory);
            // gan gia tri
            imageViewCategoryProduct.setImageResource(listCategoryProduct.get(position).getImage());
            textViewNameCategory.setText(listCategoryProduct.get(position).getName());
        } else {
            view = convertView;
        }
        return view;

    }
}

