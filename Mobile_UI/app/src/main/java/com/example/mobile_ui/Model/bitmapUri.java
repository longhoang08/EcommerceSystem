package com.example.mobile_ui.Model;

import android.graphics.Bitmap;
import android.net.Uri;

public class bitmapUri {
    Bitmap bitmap;
    Uri uri;

    public bitmapUri(Bitmap bitmap, Uri uri) {
        this.bitmap = bitmap;
        this.uri = uri;
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
