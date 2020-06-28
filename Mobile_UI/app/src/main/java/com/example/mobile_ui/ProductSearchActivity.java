package com.example.mobile_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_ui.Adapter.ProductAdapter;
import com.example.mobile_ui.Model.Product;
import com.example.mobile_ui.Model.chooseImage;
import com.example.mobile_ui.Model.filterProduct;
import com.example.mobile_ui.View.ExpandHeightGridView;

import java.util.ArrayList;
import java.util.List;

public class ProductSearchActivity extends AppCompatActivity {

    ExpandHeightGridView expandHeightGridViewProduct;
    TextView textViewSearch;
    ImageView imageButtonFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);

        imageButtonFilter = findViewById(R.id.imageButtonFilter);
        imageButtonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final filterProduct filter = new filterProduct();
                filter.show(getSupportFragmentManager(), "");
            }
        });
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
        String url = "https://img1.looper.com/img/gallery/we-now-know-the-one-time-batman-was-supposed-to-die/intro-1576009072.jpg";
        listProduct.add(new Product(url, "Redmi Note 7", 45000, 45, "1"));
        listProduct.add(new Product(url, "Redmi Note 7", 45000, 45, "1"));
        listProduct.add(new Product(url, "Redmi Note 7", 45000, 45, "1"));
    }
}
