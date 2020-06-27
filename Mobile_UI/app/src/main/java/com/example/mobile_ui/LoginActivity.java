package com.example.mobile_ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button buttonLogin;
    EditText username;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = findViewById(R.id.buttonLogin);
        username = findViewById(R.id.username);
        password  = findViewById(R.id.password);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login(){
        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setTitle("Đang đăng nhập ....");
        pd.show();
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        JSONObject params = new JSONObject();

        try {
            params.put("username", username.getText());
            params.put("password", password.getText());
        } catch (JSONException e) {

        }
        String url = "http://112.137.129.216:5001/api/users/login";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, params,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        pd.cancel();
//                        Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        if(response!=null){
                            //lưu vào máy
                            SharedPreferences sharedPreferences = getSharedPreferences("VALUABLE_APP", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            try {
                                editor.putString("LOGIN_TOKEN", (String) response.get("access_token"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            editor.commit();
                            System.out.println("save success LOGIN_TOKEN"+response.toString());
                            updateStateLogin();
                            // chuyển màn hình trước đó nếu đăng nhập thành công
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
//                Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_LONG).show();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
//                        System.out.println(res);
                        // hiện thông báo lỗi
                        JSONObject responseMsg = new JSONObject(res);
                        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Thông báo");
                    builder.setMessage("You must confirm your email first.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
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
        Volley.newRequestQueue(LoginActivity.this).add(jsonObjReq);
    }

    //cập nhật trạng thái log in
    public void updateStateLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences("VALUABLE_APP", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("LOGIN_TOKEN","");
        if(token!=""&&token!=null) MainActivity.STATUS_LOGIN=true;
    }
}
