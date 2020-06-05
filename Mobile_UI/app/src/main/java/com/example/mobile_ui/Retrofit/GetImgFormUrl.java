package com.example.mobile_ui.Retrofit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class GetImgFormUrl {
    public static void getImgFromurl(ImageView imgview,String url){
        try {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
            imgview.setImageBitmap(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Bitmap getBitmapImgFromUrl(String url){
        Bitmap bitmap = null;
//        try {
//            bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }  catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            InputStream in = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }
}
