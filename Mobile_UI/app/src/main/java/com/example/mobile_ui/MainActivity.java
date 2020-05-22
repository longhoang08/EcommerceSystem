package com.example.mobile_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.mobile_ui.Fragment.AccountFragment;
import com.example.mobile_ui.Fragment.HomeFragment;
import com.example.mobile_ui.Fragment.ListviewSearchFragment;
import com.example.mobile_ui.Fragment.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    // gio hang
    private ImageButton imageButtonCart;
    //search
    public static SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //thêm sự kiện cho searchView
        searchSetEvent();
        //thêm sự kiện cho bottom nav
        BottomNavigationView bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setOnNavigationItemSelectedListener(navListener);
        // khi mới vào, mặc định chọn nav.item_home
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
        // su kien an vao gio hang
        imageButtonCart = findViewById(R.id.imageButtonCart);
        imageButtonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartProductActivity.class);
                startActivity(intent);
            }
        });
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

    void searchSetEvent(){
        //search=========================================================================================================
        searchView = (SearchView) findViewById(R.id.searchView);

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                searchView.setIconifiedByDefault(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ListviewSearchFragment()).commit();
            }

        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ListviewSearchFragment.adapter.getFilter().filter(newText);
                return false;
            }
        });

    }



}
