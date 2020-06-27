package com.example.mobile_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobile_ui.Adapter.ProductStallAdapter;
import com.example.mobile_ui.Model.Product;

import java.util.ArrayList;

public class StallActivity extends AppCompatActivity {
    TextView btnAddProduct;
    TextView showBuyRecord;
    ListView stallProListView;
//    ImageView stall_infor_img_main;
//    TextView userName;
//    TextView numOfPro;

    ArrayList<Product> products = new ArrayList<Product>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stall);
        //anh xa
        btnAddProduct = findViewById(R.id.btn_add_product);
        showBuyRecord = findViewById(R.id.stallBuyRecord);
        stallProListView = findViewById(R.id.stallProListView);
//        stall_infor_img_main = findViewById(R.id.stall_infor_img_main);
//        userName = findViewById(R.id.userName);
//        numOfPro = findViewById(R.id.numOfPro);

        //fake data
        String url = "https://image.thanhnien.vn/800/uploaded/tuyenth/2019_07_07/47585172_614712392300363_2894160633832439484_n_jxdz.jpg";
        products.add(new Product(url,"banana",12000,120, "1"));
        products.add(new Product(url,"apple",12000,100, "2"));

        ProductStallAdapter adapter = new ProductStallAdapter(
                StallActivity.this,R.layout.stall_product_item,products
        );
        stallProListView.setAdapter(adapter);

        //sang màn hình thêm 1 sp vào stall
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StallActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });

        //sang màn xem những đơn hàng
        showBuyRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StallActivity.this, BuyRecordActivity.class);
                startActivity(intent);
            }
        });

        //click to view one product
        stallProListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StallActivity.this,StallDetailProductActivity.class);
                startActivity(intent);
            }
        });
    }
}
