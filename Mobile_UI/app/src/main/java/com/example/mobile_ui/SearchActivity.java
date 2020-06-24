package com.example.mobile_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.mobile_ui.Model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    SearchView searchView;
    ListView lvsearch;
    List<String> dataSearch = new ArrayList<>();
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        lvsearch = findViewById(R.id.lvSearch);

//        dataSearch = new ArrayList<>();
//        dataSearch.add("quần áo");
//        dataSearch.add("giày dép");
//        dataSearch.add("mũ");
//        dataSearch.add("đồ ăn");
//        dataSearch.add("đồ uống");

//        final ArrayAdapter adapter = new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_1, dataSearch);
//        lvsearch.setAdapter(adapter);

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
//                adapter.getFilter().filter(newText);
                filterFromKeyword(newText);
                return false;
            }
        });
    }

    private void filterFromKeyword(final String newText) {
        // call api filter
        RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);
        JSONObject params = new JSONObject();

        try {
            params.put("q", newText);
            params.put("_page", 0);
            params.put("_limit", 5);
//            params.put("phone_number", "0854230458");
//            params.put("email", "doanhc@gmail.com");
//            params.put("address", "Ha Noi");
//            params.put("gender", 1);
        } catch (JSONException e) {
            System.out.println("OK");
        }
        final String url = "http://112.137.129.216:5001/api/product/categories/choosable";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, params,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(SearchActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        dataSearch = new ArrayList<>();
                        try {
                            JSONArray categories = response.getJSONObject("data").getJSONObject("result").getJSONArray("categories");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject category = categories.getJSONObject(i);
                                dataSearch.add((String) category.get("name"));
                            }
                            //Toast.makeText(SearchActivity.this, dataSearch.toString(), Toast.LENGTH_LONG).show();
                            adapter = new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_1, dataSearch);
                            lvsearch.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, "error", Toast.LENGTH_LONG).show();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        System.out.println(res);
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        Volley.newRequestQueue(SearchActivity.this).add(jsonObjReq);
    }
}
