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
        ArrayList<Product> z=new ArrayList<Product>();
        x.add(new Product("https://lh3.googleusercontent.com/Ya1nGWiIvVQZBkRdN0fZyFXZZ6e5VS0JDtmZ2Wln4rIOnFK5ja8Ld8rdSoSwsbJCvW4MFRfcaz8yA_tcKOg","Bột Ca Cao NESTLÉ Hot Choco Hộp 10...",54500,120, "2"));
        z.add(new Product("https://lh3.googleusercontent.com/fNQKDDLRRUTd_BZ8hXR3GGAbrXUj57Ffhai-W4qYW7Id7finUrO6udwC4wd-5UyGcJ7LWVgHAuFw5o7tvhfI","Australia's Own Sữa tươi tiệt trùng nguyên...",35000,100, "2"));
        ArrayList<Integer> y=new ArrayList<Integer>();
        y.add(new Integer(2));
//        y.add(new Integer(3));
//        arrayBuyrecords.add(new MyBuyRecord(1, "SHop A","choxacnhan", x, y,250000));
//        arrayBuyrecords.add(new MyBuyRecord(2, "SHop A","choxacnhan", x, y,150000));
//        arrayBuyrecords.add(new MyBuyRecord(3, "SHop A","choxacnhan", x,y,25000));

        arrayBuyrecords.add(new MyBuyRecord(4, "Cà phê Trung Nguyên","danggiao",x, y,114000));
        arrayBuyrecords.add(new MyBuyRecord(4, "Sữa tươi Mộc Châu","dagiao",z, y,75000));
//        arrayBuyrecords.add(new MyBuyRecord(5, "SHop A","danggiao", x,y,150000));
//        arrayBuyrecords.add(new MyBuyRecord(6, "SHop A","danggiao", x,y,25000));
//
//        arrayBuyrecords.add(new MyBuyRecord(7, "SHop A","dagiao", x,y,250000));
//        arrayBuyrecords.add(new MyBuyRecord(8, "SHop A","dagiao", x,y,150000));
//        arrayBuyrecords.add(new MyBuyRecord(9, "SHop A","dagiao",x, y,25000));
//        arrayBuyrecords.add(new MyBuyRecord(10, "SHop 8","dagiao",x, y,10000000));
    }
}
