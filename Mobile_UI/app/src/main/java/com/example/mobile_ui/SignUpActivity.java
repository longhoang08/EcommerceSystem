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
import com.example.mobile_ui.Adapter.CartProductAdapter;
import com.example.mobile_ui.Retrofit.APIUtils;
import com.example.mobile_ui.Retrofit.DataClient;
import com.example.mobile_ui.Retrofit.User;
import com.example.mobile_ui.View.ExpandHeightGridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class SignUpActivity extends AppCompatActivity {
    private EditText email;
    private EditText phone_number;
    private EditText fullname;
    private EditText password;
    private EditText address;
    private EditText gender;
    private Button buttonSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = findViewById(R.id.email);
//        phone_number = findViewById(R.id.phone_number);
        fullname = findViewById(R.id.fullname);
        password = findViewById(R.id.password);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
//        address = findViewById(R.id.address);
//        gender = findViewById(R.id.gender);

//        User user = new User(email.getText().toString(),phone_number.getText().toString(),fullname.getText().toString(),password.getText().toString(),address.getText().toString(),gender.getText().toString());
//        User user = new User(
//                "doanhnh0801@gmail.com", "0386549632", "DoanhNguyen", "abc12345", "HaNoi", 1);
        //call post api
//    DataClient dataClient = APIUtils.getData();
//    Call<String> callback = dataClient.signUp(user);
//        ((Call) callback).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if(response!=null){
//                    String x = response.body();
//                    Toast.makeText(SignUpActivity.this, x, Toast.LENGTH_SHORT).show();
//                }
//                else Toast.makeText(SignUpActivity.this,"fail",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.d("EEROR", t.getMessage());
//            }
//        });



    }
    private void signUp(){
        final ProgressDialog pd = new ProgressDialog(SignUpActivity.this);
        pd.setTitle("Vui lòng chờ 1 chút ....");
        pd.show();
        RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
        JSONObject params = new JSONObject();

        try {
            params.put("email", email.getText());
            params.put("password", password.getText());
                params.put("fullname", fullname.getText());

//            params.put("phone_number", "0854230458");
//            params.put("email", "doanhc@gmail.com");
//            params.put("address", "Ha Noi");
//            params.put("gender", 1);
        } catch (JSONException e) {
            System.out.println("OK");
        }
        String url = "http://112.137.129.216:5001/api/register/";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, params,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                NetworkResponse response = error.networkResponse;
//                    Toast.makeText(SignUpActivity.this, response.statusCode+"", Toast.LENGTH_LONG).show();
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
//                        System.out.println(res);
                        // hiện thông báo lỗi
                        JSONObject responseMsg = new JSONObject(res);
                        final AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
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
                    // hiện thông báo đăng nhập thành công
                    final AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setTitle("Thông báo");
                    builder.setCancelable(false);
                    builder.setMessage("Đăng ký thành công. Vui lòng check mail.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            SignUpActivity.this.finish();
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
        Volley.newRequestQueue(SignUpActivity.this).add(jsonObjReq);
    }

    //cập nhật trạng thái sign up
    public void updateStateSignUp(){
        SharedPreferences sharedPreferences = getSharedPreferences("VALUABLE_APP", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("LOGIN_TOKEN","");
        if(token!=""&&token!=null) MainActivity.STATUS_LOGIN=true;
    }
}
