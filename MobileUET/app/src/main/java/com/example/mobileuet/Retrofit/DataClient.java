package com.example.mobileuet.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataClient {
    @GET("/")
    Call<List<User>> getUser();


}
