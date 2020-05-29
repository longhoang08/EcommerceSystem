package com.example.mobile_ui.Adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mobile_ui.Fragment.BuyrecordFragment;
import com.example.mobile_ui.Fragment.ProductDetailDescriptionFragment;
import com.example.mobile_ui.Fragment.ProductDetailSpecificFragment;

import java.util.ArrayList;

public class BuyrecordAdapter extends FragmentPagerAdapter {

    private ArrayList<String> itemMenu=new ArrayList<String>();
    private int totalTabs;

    public BuyrecordAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
        itemMenu.add("choxacnhan");
        itemMenu.add("cholayhang");
        itemMenu.add("danggiao");
        itemMenu.add("dagiao");
        itemMenu.add("dahuy");
        itemMenu.add("trahang");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        BuyrecordFragment buyrecordFragment;
        switch (position) {
            case 0:
                buyrecordFragment = new BuyrecordFragment(itemMenu.get(0));
                return buyrecordFragment;
            case 1:
                buyrecordFragment = new BuyrecordFragment(itemMenu.get(1));
                return buyrecordFragment;
            case 2:
                buyrecordFragment = new BuyrecordFragment(itemMenu.get(2));
                return buyrecordFragment;
            case 3:
                buyrecordFragment = new BuyrecordFragment(itemMenu.get(3));
                return buyrecordFragment;
            case 4:
                buyrecordFragment = new BuyrecordFragment(itemMenu.get(4));
                return buyrecordFragment;
            case 5:
                buyrecordFragment = new BuyrecordFragment(itemMenu.get(5));
                return buyrecordFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
