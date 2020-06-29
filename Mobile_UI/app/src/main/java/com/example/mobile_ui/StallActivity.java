package com.example.mobile_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import com.example.mobile_ui.Adapter.ProductStallAdapter;
import com.example.mobile_ui.Model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StallActivity extends AppCompatActivity {
    TextView btnAddProduct;
    TextView showBuyRecord;
    ListView stallProListView;
    int idSeller = 11;
//    ImageView stall_infor_img_main;
//    TextView userName;
//    TextView numOfPro;

    ArrayList<Product> products = new ArrayList<Product>();
    ProductStallAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stall);
        //anh xa
        btnAddProduct = findViewById(R.id.btn_add_product);
        showBuyRecord = findViewById(R.id.stallBuyRecord);
        stallProListView = findViewById(R.id.stallProListView);
//        stall_infor_img_main = findViewById(R.id.stall_infor_img_main);
//        userName = findViewById(R.id.userName);
//        numOfPro = findViewById(R.id.numOfPro);

        //fake data
        RequestQueue queue = Volley.newRequestQueue(StallActivity.this);
//        String url = "https://image.thanhnien.vn/800/uploaded/tuyenth/2019_07_07/47585172_614712392300363_2894160633832439484_n_jxdz.jpg";
//        products.add(new Product(url,"banana",12000,120, "1"));
//        products.add(new Product(url,"apple",12000,100, "2"));


        //sang màn hình thêm 1 sp vào stall
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StallActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });

        //sang màn xem những đơn hàng
        showBuyRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StallActivity.this, BuyRecordActivity.class);
                startActivity(intent);
            }
        });

        adapter = new ProductStallAdapter(
                StallActivity.this,R.layout.stall_product_item,products
        );
        idSeller = getIdSeller();
        getProduct();
    }

    private void getProduct() {
        JSONObject params = new JSONObject();

        try {
            params.put("seller_id", idSeller);

        } catch (JSONException e) {
//            System.out.println("OK");
        }
        String url = "http://112.137.129.216:5001/api/product/search";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, params,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            JSONArray data = response.getJSONObject("data").getJSONArray("products");
                            //Toast.makeText(ProductSearchActivity.this, data.toString(), Toast.LENGTH_LONG).show();
//                            if (data.length() == 0) {
//                                loadMore.setVisibility(View.INVISIBLE);
//                                noMorePro.setVisibility(View.VISIBLE);
//                                return;
//                            }
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject product = data.getJSONObject(i);

                                String urlImage = null;
                                if (product.has("images")) {
                                    if (!product.isNull("images"))
                                        urlImage = (String) product.getJSONArray("images").getJSONObject(0).get("url");
                                }
                                int price = ((Double) product.getJSONObject("prices").get("price")).intValue();
                                String name = (String) product.get("name");
                                String id = (String) product.get("sku");
//                                Toast.makeText(getActivity(), price+"", Toast.LENGTH_LONG).show();
                                int stock = (int) product.getJSONObject("stock").get("in_stock");
                                products.add(new Product(urlImage, name, price, stock, id));
                                //Toast.makeText(getActivity(), listProduct.size()+"", Toast.LENGTH_LONG).show();
                                stallProListView.setAdapter(adapter);
                                //click to view one product
                                stallProListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent(StallActivity.this,StallDetailProductActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        System.out.println(res);
//                        Toast.makeText(getActivity(), res, Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(StallActivity.this).add(jsonObjReq);
    }

    private int getIdSeller() {
        return 11;
    }
}
