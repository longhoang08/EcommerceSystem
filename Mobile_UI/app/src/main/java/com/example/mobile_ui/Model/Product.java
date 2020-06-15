package com.example.mobile_ui.Model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private int imageRepresent;
    private String name;
    private int price;
    private double star;
    private Bitmap imgFromUrl;
    private int number;

    public Product(int imageRepresent, String name, int price, double star) {
        this.imageRepresent = imageRepresent;
        this.name = name;
        this.price = price;
        this.star = star;
    }

    public Product(Bitmap imgFromUrl, String name, int price, double star) {
        this.imgFromUrl = imgFromUrl;
        this.name = name;
        this.price = price;
        this.star = star;
    }

    public Product(int imageRepresent, String name, int price, int number) {
        this.imageRepresent = imageRepresent;
        this.name = name;
        this.price = price;
        this.number = number;
    }

    protected Product(Parcel in) {
        imageRepresent = in.readInt();
        name = in.readString();
        price = in.readInt();
        star = in.readDouble();
        imgFromUrl = in.readParcelable(Bitmap.class.getClassLoader());
        number = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public int getNumber() { return number;}

    public void setNumber(int number) { this.number = number; }

    public Bitmap getImgFromUrl() { return imgFromUrl; }

    public void setImgFromUrl(Bitmap imgFromUrl) { this.imgFromUrl = imgFromUrl; }

    public int getImageRepresent() {
        return imageRepresent;
    }

    public void setImageRepresent(int imageRepresent) {
        this.imageRepresent = imageRepresent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageRepresent);
        dest.writeString(name);
        dest.writeInt(price);
        dest.writeDouble(star);
        dest.writeParcelable(imgFromUrl, flags);
        dest.writeInt(number);
    }
}
