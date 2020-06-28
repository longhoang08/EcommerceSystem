package com.example.mobile_ui.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_ui.DetailProductActivity;
import com.example.mobile_ui.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailDescriptionFragment extends Fragment {

    public ProductDetailDescriptionFragment() {
        // Required empty public constructor
    }

    TextView descriptionPro;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_product_detail_description, container, false);

        descriptionPro = root.findViewById(R.id.descriptionPro);
        String description = DetailProductActivity.description;
        if (description != null) {
//            descriptionPro.setText(description);
            System.out.println(description);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                descriptionPro.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
            } else {
                descriptionPro.setText(Html.fromHtml(description));
            }
        }
        return root;
    }
}
