package com.example.mobile_ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.mobile_ui.Adapter.ListOfMyBuyrecordAdapter;
import com.example.mobile_ui.DetailMybuyrecordActivity;
import com.example.mobile_ui.Model.MyBuyRecord;
import com.example.mobile_ui.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyBuyrecordFragment extends Fragment {

    ListView listView;
    private ArrayList<MyBuyRecord> data;
    public MyBuyrecordFragment() {
        // Required empty public constructor
    }

    public MyBuyrecordFragment(ArrayList<MyBuyRecord> data) {
        this.data=data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_buy_record, container, false);
        //fragment_buy_record có thể dùng chung với đơn mua của shop mình bán

        //anh xa
        listView = root.findViewById(R.id.listOfBuyrecord);

        ListOfMyBuyrecordAdapter adapter = new ListOfMyBuyrecordAdapter(data);
        listView.setAdapter(adapter);
//        System.out.println("Frame này");
        //set sự kiện click cho mỗi element in listView
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                System.out.println("Yes");
//                Intent intent = new Intent(getActivity(), DetailMybuyrecordActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("idOfMybyrecord",data.get(position).getId());
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
//        System.out.println(data.size());
        return root;
    }
}
