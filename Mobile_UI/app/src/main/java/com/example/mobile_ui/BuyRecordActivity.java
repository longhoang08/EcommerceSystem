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
        String url = "https://image2.tienphong.vn/w665/Uploaded/2020/bzivobpc/2019_12_26/vanda_ip_man_4_paui.jpg";
        arrayBuyrecords.add(new BuyRecord("danggiao", 1, an,
                new Product(url,"banana",12000,120, "1"),
                2,25000));
        arrayBuyrecords.add(new BuyRecord("danggiao", 2, an,
                new Product(url,"banana",12000,120, "2"),
                2,25000));

        arrayBuyrecords.add(new BuyRecord("dagiao", 3, an,
                new Product(url,"banana",12000,120, "#"),
                2,25000));
        arrayBuyrecords.add(new BuyRecord("dagiao", 4, an,
                new Product(url,"banana",12000,120,"4"),
                2,25000));

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
