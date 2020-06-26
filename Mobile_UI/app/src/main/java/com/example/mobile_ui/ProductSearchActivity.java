package com.example.mobile_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mobile_ui.Adapter.ProductAdapter;
import com.example.mobile_ui.Model.Product;
import com.example.mobile_ui.View.ExpandHeightGridView;

import java.util.ArrayList;
import java.util.List;

public class ProductSearchActivity extends AppCompatActivity {

    ExpandHeightGridView expandHeightGridViewProduct;
    TextView textViewSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);

        textViewSearch = findViewById(R.id.textViewSearch);
        textViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        expandHeightGridViewProduct = findViewById(R.id.expandHeightGridViewProduct);
        List<Product> listProduct = new ArrayList<>();
        loadDataProduct(listProduct);
        ProductAdapter productAdapter = new ProductAdapter(listProduct);
        expandHeightGridViewProduct.setAdapter(productAdapter);
    }

    private void loadDataProduct(List<Product> listProduct) {
//        listProduct.add(new Product(R.drawable.icon_dragon_fruit, "Redmi Note 7", 45000, 4.5));
//        listProduct.add(new Product(R.drawable.icon_kiwi_fruit, "Redmi Note 7", 45000, 3.5));
//        listProduct.add(new Product(R.drawable.icon_dragon_fruit, "Redmi Note 7", 45000, 2.5));
//        listProduct.add(new Product(R.drawable.icon_pineapple, "Redmi Note 7", 45000, 3.5));
//        listProduct.add(new Product(R.drawable.icon_kiwi_fruit, "Redmi Note 7", 45000, 1.5));
//        listProduct.add(new Product(R.drawable.icon_dragon_fruit, "Redmi Note 7", 45000, 5.0));
//        listProduct.add(new Product(R.drawable.icon_kiwi_fruit, "Redmi Note 7", 45000, 4.5));
//        listProduct.add(new Product(R.drawable.icon_dragon_fruit, "Redmi Note 7", 45000, 4.5));
//        listProduct.add(new Product(R.drawable.icon_pineapple, "Redmi Note 7", 45000, 4.5));
//        listProduct.add(new Product(R.drawable.icon_dragon_fruit, "Redmi Note 7", 45000, 4.5));
    }
}
