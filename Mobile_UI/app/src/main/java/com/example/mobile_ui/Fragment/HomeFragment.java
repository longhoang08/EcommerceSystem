package com.example.mobile_ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_ui.Adapter.CategoryProductScrollAdapter;
import com.example.mobile_ui.Adapter.ProductAdapter;
import com.example.mobile_ui.DetailProductActivity;
import com.example.mobile_ui.ListCategoryActivity;
import com.example.mobile_ui.Model.Category;
import com.example.mobile_ui.Model.Product;
import com.example.mobile_ui.R;
import com.example.mobile_ui.View.ExpandHeightGridView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    // danh sach the loai
    private RecyclerView recyclerViewCategoryProduct;
    private Button buttonViewAllCategoryProduct;
    // danh sach san pham
    private ExpandHeightGridView expandHeightGridViewProduct;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // load danh muc san pham
        recyclerViewCategoryProduct = root.findViewById(R.id.recyclerviewCategoryProduct);
        List<Category> listCategoryProduct = new ArrayList<>();
        loadDataCategoryProduct(listCategoryProduct);
        // thiet ke 2 hang truot theo chieu ngang
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryProduct.setLayoutManager(gridLayoutManager);
        CategoryProductScrollAdapter categoryProductAdapter = new CategoryProductScrollAdapter(listCategoryProduct);
        recyclerViewCategoryProduct.setAdapter(categoryProductAdapter);
        // end load danh muc san pham
        // load san pham
        expandHeightGridViewProduct = root.findViewById(R.id.expandHeightGridViewProduct);
        List<Product> listProduct = new ArrayList<>();
        loadDataProduct(listProduct);
        ProductAdapter productAdapter = new ProductAdapter(listProduct);
        expandHeightGridViewProduct.setAdapter(productAdapter);
        //end load san pham
        //su kien
        // man hinh tat ca the loai
        buttonViewAllCategoryProduct = root.findViewById(R.id.buttonViewAllCategoryProduct);
        buttonViewAllCategoryProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListCategoryActivity.class);
                startActivity(intent);
            }
        });
        // chon 1 san pham -> trang chi tiet
        expandHeightGridViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetailProductActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

    private void loadDataProduct(List<Product> listProduct) {
        listProduct.add(new Product(R.drawable.icon_dragon_fruit, "Redmi Note 7", 45000, 4.5));
        listProduct.add(new Product(R.drawable.icon_kiwi_fruit, "Redmi Note 7", 45000, 3.5));
        listProduct.add(new Product(R.drawable.icon_dragon_fruit, "Redmi Note 7", 45000, 2.5));
        listProduct.add(new Product(R.drawable.icon_pineapple, "Redmi Note 7", 45000, 3.5));
        listProduct.add(new Product(R.drawable.icon_kiwi_fruit, "Redmi Note 7", 45000, 1.5));
        listProduct.add(new Product(R.drawable.icon_dragon_fruit, "Redmi Note 7", 45000, 5.0));
        listProduct.add(new Product(R.drawable.icon_kiwi_fruit, "Redmi Note 7", 45000, 4.5));
        listProduct.add(new Product(R.drawable.icon_dragon_fruit, "Redmi Note 7", 45000, 4.5));
        listProduct.add(new Product(R.drawable.icon_pineapple, "Redmi Note 7", 45000, 4.5));
        listProduct.add(new Product(R.drawable.icon_dragon_fruit, "Redmi Note 7", 45000, 4.5));
    }

    private void loadDataCategoryProduct(List<Category> listCategoryProduct) {
        listCategoryProduct.add(new Category(R.drawable.icon_dragon_fruit, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_kiwi_fruit, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_pineapple, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_dragon_fruit, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_kiwi_fruit, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_pineapple, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_dragon_fruit, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_kiwi_fruit, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_pineapple, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_pineapple, "Rồng hoa quả"));
    }
}

