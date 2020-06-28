package com.example.mobile_ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
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
import com.example.mobile_ui.Adapter.DetailProductDescriptionAdapter;
import com.example.mobile_ui.Adapter.ProductImagesAdapter;
import com.example.mobile_ui.Adapter.ReviewProductAdapter;
import com.example.mobile_ui.Model.Product;
import com.example.mobile_ui.Model.Review;
import com.example.mobile_ui.View.ExpandHeightGridView;
import com.example.mobile_ui.View.ExpandHeightViewPager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailProductActivity extends AppCompatActivity {

    public static String description;
    // viewpager la slide, tablayout cac dau cham ben duoi slide
    private ViewPager viewPagerImagesProduct;
    private TabLayout tabLayoutImagesProduct;
    // nut yeu thich san pham
//    private FloatingActionButton floatingactionbuttonFavorite;
//    boolean favouriteProduct = false;

    private ExpandHeightViewPager viewPagerProductDescription;
    private TabLayout tabLayoutProductDescription;

    private ExpandHeightGridView expandHeightGridViewReviewProduct;
    private Button buttonViewAllReviewProduct;
    // mua hang
    private TextView textViewAddToCart, textViewBuyNow, textViewNoticeAddProduct, textViewProductName, textViewPriceProduct,
            textViewStock, nameOfShop;
    List<String> imagesProduct = new ArrayList<>();
    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(imagesProduct);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        // load slide anh
        viewPagerImagesProduct = findViewById(R.id.viewPagerImagesProduct);
        tabLayoutImagesProduct = findViewById(R.id.tabLayoutImagesProduct);
        // mang anh
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String idPro = bundle.getString("idPro");
//        System.out.println(idPro);

        textViewProductName = findViewById(R.id.textViewProductName);
        textViewPriceProduct = findViewById(R.id.textViewPriceProduct);
        textViewStock = findViewById(R.id.textViewStock);
        nameOfShop = findViewById(R.id.nameOfShop);
//        loadDataImagesProduct(imagesProduct);
        viewPagerImagesProduct.setAdapter(productImagesAdapter);
        tabLayoutImagesProduct.setupWithViewPager(viewPagerImagesProduct, true);
        // end load slide anh
        /*// yeu thich san pham
        floatingactionbuttonFavorite = findViewById(R.id.floatingactionbuttonFavorite);
        floatingactionbuttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favouriteProduct){
                    favouriteProduct = false;
                    floatingactionbuttonFavorite.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                } else {
                    favouriteProduct = true;
                    floatingactionbuttonFavorite.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FFCC0000")));
                }
            }
        });*/
        // mieu ta ro san pham = 2 phan mo ta, chi tiet
        viewPagerProductDescription = findViewById(R.id.viewPagerProductDescription);//View hiện mỗi tab
        tabLayoutProductDescription = findViewById(R.id.tabLayoutProductDescription);// gồm thanh chọn

        // load review
        expandHeightGridViewReviewProduct = findViewById(R.id.expandHeightGridViewReviewProduct);
        List<Review> listReviewProduct = new ArrayList<>();
        loadDataReviewProduct(listReviewProduct);
        ReviewProductAdapter reviewProductAdapter = new ReviewProductAdapter(listReviewProduct);
        expandHeightGridViewReviewProduct.setAdapter(reviewProductAdapter);
        // end load review
        // chuyển sang trang load tất cả bình luận
        buttonViewAllReviewProduct = findViewById(R.id.buttonViewAllReviewProduct);
        buttonViewAllReviewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailProductActivity.this, ListReviewProductActivity.class);
                startActivity(intent);
            }
        });
        // su kien mua hang
        textViewAddToCart = findViewById(R.id.textViewAddToCart);
        textViewBuyNow = findViewById(R.id.textViewBuyNow);
        textViewNoticeAddProduct = findViewById(R.id.textViewNoticeAddProduct);
        textViewAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewNoticeAddProduct.setVisibility(View.VISIBLE);
                new CountDownTimer(500, 500) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        textViewNoticeAddProduct.setVisibility(View.INVISIBLE);
                    }
                }.start();
            }
        });
        textViewBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        getProduct(idPro);
    }

    private void getProduct(String idPro) {
        RequestQueue queue = Volley.newRequestQueue(DetailProductActivity.this);
        JSONObject params = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(idPro);
        try {
            params.put("skus", jsonArray);
        } catch (JSONException e) {
//            System.out.println("OK");
        }
        final String url = "http://112.137.129.216:5001/api/product/search";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, params,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(DetailProductActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            JSONArray data = response.getJSONObject("data").getJSONArray("products");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject product = data.getJSONObject(i);
                                JSONArray urlImages = product.getJSONArray("images");
                                for (int j = 0; j < urlImages.length(); j++) {
                                    imagesProduct.add((String) urlImages.getJSONObject(j).get("url"));
                                }
                                productImagesAdapter.notifyDataSetChanged();
                                int price = ((Double) product.getJSONObject("prices").get("price")).intValue();
                                textViewPriceProduct.setText(price + " VND");
                                String name = (String) product.get("name");
                                textViewProductName.setText(name);
                                int stock = (int) product.getJSONObject("stock").get("in_stock");
                                textViewStock.setText("Kho "+stock);
                                String nameShop = (String) product.getJSONObject("seller").get("name");
                                nameOfShop.setText(nameShop);
                                String id = (String) product.get("sku");
                                description = (String) product.get("description");
//                                System.out.println(description);
                                description = description.replaceAll("(<(/)img>)|(<img.+?>)", "");
//                                System.out.println(description);
                                // set event
                                viewPagerProductDescription.setAdapter(new DetailProductDescriptionAdapter(getSupportFragmentManager(), tabLayoutProductDescription.getTabCount()));
                                viewPagerProductDescription.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutProductDescription));
                                tabLayoutProductDescription.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                    @Override
                                    public void onTabSelected(TabLayout.Tab tab) {
                                        viewPagerProductDescription.setCurrentItem(tab.getPosition());
                                    }

                                    @Override
                                    public void onTabUnselected(TabLayout.Tab tab) {

                                    }

                                    @Override
                                    public void onTabReselected(TabLayout.Tab tab) {

                                    }
                                });
//                                descriptionPro.setText(Html.fromHtml(description));
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
        Volley.newRequestQueue(DetailProductActivity.this).add(jsonObjReq);
    }

    private void loadDataReviewProduct(List<Review> listReviewProduct) {
        listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 4, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
        listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 4, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
        listReviewProduct.add(new Review(R.drawable.icon_kiwi_fruit, "Nguyen Thanh An", 4, "Rất tốt, rất tốt, rất tốt", "05-05-2020 20:20"));
    }

    /*private void loadDataImagesProduct(List<String> imagesProduct) {
        imagesProduct.add(R.drawable.icon_pineapple);
        imagesProduct.add(R.drawable.icon_kiwi_fruit);
        imagesProduct.add(R.drawable.icon_dragon_fruit);
        imagesProduct.add(R.drawable.icon_pineapple);
        imagesProduct.add(R.drawable.icon_dragon_fruit);
    }*/
}
