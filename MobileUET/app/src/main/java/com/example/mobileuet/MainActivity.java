package com.example.mobileuet;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.mobileuet.ui.dashboard.DashboardFragment;
import com.example.mobileuet.ui.home.HomeFragment;
import com.example.mobileuet.ui.notifications.NotificationsFragment;
import com.example.mobileuet.ui.search.ListviewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static SearchView searchView;
    //ham main khi chay
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //search=========================================================================================================
        searchView = (SearchView) findViewById(R.id.search);

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                searchView.setIconifiedByDefault(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ListviewFragment()).commit();
            }

        });
//        SearchView.OnCloseListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                Intent x =
//            }
//        });
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               ListviewFragment.adapter.getFilter().filter(newText);
               return false;
           }
       });

        //thêm sự kiện cho bottom nav
        BottomNavigationView bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setOnNavigationItemSelectedListener(navListener);
        // khi mới vào, mặc định chọn nav.item_home
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
    }

    //ham set su kien cho navigation====================================================================================
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    Window w = getWindow();
                    switch (item.getItemId()){
                        case R.id.navigation_home:
                            selectedFragment = new HomeFragment();
                            w.setTitle("Home");
                            break;
                        case R.id.navigation_notifications:
                            selectedFragment = new NotificationsFragment();
                            w.setTitle("Notifications");
                            break;
                        case R.id.navigation_dashboard:
                            selectedFragment = new DashboardFragment();
                            w.setTitle("Dashboard");
                            break;
                    }
                    //thay the fragment container bang fragment tuong ung
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return true;
                }
            };
}
