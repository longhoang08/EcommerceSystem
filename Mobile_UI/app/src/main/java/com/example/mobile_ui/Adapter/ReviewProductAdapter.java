package com.example.mobile_ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mobile_ui.Model.Product;
import com.example.mobile_ui.Model.Review;
import com.example.mobile_ui.R;

import java.util.List;

public class ReviewProductAdapter extends BaseAdapter {
    private List<Review> listReviewProduct;

    public ReviewProductAdapter(List<Review> listReviewProduct) {
        this.listReviewProduct = listReviewProduct;
    }

    @Override
    public int getCount() {
        return listReviewProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_product_item, null);
            // anh xa
            ImageView imageViewCustomer = view.findViewById(R.id.imageViewCustomer);
            TextView textViewNameCustomer = view.findViewById(R.id.textViewNameCustomer);
            RatingBar ratingBarReview = view.findViewById(R.id.ratingBarReview);
            TextView textViewComment = view.findViewById(R.id.textViewComment);
            TextView textViewTimeReview = view.findViewById(R.id.textViewTimeReview);
            // gan gia tri
            imageViewCustomer.setImageResource(listReviewProduct.get(position).getImageCustomer());
            textViewNameCustomer.setText(listReviewProduct.get(position).getNameCustormer());
            ratingBarReview.setRating(listReviewProduct.get(position).getRating());
            textViewComment.setText(listReviewProduct.get(position).getComment());
            textViewTimeReview.setText(listReviewProduct.get(position).getTimeReview());
        } else {
            view = convertView;
        }
        return view;
    }
}
