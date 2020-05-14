package com.example.mobileuet.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mobileuet.R;
import com.example.mobileuet.Retrofit.APIUtils;
import com.example.mobileuet.Retrofit.DataClient;
import com.example.mobileuet.Retrofit.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        //1. tao ra fragment
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //2. fill du lieu trc khi hien fragment
        final TextView tv1 = root.findViewById(R.id.textViewEmail);
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        DataClient dataClient = APIUtils.getData();
        Call<List<User>> callback = dataClient.getUser();
        callback.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response!=null){
                    List<User> users = response.body();
                    String kq = "";
                    for (User s : users) {
                        Log.d(s.getEmail(),s.getEmail());
                        kq+=" " + s.getEmail();
                    }
                    tv1.setText(kq);
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable throwable) {
                Log.d("EEROR",throwable.getMessage());
            }
        });

        //3. return
        return root;
    }
}
