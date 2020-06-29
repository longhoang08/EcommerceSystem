package com.example.mobile_ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

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
        final List<Review> listReviewProduct = loadDataReviewProduct( 6);;
        final ReviewProductAdapter reviewProductAdapter = new ReviewProductAdapter(listReviewProduct);
        expandHeightGridViewReviewProduct.setAdapter(reviewProductAdapter);
        // su kien xem binh luan theo so sao
        linearLayoutOptionSeeReivew = findViewById(R.id.linearLayoutOptionSeeReivew);
        buttonReviewChoosing = (Button) ((LinearLayout) linearLayoutOptionSeeReivew.getChildAt(0)).getChildAt(0);
        for (int i = 0; i < 2; i++) {
            LinearLayout linearLayoutChild = (LinearLayout) linearLayoutOptionSeeReivew.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                final Button button = (Button) linearLayoutChild.getChildAt(j);
                final int finalI = i;
                final int finalJ = j;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonReviewChoosing.setBackgroundResource(android.R.drawable.btn_default);
                        int numStart = 6- finalI*3-finalJ;
                        expandHeightGridViewReviewProduct.setAdapter(new ReviewProductAdapter(loadDataReviewProduct(numStart)));
                        buttonReviewChoosing = button;
                        buttonReviewChoosing.setBackgroundColor(Color.parseColor("#8BC34A"));
                    }
                });
            }
        }
    }

    private List<Review> loadDataReviewProduct(int numStart) {
        List<Review> listReviewProduct = new ArrayList<>();
        // 6 sao - load tat ca binh luan
        if (numStart == 6) {
            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 4, "Rất tốt, rất tốt, rất tốt"));
//            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 5, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
//            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 3, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
//            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 3, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
//            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 4, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
//            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 5, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
//            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 3, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
//            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 3, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
//            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 4, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
//            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 5, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
//            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 3, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
//            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 3, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
        } else {
//            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", numStart, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
//            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", numStart, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
//            listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", numStart, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
        }
        return listReviewProduct;
    }
}
