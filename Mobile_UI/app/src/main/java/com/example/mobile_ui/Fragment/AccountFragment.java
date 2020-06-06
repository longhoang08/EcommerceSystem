package com.example.mobile_ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mobile_ui.Adapter.CartProductShopAdapter;
import com.example.mobile_ui.LoginActivity;
import com.example.mobile_ui.Model.CartShop;
import com.example.mobile_ui.R;
import com.example.mobile_ui.SettingAccountActivity;
import com.example.mobile_ui.SignUpActivity;
import com.example.mobile_ui.StallActivity;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment {

    Button buttonSignUp, buttonLogin;
    ListView listViewDetailAcc;
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_account, container, false);
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        buttonLogin = root.findViewById(R.id.buttonLogin);
        buttonSignUp = root.findViewById(R.id.buttonSignUp);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
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
        abc.add("Thiết lập tài khoản");
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
                }
            }
        });
        return root;
    }
}

