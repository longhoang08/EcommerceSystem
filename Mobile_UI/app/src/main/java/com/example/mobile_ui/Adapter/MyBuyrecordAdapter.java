package com.example.mobile_ui.Adapter;
//this class to filter data following state

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mobile_ui.Fragment.BuyrecordFragment;
import com.example.mobile_ui.Fragment.MyBuyrecordFragment;
import com.example.mobile_ui.Model.BuyRecord;
import com.example.mobile_ui.Model.MyBuyRecord;

import java.util.ArrayList;

public class MyBuyrecordAdapter extends FragmentPagerAdapter {

    private ArrayList<String> itemMenu=new ArrayList<String>();//name of item menu "choxacnhan" "danggiao" "dagiao"...
    private int totalTabs;
    private ArrayList<MyBuyRecord> data;
    public MyBuyrecordAdapter(@NonNull FragmentManager fm, int totalTabs, ArrayList<MyBuyRecord> data) {
        super(fm);
        this.totalTabs = totalTabs;
        itemMenu.add("choxacnhan");
        itemMenu.add("danggiao");
        itemMenu.add("dagiao");
        this.data=data;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        MyBuyrecordFragment myBuyrecordFragment;
        switch (position) {
            case 0:
                myBuyrecordFragment = new MyBuyrecordFragment(filterData(itemMenu.get(0)));
                return myBuyrecordFragment;
            case 1:
                myBuyrecordFragment = new MyBuyrecordFragment(filterData(itemMenu.get(1)));
                return myBuyrecordFragment;
            case 2:
                myBuyrecordFragment = new MyBuyrecordFragment(filterData(itemMenu.get(2)));
                return myBuyrecordFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    //ham loc data theo "menu tiem"
    private ArrayList<MyBuyRecord> filterData(String state){
        ArrayList<MyBuyRecord> consequense= new ArrayList<MyBuyRecord>();
        for (MyBuyRecord record:data){
            if(record.getState()==state) consequense.add(record);
        }
        return consequense;
    }
}
