package com.example.mobile_ui.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.mobile_ui.R;

import java.util.List;

public class ProductImagesAdapter extends PagerAdapter {
    private List<String> imagesProduct;

    public ProductImagesAdapter(List<String> imagesProduct) {
        this.imagesProduct = imagesProduct;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView productImage = new ImageView(container.getContext());
        Glide.with(container.getContext())
                .load(imagesProduct.get(position)).override(350, 350).centerCrop()
                .into(productImage);
        container.addView(productImage, 0);
        return productImage;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }

    @Override
    public int getCount() {
        return imagesProduct.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}

