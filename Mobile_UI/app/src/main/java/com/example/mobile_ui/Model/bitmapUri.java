package com.example.mobile_ui.Model;

import android.graphics.Bitmap;
import android.net.Uri;

public class bitmapUri {
    Bitmap bitmap;
    Uri uri;
    String url;

    public bitmapUri(String url) {
        this.url = url;
    }

    public bitmapUri(Bitmap bitmap, Uri uri) {
        this.bitmap = bitmap;
        this.uri = uri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
