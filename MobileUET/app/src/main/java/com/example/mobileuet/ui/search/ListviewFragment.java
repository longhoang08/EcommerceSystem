package com.example.mobileuet.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mobileuet.MainActivity;
import com.example.mobileuet.R;
import com.example.mobileuet.ui.notifications.NotificationsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListviewFragment extends Fragment {

    private ListviewModel listviewModel;
    public static ArrayAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listviewModel =
                ViewModelProviders.of(this).get(ListviewModel.class);
        View root = inflater.inflate(R.layout.fragment_listview_search, container, false);
        final ListView lvsearch = root.findViewById(R.id.lvSearch);
        List<String> dataSearch;
        dataSearch = new ArrayList<>();
        dataSearch.add("chim");
        dataSearch.add("cho");
        dataSearch.add("meo");
        dataSearch.add("ga");
        dataSearch.add("lon");
        adapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,dataSearch);
        lvsearch.setAdapter(adapter);
        return root;
    }
}
