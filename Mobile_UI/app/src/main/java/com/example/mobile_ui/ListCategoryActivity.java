package com.example.mobile_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mobile_ui.Adapter.CategoryProductListAdapter;
import com.example.mobile_ui.Model.Category;
import com.example.mobile_ui.View.ExpandHeightGridView;

import java.util.ArrayList;
import java.util.List;

public class ListCategoryActivity extends AppCompatActivity {

    private ExpandHeightGridView expandHeightGridViewCategoryProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);

        expandHeightGridViewCategoryProduct = findViewById(R.id.expandHeightGridViewCategoryProduct);
        List<Category> listCategoryProduct = new ArrayList<>();
        loadDataCategoryProduct(listCategoryProduct);
        CategoryProductListAdapter categoryProductListAdapter = new CategoryProductListAdapter(listCategoryProduct);
        expandHeightGridViewCategoryProduct.setAdapter(categoryProductListAdapter);
    }

    private void loadDataCategoryProduct(List<Category> listCategoryProduct) {
        listCategoryProduct.add(new Category(R.drawable.icon_dragon_fruit, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_kiwi_fruit, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_pineapple, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_dragon_fruit, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_pineapple, "Cham soc va giat giu n..."));
        listCategoryProduct.add(new Category(R.drawable.icon_pineapple, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_dragon_fruit, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_kiwi_fruit, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_pineapple, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_pineapple, "Rồng hoa quả"));
    }
}
