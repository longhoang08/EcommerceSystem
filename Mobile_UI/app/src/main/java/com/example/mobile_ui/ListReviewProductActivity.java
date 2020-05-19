package com.example.mobile_ui;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_ui.Adapter.ReviewProductAdapter;
import com.example.mobile_ui.Model.Review;
import com.example.mobile_ui.View.ExpandHeightGridView;

import java.util.ArrayList;
import java.util.List;

public class ListReviewProductActivity extends AppCompatActivity {

    private ExpandHeightGridView expandHeightGridViewReviewProduct;
    private LinearLayout linearLayoutOptionSeeReivew;
    private Button buttonReviewChoosing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_review_product);

        // load review vao list
        expandHeightGridViewReviewProduct = findViewById(R.id.expandHeightGridViewReviewProduct);
        final List<Review>[] listReviewProduct = new List[]{new ArrayList<>()};
        loadDataReviewProduct(listReviewProduct[0], 6);
        final ReviewProductAdapter reviewProductAdapter = new ReviewProductAdapter(listReviewProduct[0]);
        expandHeightGridViewReviewProduct.setAdapter(reviewProductAdapter);
        // su kien xem binh luan theo so sao
        linearLayoutOptionSeeReivew = findViewById(R.id.linearLayoutOptionSeeReivew);
        buttonReviewChoosing = (Button) ((LinearLayout) linearLayoutOptionSeeReivew.getChildAt(0)).getChildAt(0);
        for (int i = 0; i < 2; i++) {
            LinearLayout linearLayoutChild = (LinearLayout) linearLayoutOptionSeeReivew.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                final Button[] button = {(Button) linearLayoutChild.getChildAt(j)};
                final int finalI = i;
                final int finalJ = j;
                button[0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonReviewChoosing.setBackgroundResource(android.R.drawable.btn_default);
                        int numStart = 6- finalI*3 - finalJ;
                        loadDataReviewProduct(listReviewProduct[0], numStart);
                        reviewProductAdapter.notifyDataSetChanged();
                        buttonReviewChoosing = button[0];
                        buttonReviewChoosing.setBackgroundColor(Color.parseColor("#8BC34A"));
                    }
                });
            }
        }
    }

    private void loadDataReviewProduct(List<Review> listReviewProduct, int numStart) {
        for (int i = 0; i < listReviewProduct.size(); i++) {
            listReviewProduct.remove(i);
        }
        // 6 sao - load tat ca binh luan
        if (numStart == 6) {
            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 4, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 5, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 3, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
        } else {
            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", numStart, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", numStart, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", numStart, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
        }
    }
}
