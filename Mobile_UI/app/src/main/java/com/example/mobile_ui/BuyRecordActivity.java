package com.example.mobile_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mobile_ui.Adapter.BuyrecordAdapter;
import com.example.mobile_ui.Adapter.DetailProductDescriptionAdapter;
import com.example.mobile_ui.View.ExpandHeightViewPager;
import com.google.android.material.tabs.TabLayout;

public class BuyRecordActivity extends AppCompatActivity {
    TabLayout tabLayoutBuyrecord;
    ExpandHeightViewPager viewPagerBuyrecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_record);

        //anh xa
        tabLayoutBuyrecord = findViewById(R.id.tabLayoutBuyrecord);
        viewPagerBuyrecord = findViewById(R.id.viewPagerBuyrecord);

        //
        viewPagerBuyrecord.setAdapter(new BuyrecordAdapter(getSupportFragmentManager(), tabLayoutBuyrecord.getTabCount()));
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
}
