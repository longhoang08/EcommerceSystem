package com.example.mobileuet.Retrofit;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface DataClient {
    @GET("/")
    Call<List<User>> getUser();

    @POST("/")
    Call<User> signUp(@Body User user);

    @PUT("/")
    Call<User> updateUser(@Body User user);

    @DELETE("/")
    void deleteUser(@Field("email") String email);

//    @FormUrlEncoded
//    @POST("signin/")
//    Call<User> signIn(@Field("email") String email, @Field("name") String name);
}
