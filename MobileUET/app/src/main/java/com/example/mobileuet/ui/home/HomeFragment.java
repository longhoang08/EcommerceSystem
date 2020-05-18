package com.example.mobileuet.ui.home;

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

public class HomeFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        User user= new User("Nguyen Thi Thu Thuy","12345","Thuy@gmail.com");

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView tv2 = root.findViewById(R.id.textViewEmail);
        //call post api
        DataClient dataClient = APIUtils.getData();
        Call<User> callback = dataClient.signUp(user);
        callback.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response!=null){
                    User x = response.body();
                    String kq = x.getName()+ " "+x.getEmail();
                    tv2.setText(kq);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("EEROR",t.getMessage());
            }
        });

        return root;
    }
}
