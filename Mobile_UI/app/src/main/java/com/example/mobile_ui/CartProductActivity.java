package com.example.mobile_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.mobile_ui.Adapter.CartProductAdapter;
import com.example.mobile_ui.Model.CartProduct;
import com.example.mobile_ui.Model.OrderProduct;
import com.example.mobile_ui.Model.Payment;
import com.example.mobile_ui.View.ExpandHeightGridView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartProductActivity extends AppCompatActivity {

    ExpandHeightGridView expandHeightGridViewProductCart;
    CheckBox checkBoxAllProductCart;
    TextView textViewBuy;
    final List<OrderProduct> listCartProduct = new ArrayList<>();
    CartProductAdapter cartProductAdapter = new CartProductAdapter(listCartProduct);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_product);
//MainActivity.cartProduct.print();
        expandHeightGridViewProductCart = findViewById(R.id.expandHeightGridViewProductCart);

        loadDataCartShop();

        expandHeightGridViewProductCart.setAdapter(cartProductAdapter);
        // khách hàng chọn tất cả sản phẩm để mua
        checkBoxAllProductCart = findViewById(R.id.checkBoxAllProductCart);
        checkBoxAllProductCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < listCartProduct.size(); i++) {
                    CheckBox checkBoxShopProduct = getViewByPosition(expandHeightGridViewProductCart, i).findViewById(R.id.checkBoxProduct);
                    if (checkBoxShopProduct.isChecked() != checkBoxAllProductCart.isChecked()) {
                        checkBoxShopProduct.performClick();
                    }
                }
            }
        });
        textViewBuy = findViewById(R.id.textViewBuy);
        textViewBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartProductActivity.this, PaymentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("listPro", new Gson().toJson(listCartProduct, listCartProduct.getClass()));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    private View getViewByPosition(ExpandHeightGridView expandHeightGridView, int position) {
        int firstItemPosition = expandHeightGridView.getFirstVisiblePosition();
        int lastItemPosition = firstItemPosition+expandHeightGridView.getChildCount()-1;
        if (position < firstItemPosition || position > lastItemPosition) {
            return expandHeightGridView.getAdapter().getView(position, null, expandHeightGridView);
        } else {
            int childIndex = position-firstItemPosition;
            return expandHeightGridView.getChildAt(childIndex);
        }
    }


    private void loadDataCartShop() {
        RequestQueue queue = Volley.newRequestQueue(CartProductActivity.this);
        JSONObject params = new JSONObject();
        JSONArray jsonArray = MainActivity.cartProduct.getIdPro();
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
//                            Toast.makeText(CartProductActivity.this, data.toString(), Toast.LENGTH_LONG).show();
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject product = data.getJSONObject(i);
                                String urlImage = (String) product.getJSONArray("images").getJSONObject(0).get("url");
                                int price = ((Double) product.getJSONObject("prices").get("price")).intValue();
                                String name = (String) product.get("name");
                                int stock = (int) product.getJSONObject("stock").get("in_stock");
                                String nameShop = (String) product.getJSONObject("seller").get("name");
                                String id = (String) product.get("sku");
//                                System.out.println(id);
//                                int quantity = MainActivity.cartProduct.getBuyNumById(id);
                                listCartProduct.add(new OrderProduct(nameShop, urlImage, name, price, 1, stock, id));
                                cartProductAdapter.notifyDataSetChanged();
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
        Volley.newRequestQueue(CartProductActivity.this).add(jsonObjReq);
//        listCartProduct.add(new OrderProduct(R.drawable.icon_kiwi_fruit, "Redmi Note 7", 40000, 2, 12));
//        listCartProduct.add(new OrderProduct(R.drawable.icon_pineapple, "Redmi Note 7", 40000, 2, 12));
//        listCartProduct.add(new OrderProduct(R.drawable.icon_dragon_fruit, "Redmi Note 7", 40000, 2, 18));
//        listCartProduct.add(new OrderProduct(R.drawable.icon_pineapple, "Redmi Note 7", 40000, 1, 20));
//        listCartProduct.add(new OrderProduct(R.drawable.ic_home_black_24dp, "Redmi Note 7", 40000, 2, 12));
//        listCartProduct.add(new OrderProduct(R.drawable.icon_pineapple, "Redmi Note 7", 40000, 2, 12));
//        listCartProduct.add(new OrderProduct(R.drawable.icon_dragon_fruit, "Redmi Note 7", 40000, 2, 18));
//        listCartProduct.add(new OrderProduct(R.drawable.gradient_background, "Redmi Note 7", 40000, 1, 20));
    }
}
