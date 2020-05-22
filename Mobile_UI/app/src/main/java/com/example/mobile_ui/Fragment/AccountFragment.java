package com.example.mobile_ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mobile_ui.LoginActivity;
import com.example.mobile_ui.R;
import com.example.mobile_ui.SignUpActivity;

public class AccountFragment extends Fragment {

    Button buttonSignUp, buttonLogin;
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
        return root;
    }
}

