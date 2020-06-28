package com.example.mobile_ui.Adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mobile_ui.Fragment.ProductDetailDescriptionFragment;

public class DetailProductDescriptionAdapter extends FragmentPagerAdapter {
    private int totalTabs;

    public DetailProductDescriptionAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProductDetailDescriptionFragment productDetailDescriptionFragment = new ProductDetailDescriptionFragment();
                return productDetailDescriptionFragment;
//            case 1:
//                ProductDetailSpecificFragment productDetailSpecificFragment = new ProductDetailSpecificFragment();
//                return productDetailSpecificFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
