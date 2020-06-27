package com.example.mobile_ui.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.mobile_ui.Adapter.CategoryProductScrollAdapter;
import com.example.mobile_ui.Adapter.ProductAdapter;
import com.example.mobile_ui.CartProductActivity;
import com.example.mobile_ui.DetailProductActivity;
import com.example.mobile_ui.ListCategoryActivity;
import com.example.mobile_ui.Model.Category;
import com.example.mobile_ui.Model.Product;
import com.example.mobile_ui.R;
import com.example.mobile_ui.SearchActivity;
import com.example.mobile_ui.SignUpActivity;
import com.example.mobile_ui.View.ExpandHeightGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    // gio hang
    private ImageButton imageButtonCart;
    //search
    TextView searchView;

    ProductAdapter productAdapter;
    // danh sach the loai
    private RecyclerView recyclerViewCategoryProduct;
    private Button buttonViewAllCategoryProduct;
    // danh sach san pham
    private ExpandHeightGridView expandHeightGridViewProduct;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // su kien an vao gio hang
        imageButtonCart = root.findViewById(R.id.imageButtonCart);
        imageButtonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CartProductActivity.class);
                startActivity(intent);
            }
        });
        //su kien search
        searchView = (TextView) root.findViewById(R.id.searchView);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity().getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }

        });

        // load danh muc san pham
        recyclerViewCategoryProduct = root.findViewById(R.id.recyclerviewCategoryProduct);
        List<Category> listCategoryProduct = new ArrayList<>();
        loadDataCategoryProduct(listCategoryProduct);
        // thiet ke 2 hang truot theo chieu ngang
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryProduct.setLayoutManager(gridLayoutManager);
        CategoryProductScrollAdapter categoryProductAdapter = new CategoryProductScrollAdapter(listCategoryProduct);
        recyclerViewCategoryProduct.setAdapter(categoryProductAdapter);
        // end load danh muc san pham
        // load san pham
        expandHeightGridViewProduct = root.findViewById(R.id.expandHeightGridViewProduct);
        List<Product> listProduct = new ArrayList<>();
        loadDataProduct(listProduct);
        productAdapter = new ProductAdapter(listProduct);
        expandHeightGridViewProduct.setAdapter(productAdapter);
        //end load san pham
        //su kien
        // man hinh tat ca the loai
        buttonViewAllCategoryProduct = root.findViewById(R.id.buttonViewAllCategoryProduct);
        buttonViewAllCategoryProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListCategoryActivity.class);
                startActivity(intent);
            }
        });
        // chon 1 san pham -> trang chi tiet
        expandHeightGridViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetailProductActivity.class);
//                intent.putExtra("id", lí)
                startActivity(intent);
            }
        });
        return root;
    }

    private void loadDataProduct(List<Product> listProduct) {
//        getProduct(listProduct);
        String url = "https://upload.wikimedia.org/wikipedia/en/3/35/Supermanflying.png";
        listProduct.add(new Product(url, "Redmi Note 7", 45000, 4.5, "1"));
        listProduct.add(new Product(url, "Redmi Note 7", 45000, 3.5, "2"));
    }

    private void loadDataCategoryProduct(List<Category> listCategoryProduct) {
        listCategoryProduct.add(new Category(R.drawable.icon_dragon_fruit, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_kiwi_fruit, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_pineapple, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_dragon_fruit, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_kiwi_fruit, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_pineapple, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_dragon_fruit, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_kiwi_fruit, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_pineapple, "Rồng hoa quả"));
        listCategoryProduct.add(new Category(R.drawable.icon_pineapple, "Rồng hoa quả"));
    }

    private void getProduct(final List<Product> listProduct){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JSONObject params = new JSONObject();

        try {
            params.put("_page", 0);
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
                        //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).shgiow();
                        try {
                            JSONArray data = response.getJSONObject("data").getJSONArray("products");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject product = data.getJSONObject(i);
                                String urlImage = (String) product.getJSONArray("images").getJSONObject(0).get("url");
                                int price = ((Double) product.getJSONObject("price").get("price")).intValue();
                                String name = (String) product.get("name");
                                String id = (String) product.get("sku");
//                                Toast.makeText(getActivity(), price+"", Toast.LENGTH_LONG).show();
                                listProduct.add(new Product(urlImage, name, price, 4.5, id));
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
        Volley.newRequestQueue(getActivity()).add(jsonObjReq);
    }

}

