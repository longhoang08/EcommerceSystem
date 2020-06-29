package com.example.mobile_ui.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobile_ui.ListCategoryActivity;
import com.example.mobile_ui.Model.Category;
import com.example.mobile_ui.ProductSearchActivity;
import com.example.mobile_ui.R;

import java.util.List;

public class CategoryProductScrollAdapter extends RecyclerView.Adapter<CategoryProductScrollAdapter.ViewHolder> {
    private List<Category> listCategoryProduct;

    public CategoryProductScrollAdapter(List<Category> listCategoryProduct) {
        this.listCategoryProduct = listCategoryProduct;
    }

    @NonNull
    @Override
    public CategoryProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_product_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryProductScrollAdapter.ViewHolder holder, int position) {
        String categoryImage = listCategoryProduct.get(position).getImage();
        String nameCategory = listCategoryProduct.get(position).getName();
        holder.setCategoryImage(categoryImage);
        holder.setNameCategory(nameCategory);
    }

    @Override
    public int getItemCount() {
        return listCategoryProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewCategory;
        private TextView textViewNameCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCategory = itemView.findViewById(R.id.imageViewCategory);
            textViewNameCategory = itemView.findViewById(R.id.textViewNameCategory);
        }
        public void setCategoryImage(final String categoryImage) {
            Glide.with(this.imageViewCategory.getContext())
                    .load(categoryImage).override(113, 113).centerCrop()
                    .into(imageViewCategory);
//            this.imageViewCategory.setImageResource(categoryImage);
        }

        public void setNameCategory(final String nameCategory) {
            this.textViewNameCategory.setText(nameCategory);
            ((ViewGroup)this.textViewNameCategory.getParent()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ProductSearchActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("keyWord", nameCategory);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
