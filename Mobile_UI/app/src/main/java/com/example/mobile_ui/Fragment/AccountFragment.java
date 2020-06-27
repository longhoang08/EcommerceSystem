package com.example.mobile_ui.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

//import com.example.mobile_ui.Adapter.CartProductShopAdapter;
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
import com.example.mobile_ui.LoginActivity;
//import com.example.mobile_ui.Model.CartShop;
import com.example.mobile_ui.MainActivity;
import com.example.mobile_ui.MyBuyrecordActivity;
import com.example.mobile_ui.R;
import com.example.mobile_ui.ResetPassActivity;
import com.example.mobile_ui.SettingAccountActivity;
import com.example.mobile_ui.SignUpActivity;
import com.example.mobile_ui.StallActivity;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountFragment extends Fragment {

    Button buttonSignUp, buttonLogin;
    ListView listViewDetailAcc;
    int REQUEST_CODE_LOGIN = 13;

    TextView textViewNameUser;
    ImageView user_infor_img_main;
    Button buttonSeller;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        user_infor_img_main = root.findViewById(R.id.user_infor_img_main);
        buttonLogin = root.findViewById(R.id.buttonLogin);
        buttonSignUp = root.findViewById(R.id.buttonSignUp);
        buttonSeller = root.findViewById(R.id.buttonSeller);
        // tên username, password
        textViewNameUser = root.findViewById(R.id.textViewNameUser);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), LoginActivity.class), REQUEST_CODE_LOGIN);
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        listViewDetailAcc = root.findViewById(R.id.listDetailAccount);
        final List<String> abc = new ArrayList<>();
        abc.add("Xem Gian Hàng");
        abc.add("Đơn hàng");
        abc.add("Thiết lập tài khoản");
        abc.add("Đổi mật khẩu");
        abc.add("Đăng xuất");
        ArrayAdapter adapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,abc);
        listViewDetailAcc.setAdapter(adapter);
        listViewDetailAcc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (abc.get(position)){
                    case "Xem Gian Hàng":
                        intent = new Intent(getContext(), StallActivity.class);
                        startActivity(intent);break;
                    case "Thiết lập tài khoản":
                        intent = new Intent(getContext(), SettingAccountActivity.class);
                        startActivity(intent);break;
                    case "Đơn hàng":
                        intent = new Intent(getContext(), MyBuyrecordActivity.class);
                        startActivity(intent);break;
                    case "Đổi mật khẩu":
                        intent = new Intent(getContext(), ResetPassActivity.class);
                        startActivity(intent);break;
                    case "Đăng xuất":
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("VALUABLE_APP", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("LOGIN_TOKEN");
                        editor.commit();
                        MainActivity.STATUS_LOGIN=false;
                        System.out.println("LOGIN_TOKEN deleted");
                        setFormAccordingStatusLogin();
                }
            }
        });
        setFormAccordingStatusLogin();
        return root;
    }

    //lắng nghe sự kiện khi login trả về thành công
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data); comment this unless you want to pass your result to the activity.
        // Kiểm tra requestCode có trùng với REQUEST_CODE vừa dùng
        if(requestCode == REQUEST_CODE_LOGIN) {
            // resultCode được set bởi DetailActivity
            // RESULT_OK chỉ ra rằng kết quả này đã thành công
            if(resultCode == Activity.RESULT_OK) {
                // Nhận dữ liệu từ Intent trả về
                //final String result = data.getStringExtra("status");
                setFormAccordingStatusLogin();
                //if(result=="login success")  Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            } else {
                System.out.println("error in recerved anount success");
            }
        }
    }

    //set lại form khi bắt đầu activity hoặc khi status_login thay đổi
    private void setFormAccordingStatusLogin(){
        if(MainActivity.STATUS_LOGIN==true) {
            getUser();
        }else{
            textViewNameUser.setVisibility(View.GONE);
            listViewDetailAcc.setVisibility(View.GONE);
            buttonSeller.setVisibility(View.GONE);
            buttonLogin.setVisibility(View.VISIBLE);
            buttonSignUp.setVisibility(View.VISIBLE);
            user_infor_img_main.setImageResource(R.drawable.ic_person_black_24dp);
        }
    }

    private void getUser() {
        // call api get status
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JSONObject params = new JSONObject();

        final String url = "http://112.137.129.216:5001/api/users/get_status";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, url, params,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            JSONObject dataUser = response.getJSONObject("data");
                            textViewNameUser.setVisibility(View.VISIBLE);
                            textViewNameUser.setText((String) dataUser.get("fullname"));
                            String urlAvatar = (String) dataUser.get("avatar_url");
                            Glide.with(getActivity())
                                    .load(urlAvatar).override(50, 50).centerCrop()
                                    .into(user_infor_img_main);
                            listViewDetailAcc.setVisibility(View.VISIBLE);
                            buttonLogin.setVisibility(View.GONE);
                            buttonSignUp.setVisibility(View.GONE);
                            buttonSeller.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
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
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("VALUABLE_APP", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("LOGIN_TOKEN");
                editor.commit();
                MainActivity.STATUS_LOGIN=false;
                setFormAccordingStatusLogin();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("VALUABLE_APP", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("LOGIN_TOKEN","");
//                System.out.println(token);
                headers.put("x-access-token", token);
                return headers;
            }
        };
        Volley.newRequestQueue(getActivity()).add(jsonObjReq);
    }
}
