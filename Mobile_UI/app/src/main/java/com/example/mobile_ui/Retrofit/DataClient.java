package com.example.mobile_ui.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface DataClient {
    @GET("/")
    Call<List<User>> getUser();

    @Headers({"Accept:application/json", "Content-Type:application/json;"})
    @POST("/register")
    Call<String> signUp(@Body User user);

    @PUT("/")
    Call<User> updateUser(@Body User user);

    @DELETE("/")
    void deleteUser(@Field("email") String email);

//    @FormUrlEncoded
//    @POST("signin/")
//    Call<User> signIn(@Field("email") String email, @Field("name") String name);
}
