package com.example.mobile_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mobile_ui.Adapter.BuyrecordAdapter;
import com.example.mobile_ui.Adapter.DetailProductDescriptionAdapter;
import com.example.mobile_ui.Model.BuyRecord;
import com.example.mobile_ui.Model.Product;
import com.example.mobile_ui.View.ExpandHeightViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class BuyRecordActivity extends AppCompatActivity {
    ArrayList<BuyRecord> arrayBuyrecords=new ArrayList<BuyRecord>();
    TabLayout tabLayoutBuyrecord;
    ExpandHeightViewPager viewPagerBuyrecord;
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

    private void getArrayBuyrecords(){
        //fake data
        arrayBuyrecords.add(new BuyRecord("choxacnhan",
                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
                2,25000));
        arrayBuyrecords.add(new BuyRecord("choxacnhan",
                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
                2,25000));
        arrayBuyrecords.add(new BuyRecord("choxacnhan",
                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
                2,25000));

        arrayBuyrecords.add(new BuyRecord("cholayhang",
                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
                2,25000));
        arrayBuyrecords.add(new BuyRecord("cholayhang",
                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
                2,25000));
        arrayBuyrecords.add(new BuyRecord("cholayhang",
                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
                2,25000));

        arrayBuyrecords.add(new BuyRecord("danggiao",
                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
                2,25000));
        arrayBuyrecords.add(new BuyRecord("danggiao",
                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
                2,25000));
        arrayBuyrecords.add(new BuyRecord("danggiao",
                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
                2,25000));

        arrayBuyrecords.add(new BuyRecord("dagiao",
                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
                2,25000));
        arrayBuyrecords.add(new BuyRecord("dagiao",
                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
                2,25000));
        arrayBuyrecords.add(new BuyRecord("dagiao",
                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
                2,25000));

        arrayBuyrecords.add(new BuyRecord("dahuy",
                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
                2,25000));
        arrayBuyrecords.add(new BuyRecord("dahuy",
                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
                2,25000));
        arrayBuyrecords.add(new BuyRecord("dahuy",
                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
                2,25000));

        arrayBuyrecords.add(new BuyRecord("trahang",
                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
                2,25000));
        arrayBuyrecords.add(new BuyRecord("trahang",
                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
                2,25000));
        arrayBuyrecords.add(new BuyRecord("trahang",
                new Product(R.drawable.icon_kiwi_fruit,"banana",12000,120),
                2,25000));
    }
}
