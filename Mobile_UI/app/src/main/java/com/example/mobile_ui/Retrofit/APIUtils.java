package com.example.mobile_ui.Retrofit;

//gui nhan du lieu ve
public class
APIUtils {
    public static final String Base_Url="http://112.137.129.216:5001/api/";

    public static DataClient getData(){
        return RetrofitClient.getClient(Base_Url).create(DataClient.class);
    }
}
