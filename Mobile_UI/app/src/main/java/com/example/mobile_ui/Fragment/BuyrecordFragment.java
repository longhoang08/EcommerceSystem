package com.example.mobile_ui.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mobile_ui.Adapter.ProductSpecificAdapter;
import com.example.mobile_ui.Model.ProductSpecific;
import com.example.mobile_ui.R;
import com.example.mobile_ui.View.ExpandHeightGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyrecordFragment extends Fragment {

    private String nameOfItemtab;

    public BuyrecordFragment() {
        // Required empty public constructor
    }

    public BuyrecordFragment(String item) {
        this.nameOfItemtab=item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_buy_record, container, false);

        TextView typeBuyrecord = root.findViewById(R.id.typeBuyrecord);
        typeBuyrecord.setText(nameOfItemtab);
        return root;
    }
}
