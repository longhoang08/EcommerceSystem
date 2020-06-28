package com.example.mobile_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.mobile_ui.Adapter.ProductAdapter;
import com.example.mobile_ui.Model.Product;
import com.example.mobile_ui.Model.chooseImage;
import com.example.mobile_ui.Model.filterProduct;
import com.example.mobile_ui.View.ExpandHeightGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductSearchActivity extends AppCompatActivity {

    ExpandHeightGridView expandHeightGridViewProduct;
    TextView textViewSearch, loadMore, noMorePro;
    ImageView imageButtonFilter;
    int page = 0;
    String keyWord = "";
    List<Product> listProduct = new ArrayList<>();
    ProductAdapter productAdapter = new ProductAdapter(listProduct);
    ArrayList<String> nameBrand = new ArrayList<>();
    ArrayList<String> codeBrand = new ArrayList<>();
    ArrayList<String> nameCategory = new ArrayList<>();
    ArrayList<String> codeCategory = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);

        RequestQueue queue = Volley.newRequestQueue(ProductSearchActivity.this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        keyWord = bundle.getString("keyWord");
        getCategoryAndBrand();
        //System.out.println(keyWord);
        noMorePro = findViewById(R.id.noMorePro);
        loadMore = findViewById(R.id.loadMore);
        loadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page ++;
                getProduct();
            }
        });
        imageButtonFilter = findViewById(R.id.imageButtonFilter);

        textViewSearch = findViewById(R.id.textViewSearch);
        textViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        expandHeightGridViewProduct = findViewById(R.id.expandHeightGridViewProduct);

//        loadDataProduct(listProduct);

        expandHeightGridViewProduct.setAdapter(productAdapter);
        expandHeightGridViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProductSearchActivity.this, DetailProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("idPro", listProduct.get(position).getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        getProduct();
    }

    private void getCategoryAndBrand() {
        JSONObject params = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put("brand");
        jsonArray.put("category");
        try {
            params.put("q", keyWord);
            params.put("aggregations", jsonArray);
//            System.out.println(keyWord);
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
                            JSONObject data = response.getJSONObject("data").getJSONObject("aggregations");
                            JSONArray brand = data.getJSONArray("brand");
                            JSONArray category = data.getJSONArray("category");
                            final ArrayList<String> nameBrand = new ArrayList<>();
                            final ArrayList<String> codeBrand = new ArrayList<>();
                            final ArrayList<String> nameCategory = new ArrayList<>();
                            final ArrayList<String> codeCategory = new ArrayList<>();
                            for (int i = 0; i < brand.length(); i++) {
                                nameBrand.add((String) brand.getJSONObject(i).get("name"));
                                codeBrand.add((String) brand.getJSONObject(i).get("code"));
                            }
                            for (int i = 0; i < category.length(); i++) {
                                nameCategory.add((String) category.getJSONObject(i).get("name"));
                                codeCategory.add((String) category.getJSONObject(i).get("code"));
                            }
                            imageButtonFilter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final filterProduct filter = new filterProduct(nameBrand, codeBrand, nameCategory, codeCategory);
                                    filter.show(getSupportFragmentManager(), "");
                                }
                            });
//                            Toast.makeText(ProductSearchActivity.this, data.toString(), Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(ProductSearchActivity.this).add(jsonObjReq);
    }

    private void getProduct() {

        JSONObject params = new JSONObject();

        try {
            params.put("_page", page);
            params.put("q", keyWord);
            System.out.println(keyWord);
            params.put("_limit", 10);
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
                            if (data.length() == 0) {
                                loadMore.setVisibility(View.INVISIBLE);
                                noMorePro.setVisibility(View.VISIBLE);
                                return;
                            }
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject product = data.getJSONObject(i);
                                
                                String urlImage = null;
//                                if (!product.isNull("images")) {
                                    urlImage = (String) product.getJSONArray("images").getJSONObject(0).get("url");
//                                }
                                int price = ((Double) product.getJSONObject("prices").get("price")).intValue();
                                String name = (String) product.get("name");
                                String id = (String) product.get("sku");
//                                Toast.makeText(getActivity(), price+"", Toast.LENGTH_LONG).show();
                                int stock = (int) product.getJSONObject("stock").get("in_stock");
                                listProduct.add(new Product(urlImage, name, price, stock, id));
                                //Toast.makeText(getActivity(), listProduct.size()+"", Toast.LENGTH_LONG).show();
                            }
                            expandHeightGridViewProduct.setAdapter(productAdapter);
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
        Volley.newRequestQueue(ProductSearchActivity.this).add(jsonObjReq);
    }

//    private void loadDataProduct(List<Product> listProduct) {
//        String url = "https://img1.looper.com/img/gallery/we-now-know-the-one-time-batman-was-supposed-to-die/intro-1576009072.jpg";
//        listProduct.add(new Product(url, "Redmi Note 7", 45000, 45, "1"));
//        listProduct.add(new Product(url, "Redmi Note 7", 45000, 45, "1"));
//        listProduct.add(new Product(url, "Redmi Note 7", 45000, 45, "1"));
//    }

    public static void filterByField(String codeBrand, String codeCategory) {
        System.out.println(codeBrand+"|"+ codeCategory);
    }
}
