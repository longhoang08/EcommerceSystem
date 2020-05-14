package com.example.mobileuet.Retrofit;

//gui nhan du lieu ve
public class APIUtils {
    public static final String Base_Url="http://192.168.56.1:3000/";

    public static DataClient getData(){
        return RetrofitClient.getClient(Base_Url).create(DataClient.class);
    }
}
