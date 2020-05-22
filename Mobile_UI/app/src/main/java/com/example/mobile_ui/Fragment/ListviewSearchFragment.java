package com.example.mobile_ui.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


import com.example.mobile_ui.R;

import java.util.ArrayList;
import java.util.List;

public class ListviewSearchFragment extends Fragment {

    public static ArrayAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listview_search, container, false);
        final ListView lvsearch = root.findViewById(R.id.lvSearch);
        List<String> dataSearch;
        dataSearch = new ArrayList<>();
        dataSearch.add("chim");
        dataSearch.add("chó");
        dataSearch.add("mèo");
        dataSearch.add("gà");
        dataSearch.add("lợn");
        adapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,dataSearch);
        lvsearch.setAdapter(adapter);
        return root;
    }
}
