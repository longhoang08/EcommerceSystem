package com.example.mobile_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.mobile_ui.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    SearchView searchView;
    ListView lvsearch;
    List<String> dataSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        lvsearch = findViewById(R.id.lvSearch);
        dataSearch = new ArrayList<>();
        dataSearch.add("quần áo");
        dataSearch.add("giày dép");
        dataSearch.add("mũ");
        dataSearch.add("đồ ăn");
        dataSearch.add("đồ uống");

        final ArrayAdapter adapter = new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_1, dataSearch);
        lvsearch.setAdapter(adapter);

        //bắt sự kiện trên khi click các phần tử của listView
        lvsearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //trả về vị trí khi click trên listView ->0
                Toast.makeText(SearchActivity.this, "" + dataSearch.get(position), Toast.LENGTH_SHORT).show();
                // chuyển sang màn hình các sản phẩm phù hợp với search
                startActivity(new Intent(SearchActivity.this, ProductSearchActivity.class));
            }
        });

        //sử lý search view
        searchView = findViewById(R.id.searchView);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                finish();
                return false;
            }
        });
    }
}
