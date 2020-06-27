package com.example.mobile_ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mobile_ui.Adapter.ListOfBuyrecordAdapter;
import com.example.mobile_ui.Adapter.ProductSpecificAdapter;
import com.example.mobile_ui.Adapter.ProductStallAdapter;
import com.example.mobile_ui.DetailBuyrecordActivity;
import com.example.mobile_ui.Model.BuyRecord;
import com.example.mobile_ui.Model.ProductSpecific;
import com.example.mobile_ui.R;
import com.example.mobile_ui.StallActivity;
import com.example.mobile_ui.StallDetailProductActivity;
import com.example.mobile_ui.View.ExpandHeightGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyrecordFragment extends Fragment {

    ListView listView;
    private ArrayList<BuyRecord> data;
    public BuyrecordFragment() {
        // Required empty public constructor
    }

    public BuyrecordFragment(ArrayList<BuyRecord> data) {
        this.data=data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_buy_record, container, false);

        //anh xa
        listView = root.findViewById(R.id.listOfBuyrecord);
        //click to view one product
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetailBuyrecordActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idOfMybyrecord",data.get(position).getId());
                intent.putExtras(bundle);
                startActivity(intent);
//                startActivity(intent);
            }
        });

        ListOfBuyrecordAdapter adapter = new ListOfBuyrecordAdapter(data);
        listView.setAdapter(adapter);

        return root;
    }
}
