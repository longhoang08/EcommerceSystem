package com.example.mobile_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.RestrictionEntry;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ResetPassActivity extends AppCompatActivity {
    EditText oldpassword;
    EditText newpassword;
    EditText renewpassword;
    Button changPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        oldpassword=findViewById(R.id.oldpassword);
        newpassword=findViewById(R.id.newpassword);
        renewpassword=findViewById(R.id.renewpassword);
        changPass=findViewById(R.id.changPass);
        //
        changPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass =oldpassword.getText().toString();
                final String newPass =newpassword.getText().toString();
                final String reNewPass =renewpassword.getText().toString();
                if(!newPass.equals(reNewPass)) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ResetPassActivity.this);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Mật khẩu xác nhận không đúng");
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    return;
                }
                postToChangePass(oldPass,newPass);
            }
        });
    }

    private void postToChangePass(String oldPass,String newPass){
        System.out.println(oldPass+" "+newPass);
        // call api get status
        RequestQueue queue = Volley.newRequestQueue(ResetPassActivity.this);
        JSONObject params = new JSONObject();


        try {
            params.put("current_password", oldPass);
            params.put("new_password", newPass);
        } catch (JSONException e) {
            System.out.println("OK");
        }

        final String url = "http://112.137.129.216:5001/api/profile/change_password";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, params,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                            Toast.makeText(ResetPassActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(ResetPassActivity.this);
                        builder.setTitle("Thông báo");
                        builder.setMessage("Đổi mật khẩu thành công");
                        builder.setCancelable(false);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                finish();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(ResetPassActivity.this, "error", Toast.LENGTH_LONG).show();
                NetworkResponse response = error.networkResponse;
                System.out.println(error.getClass().toString());
                if (response != null) {
                    try {
                        final String res = new String(response.data, StandardCharsets.UTF_8);
//                        System.out.println(res);
                        // hiện thông báo lỗi
                        JSONObject responseMsg = new JSONObject(res);
                        final AlertDialog.Builder builder = new AlertDialog.Builder(ResetPassActivity.this);
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
                    } catch (JSONException e1) {
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
                SharedPreferences sharedPreferences = ResetPassActivity.this.getSharedPreferences("VALUABLE_APP", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("LOGIN_TOKEN","");
                System.out.println(token);
                headers.put("x-access-token", token);
                return headers;
            }
        };
        Volley.newRequestQueue(ResetPassActivity.this).add(jsonObjReq);
    }
}
