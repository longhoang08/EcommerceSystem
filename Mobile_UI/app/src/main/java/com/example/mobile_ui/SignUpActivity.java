package com.example.mobile_ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_ui.Retrofit.APIUtils;
import com.example.mobile_ui.Retrofit.DataClient;
import com.example.mobile_ui.Retrofit.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private EditText email;
    private EditText phone_number;
    private EditText fullname;
    private EditText password;
    private EditText address;
    private EditText gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = findViewById(R.id.email);
        phone_number = findViewById(R.id.phone_number);
        fullname = findViewById(R.id.fullname);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);
        gender = findViewById(R.id.gender);

//        User user = new User(email.getText().toString(),phone_number.getText().toString(),fullname.getText().toString(),password.getText().toString(),address.getText().toString(),gender.getText().toString());
        User user = new User(
                "doanhnh0801@gmail.com", "0386549632", "DoanhNguyen", "abc12345", "HaNoi", 1);
        //call post api
    DataClient dataClient = APIUtils.getData();
    Call<String> callback = dataClient.signUp(user);
        ((Call) callback).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response!=null){
                    String x = response.body();
                    Toast.makeText(SignUpActivity.this,x,Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(SignUpActivity.this,"fail",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("EEROR", t.getMessage());
            }
        });
    }
}
