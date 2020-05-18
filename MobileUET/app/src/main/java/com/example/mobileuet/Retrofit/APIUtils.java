package com.example.mobileuet.Retrofit;

//gui nhan du lieu ve
public class APIUtils {
    public static final String Base_Url="https://yeuem.herokuapp.com/";

    public static DataClient getData(){
        return RetrofitClient.getClient(Base_Url).create(DataClient.class);
    }
}
