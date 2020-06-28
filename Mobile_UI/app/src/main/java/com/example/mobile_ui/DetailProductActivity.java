package com.example.mobile_ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_ui.Adapter.DetailProductDescriptionAdapter;
import com.example.mobile_ui.Adapter.ProductImagesAdapter;
import com.example.mobile_ui.Adapter.ReviewProductAdapter;
import com.example.mobile_ui.Model.Review;
import com.example.mobile_ui.View.ExpandHeightGridView;
import com.example.mobile_ui.View.ExpandHeightViewPager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class DetailProductActivity extends AppCompatActivity {

    // viewpager la slide, tablayout cac dau cham ben duoi slide
    private ViewPager viewPagerImagesProduct;
    private TabLayout tabLayoutImagesProduct;
    // nut yeu thich san pham
//    private FloatingActionButton floatingactionbuttonFavorite;
    boolean favouriteProduct = false;

    private ExpandHeightViewPager viewPagerProductDescription;
    private TabLayout tabLayoutProductDescription;

    private ExpandHeightGridView expandHeightGridViewReviewProduct;
    private Button buttonViewAllReviewProduct;
    // mua hang
    private TextView textViewAddToCart, textViewBuyNow, textViewNoticeAddProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        // load slide anh
        viewPagerImagesProduct = findViewById(R.id.viewPagerImagesProduct);
        tabLayoutImagesProduct = findViewById(R.id.tabLayoutImagesProduct);
        List<Integer> imagesProduct = new ArrayList<>();
        // mang anh
        loadDataImagesProduct(imagesProduct);
        ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(imagesProduct);
        viewPagerImagesProduct.setAdapter(productImagesAdapter);
        tabLayoutImagesProduct.setupWithViewPager(viewPagerImagesProduct, true);
        // end load slide anh
        /*// yeu thich san pham
        floatingactionbuttonFavorite = findViewById(R.id.floatingactionbuttonFavorite);
        floatingactionbuttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favouriteProduct){
                    favouriteProduct = false;
                    floatingactionbuttonFavorite.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                } else {
                    favouriteProduct = true;
                    floatingactionbuttonFavorite.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FFCC0000")));
                }
            }
        });*/
        // mieu ta ro san pham = 2 phan mo ta, chi tiet
        viewPagerProductDescription = findViewById(R.id.viewPagerProductDescription);//View hiện mỗi tab
        tabLayoutProductDescription = findViewById(R.id.tabLayoutProductDescription);// gồm thanh chọn
        viewPagerProductDescription.setAdapter(new DetailProductDescriptionAdapter(getSupportFragmentManager(), tabLayoutProductDescription.getTabCount()));
        viewPagerProductDescription.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutProductDescription));
        tabLayoutProductDescription.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerProductDescription.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // load review
        expandHeightGridViewReviewProduct = findViewById(R.id.expandHeightGridViewReviewProduct);
        List<Review> listReviewProduct = new ArrayList<>();
        loadDataReviewProduct(listReviewProduct);
        ReviewProductAdapter reviewProductAdapter = new ReviewProductAdapter(listReviewProduct);
        expandHeightGridViewReviewProduct.setAdapter(reviewProductAdapter);
        // end load review
        // chuyển sang trang load tất cả bình luận
        buttonViewAllReviewProduct = findViewById(R.id.buttonViewAllReviewProduct);
        buttonViewAllReviewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailProductActivity.this, ListReviewProductActivity.class);
                startActivity(intent);
            }
        });
        // su kien mua hang
        textViewAddToCart = findViewById(R.id.textViewAddToCart);
        textViewBuyNow = findViewById(R.id.textViewBuyNow);
        textViewNoticeAddProduct = findViewById(R.id.textViewNoticeAddProduct);
        textViewAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewNoticeAddProduct.setVisibility(View.VISIBLE);
                new CountDownTimer(500, 500) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        textViewNoticeAddProduct.setVisibility(View.INVISIBLE);
                    }
                }.start();
            }
        });
        textViewBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
    private void loadDataReviewProduct(List<Review> listReviewProduct) {
        listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 4, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
        listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 4, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
        listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 4, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
    }

    private void loadDataImagesProduct(List<Integer> imagesProduct) {
        imagesProduct.add(R.drawable.icon_pineapple);
        imagesProduct.add(R.drawable.icon_kiwi_fruit);
        imagesProduct.add(R.drawable.icon_dragon_fruit);
        imagesProduct.add(R.drawable.icon_pineapple);
        imagesProduct.add(R.drawable.icon_dragon_fruit);
    }
}
