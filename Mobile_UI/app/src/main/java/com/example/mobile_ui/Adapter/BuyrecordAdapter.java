package com.example.mobile_ui.Adapter;
//this class to filter data following state

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mobile_ui.Fragment.BuyrecordFragment;
import com.example.mobile_ui.Fragment.ProductDetailDescriptionFragment;
import com.example.mobile_ui.Fragment.ProductDetailSpecificFragment;
import com.example.mobile_ui.Model.BuyRecord;

import java.util.ArrayList;

public class BuyrecordAdapter extends FragmentPagerAdapter {

    private ArrayList<String> itemMenu=new ArrayList<String>();//name of item menu "choxacnhan" "danggiao" "dagiao"...
    private int totalTabs;
    private ArrayList<BuyRecord> data;
    public BuyrecordAdapter(@NonNull FragmentManager fm, int totalTabs, ArrayList<BuyRecord> data) {
        super(fm);
        this.totalTabs = totalTabs;
//        itemMenu.add("choxacnhan");
//        itemMenu.add("cholayhang");
        itemMenu.add("danggiao");
        itemMenu.add("dagiao");
//        itemMenu.add("dahuy");
        //itemMenu.add("trahang");
        this.data=data;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        BuyrecordFragment buyrecordFragment;
        switch (position) {
            case 0:
                buyrecordFragment = new BuyrecordFragment(filterData(itemMenu.get(0)));
                return buyrecordFragment;
            case 1:
                buyrecordFragment = new BuyrecordFragment(filterData(itemMenu.get(1)));
                return buyrecordFragment;
//            case 2:
//                buyrecordFragment = new BuyrecordFragment(filterData(itemMenu.get(2)));
//                return buyrecordFragment;
//            case 3:
//                buyrecordFragment = new BuyrecordFragment(filterData(itemMenu.get(3)));
//                return buyrecordFragment;
//            case 4:
//                buyrecordFragment = new BuyrecordFragment(filterData(itemMenu.get(4)));
//                return buyrecordFragment;
//            case 5:
//                buyrecordFragment = new BuyrecordFragment(filterData(itemMenu.get(5)));
//                return buyrecordFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    //ham loc data theo "menu tiem"
    private ArrayList<BuyRecord> filterData(String state){
        ArrayList<BuyRecord> consequense= new ArrayList<BuyRecord>();
        for (BuyRecord record:data){
            if(record.getState()==state) consequense.add(record);
        }
        return consequense;
    }
}
