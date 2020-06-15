package com.example.mobile_ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.mobile_ui.Adapter.MyBuyrecordAdapter;
import com.example.mobile_ui.Model.MyBuyRecord;
import com.example.mobile_ui.Model.Product;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MyBuyrecordActivity extends AppCompatActivity {
    ArrayList<MyBuyRecord> arrayBuyrecords=new ArrayList<MyBuyRecord>();
    TabLayout tabLayoutBuyrecord;
    ViewPager viewPagerBuyrecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_buyrecord);

        //fake data
        getArrayBuyrecords();

        //anh xa
        tabLayoutBuyrecord = findViewById(R.id.tabLayoutBuyrecord);
        viewPagerBuyrecord = findViewById(R.id.viewPagerBuyrecord);

        //
        viewPagerBuyrecord.setAdapter(new MyBuyrecordAdapter(getSupportFragmentManager(), tabLayoutBuyrecord.getTabCount(),arrayBuyrecords));
        viewPagerBuyrecord.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutBuyrecord));
        //
        tabLayoutBuyrecord.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerBuyrecord.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void getArrayBuyrecords(){
        //fake data
        ArrayList<Product> x=new ArrayList<Product>();
        x.add(new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120));
        x.add(new Product(R.drawable.icon_dragon_fruit,"thanh long",25000,100));
        ArrayList<Integer> y=new ArrayList<Integer>();
        y.add(new Integer(2));
        y.add(new Integer(3));
        arrayBuyrecords.add(new MyBuyRecord(1, "SHop A","choxacnhan", x, y,250000));
        arrayBuyrecords.add(new MyBuyRecord(2, "SHop A","choxacnhan", x, y,150000));
        arrayBuyrecords.add(new MyBuyRecord(3, "SHop A","choxacnhan", x,y,25000));

        arrayBuyrecords.add(new MyBuyRecord(4, "SHop A","danggiao",x, y,250000));
        arrayBuyrecords.add(new MyBuyRecord(5, "SHop A","danggiao", x,y,150000));
        arrayBuyrecords.add(new MyBuyRecord(6, "SHop A","danggiao", x,y,25000));

        arrayBuyrecords.add(new MyBuyRecord(7, "SHop A","dagiao", x,y,250000));
        arrayBuyrecords.add(new MyBuyRecord(8, "SHop A","dagiao", x,y,150000));
        arrayBuyrecords.add(new MyBuyRecord(9, "SHop A","dagiao",x, y,25000));
        arrayBuyrecords.add(new MyBuyRecord(10, "SHop 8","dagiao",x, y,10000000));
    }
}
