package com.example.mobile_ui.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    EditText desSel;
    ListView listViewDetailAcc;
    int REQUEST_CODE_LOGIN = 13;
    public static int idSeller;

    TextView textViewNameUser;
    ImageView user_infor_img_main;
    Button buttonSeller;
    ArrayList<String> abc = new ArrayList<>();
    ArrayAdapter adapter;
    ProgressDialog pd;


    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        pd = new ProgressDialog(getActivity());
        pd.setTitle("Chờ 1 lát ...");
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        adapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,abc);
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
//        abc.add("Xem Gian Hàng");

        listViewDetailAcc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (abc.get(position)){
                    case "Shop của tôi":
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
        buttonSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View view = inflater.inflate(R.layout.form_register_seller, null);
                builder.setView(view);
                desSel = view.findViewById(R.id.desSel);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        pd.show();
                        registerSeller();
                    }
                }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

//                Intent intent = new Intent(getContext(), StallActivity.class);
//                startActivity(intent);
            }
        });
        setFormAccordingStatusLogin();
        return root;
    }

    private void registerSeller() {

        JSONObject params = new JSONObject();
        try {
            params.put("description", desSel.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String url = "http://112.137.129.216:5001/api/seller/register";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, params,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                        //                            buttonSeller.setVisibility(View.VISIBLE);
                        pd.cancel();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Đăng ký thành công thành người bán.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                abc = new ArrayList<>();
                                abc.add("Shop của tôi");
                                abc.add("Thiết lập tài khoản");
                                abc.add("Đổi mật khẩu");
                                abc.add("Đăng xuất");
                                buttonSeller.setVisibility(View.GONE);
                                adapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,abc);
                                listViewDetailAcc.setAdapter(adapter);
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                NetworkResponse response = error.networkResponse;
                if (response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
//                        System.out.println(res);
                        JSONObject responseMsg = new JSONObject(res);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Thông báo");
                        String msgError = (String) responseMsg.get("message");
                        msgError = msgError.substring(msgError.indexOf(":")+2);
                        builder.setMessage(msgError);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    } catch (UnsupportedEncodingException | JSONException e1) {
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
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("VALUABLE_APP", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("LOGIN_TOKEN","");
//                System.out.println(token);
                headers.put("x-access-token", token);
                return headers;
            }
        };
        Volley.newRequestQueue(getActivity()).add(jsonObjReq);
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
                            String role = (String) dataUser.get("role");
                            abc = new ArrayList<>();
                            if (role.equals("seller")) {
                                buttonSeller.setVisibility(View.GONE);
                                abc.add("Thiết lập tài khoản");
                                abc.add("Đổi mật khẩu");
                                abc.add("Đăng xuất");
                                abc.add("Shop của tôi");
                            } else if (role.equals("customer")) {
                                buttonSeller.setVisibility(View.VISIBLE);
                                abc.add("Đơn hàng");
                                abc.add("Thiết lập tài khoản");
                                abc.add("Đổi mật khẩu");
                                abc.add("Đăng xuất");
                            } else {
                                buttonSeller.setVisibility(View.GONE);
                            }
                            adapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,abc);
                            listViewDetailAcc.setAdapter(adapter);

                            String urlAvatar = (String) dataUser.get("avatar_url");
                            idSeller = (int) dataUser.get("id");
                            Glide.with(getActivity())
                                    .load(urlAvatar).override(50, 50).centerCrop()
                                    .into(user_infor_img_main);
                            listViewDetailAcc.setVisibility(View.VISIBLE);
                            buttonLogin.setVisibility(View.GONE);
                            buttonSignUp.setVisibility(View.GONE);
//                            buttonSeller.setVisibility(View.VISIBLE);
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
