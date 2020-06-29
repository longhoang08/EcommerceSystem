package com.example.mobile_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_ui.Adapter.CategoryProductListAdapter;
import com.example.mobile_ui.Adapter.ProductAdapter;
import com.example.mobile_ui.Model.Category;
import com.example.mobile_ui.Model.Product;
import com.example.mobile_ui.View.ExpandHeightGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ListCategoryActivity extends AppCompatActivity {

    private ExpandHeightGridView expandHeightGridViewCategoryProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);

        expandHeightGridViewCategoryProduct = findViewById(R.id.expandHeightGridViewCategoryProduct);
        final List<Category> listCategoryProduct = new ArrayList<>();
        loadDataCategoryProduct(listCategoryProduct);
        CategoryProductListAdapter categoryProductListAdapter = new CategoryProductListAdapter(listCategoryProduct);
        expandHeightGridViewCategoryProduct.setAdapter(categoryProductListAdapter);
        expandHeightGridViewCategoryProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListCategoryActivity.this, ProductSearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("keyWord", listCategoryProduct.get(position).getName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void loadDataCategoryProduct(final List<Category> listCategoryProduct) {
        listCategoryProduct.add(new Category("https://img.vnshop.vn/height/128/media/menu-icons/001/001-0.png", "Mẹ và bé"));
        listCategoryProduct.add(new Category("https://img.vnshop.vn/height/128/media/menu-icons/002/002-0.png", "Đồ chơi"));
        listCategoryProduct.add(new Category("https://img.vnshop.vn/height/128/media/menu-icons/011,012,013/011,012,013-0.png", "Sách"));
        listCategoryProduct.add(new Category("https://img.vnshop.vn/height/128/media/menu-icons/007/007-0.png", "Thời trang"));
        listCategoryProduct.add(new Category("https://img.vnshop.vn/height/128/media/menu-icons/003/003-0.png", "Mỹ phẩm và làm đẹp"));
        listCategoryProduct.add(new Category("https://img.vnshop.vn/height/128/media/menu-icons/004/004-0.png", "Chăm sóc sức khỏe"));
        listCategoryProduct.add(new Category("https://img.vnshop.vn/height/128/media/menu-icons/014/014-0.png", "Laptop"));
        listCategoryProduct.add(new Category("https://img.vnshop.vn/height/128/media/menu-icons/015/015-0.png", "Điện thoại"));
        listCategoryProduct.add(new Category("https://img.vnshop.vn/height/128/media/menu-icons/016/016-0.png", "Loa âm thanh"));
        listCategoryProduct.add(new Category("https://img.vnshop.vn/height/128/media/menu-icons/008/008-0.png", "Đồ điện"));
        listCategoryProduct.add(new Category("https://img.vnshop.vn/height/128/media/menu-icons/009/009-0.png", "Thực phẩm"));
        listCategoryProduct.add(new Category("https://img.vnshop.vn/height/128/media/menu-icons/017/017-0.png", "Máy ảnh"));

        /*JSONObject params = new JSONObject();

        try {
//            JSONArray jsoBrand = new JSONArray(), jsCategory = new JSONArray();
//            jsoBrand.put(codeBrand);
//            jsCategory.put(codeCategory);
            params.put("q", listCategoryProduct);
//            System.out.println(keyWord);
//            params.put("_limit", 5);
        } catch (JSONException e) {
            System.out.println("OK");
        }

        String url = "http://112.137.129.216:5001/api/product/categories/choosable";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, params,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            JSONArray data = response.getJSONObject("data").getJSONArray("categories");
                            System.out.println(response.toString());
                            //Toast.makeText(ProductSearchActivity.this, data.toString(), Toast.LENGTH_LONG).show();
//                            if (data.length() == 0) {
//                                loadMore.setVisibility(View.INVISIBLE);
//                                noMorePro.setVisibility(View.VISIBLE);
//                                return;
//                            }
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject category = data.getJSONObject(i);

//                                String urlImage = null;
//                                if (!product.isNull("images")) {
                                int categoryImage = (int) category.get("url");
                                String nameCategory = (String) category.get("name");

                                System.out.println(categoryImage + " " + nameCategory);

                                listCategoryProduct.add(new Category(
                                        categoryImage, nameCategory));
//                                System.out.println(codeBrand + "Image " + urlImage + " Price " + price);
                                //Toast.makeText(getActivity(), listProduct.size() +"", Toast.LENGTH_LONG).show();
                            }
                            CategoryProductListAdapter categoryProductListAdapter  = new CategoryProductListAdapter(listCategoryProduct);
                            expandHeightGridViewCategoryProduct.setAdapter(categoryProductListAdapter);
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
        Volley.newRequestQueue(ListCategoryActivity.this).add(jsonObjReq);

         */

    }
}
