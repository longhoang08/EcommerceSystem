package com.example.mobile_ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.mobile_ui.Adapter.BuyrecordAdapter;
import com.example.mobile_ui.Model.BuyRecord;
import com.example.mobile_ui.Model.Customer;
import com.example.mobile_ui.Model.Product;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class BuyRecordActivity extends AppCompatActivity {
    ArrayList<BuyRecord> arrayBuyrecords=new ArrayList<BuyRecord>();
    TabLayout tabLayoutBuyrecord;
    ViewPager viewPagerBuyrecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_record);

        //fake data
        getArrayBuyrecords();

        //anh xa
        tabLayoutBuyrecord = findViewById(R.id.tabLayoutBuyrecord);
        viewPagerBuyrecord = findViewById(R.id.viewPagerBuyrecord);

        //
        viewPagerBuyrecord.setAdapter(new BuyrecordAdapter(getSupportFragmentManager(), tabLayoutBuyrecord.getTabCount(),arrayBuyrecords));
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

    //fake data
    private void getArrayBuyrecords(){
        Customer an = new Customer(R.drawable.icon_kiwi_fruit,"Thành An","Nam",
                "11/8/1999","Hà Nam","0966947994","12345");
        String url = "https://lh3.googleusercontent.com/K6QT0fv2GqVJW4iE0zwMyUyL7X0IR_eOO7ENtxSqlvcpeDcn3vbYD56n0rPd3xuKpA6GSScSwbsj_4aE7A";
        String url1 = "https://lh3.googleusercontent.com/Oq3iOy4uHH2jXHC0m-Z4jZ_AR55uISagPjaBlsUvpmk92bT1IQNK1jlTdqW5dpM44GDmv71RwZBoePe-2_Va";
        arrayBuyrecords.add(new BuyRecord("danggiao", 1, an,
                new Product(url,"Viên Nang Nano Fucoidan Gold Jpanwell",5850000,120, "1"),
                1,5850000));
//        arrayBuyrecords.add(new BuyRecord("danggiao", 2, an,
//                new Product(url,"banana",12000,120, "2"),
//                2,25000));

        arrayBuyrecords.add(new BuyRecord("dagiao", 3, an,
                new Product(url1,"Xích đu Chicco Polly Swing màu bạc",6900000,140, "#"),
                1,6900000));
//        arrayBuyrecords.add(new BuyRecord("dagiao", 4, an,
//                new Product(url,"banana",12000,120,"4"),
//                2,25000));

//        arrayBuyrecords.add(new BuyRecord("danggiao", id, an,
//                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
//                2,25000));
//        arrayBuyrecords.add(new BuyRecord("danggiao", id, an,
//                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
//                2,25000));
//
//        arrayBuyrecords.add(new BuyRecord("dagiao", id, an,
//                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
//                2,25000));
//        arrayBuyrecords.add(new BuyRecord("dagiao", id, an,
//                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
//                2,25000));
//
//        arrayBuyrecords.add(new BuyRecord("dahuy", id, an,
//                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
//                2,25000));
//        arrayBuyrecords.add(new BuyRecord("dahuy", id, an,
//                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
//                2,25000));
//
//        arrayBuyrecords.add(new BuyRecord("trahang", id, an,
//                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
//                2,25000));
//        arrayBuyrecords.add(new BuyRecord("trahang", id, an,
//                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
//                2,25000));
    }
}
