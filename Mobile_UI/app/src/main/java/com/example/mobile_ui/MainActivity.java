package com.example.mobile_ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.mobile_ui.Fragment.AccountFragment;
import com.example.mobile_ui.Fragment.HomeFragment;
import com.example.mobile_ui.Fragment.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    int REQUEST_CODE_LOGIN = 13;
    public static boolean STATUS_LOGIN=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //thêm sự kiện cho bottom nav
        BottomNavigationView bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setOnNavigationItemSelectedListener(navListener);
        // khi mới vào, mặc định chọn nav.item_home
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

        //update do have token login
        updateStateLogin();
    }

    //ham set su kien cho navigation====================================================================================
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.navigation_notifications:
                    selectedFragment = new NotificationsFragment();
                    break;
                case R.id.navigation_account:
                    selectedFragment = new AccountFragment();
                    break;
            }
            //thay the fragment container bang fragment tuong ung
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_OK && data != null) {
            Button buttonSignUp, buttonLogin;
            buttonLogin = findViewById(R.id.buttonLogin);
            buttonSignUp = findViewById(R.id.buttonSignUp);
            buttonLogin.setVisibility(View.INVISIBLE);
            buttonSignUp.setVisibility(View.INVISIBLE);
            TextView textViewNameUser, textViewSoSp;
            textViewNameUser = findViewById(R.id.textViewNameUser);
            textViewSoSp = findViewById(R.id.textViewSoSp);
            // hiện do đăng nhập
            textViewNameUser.setVisibility(View.VISIBLE);
            textViewSoSp.setVisibility(View.VISIBLE);
        }
    }

    //cập nhật trạng thái log in
    public void updateStateLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences("VALUABLE_APP", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("LOGIN_TOKEN","");
        if(token!=""&&token!=null) STATUS_LOGIN=true;
    }
}