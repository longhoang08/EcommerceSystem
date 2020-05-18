package com.example.mobileuet.Retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//        "user":"an",
//        "password":"12345",
//        "id":0,
//        "is_admin":"Y",
//        "name":"Nguyễn Thành An",
//        "email":"nguyenthanhan1181999@gmail.com",
//        "phone":"473654765",
//        "address":"hà nội"
public class User implements Parcelable {
    public User(String name, String password,String email){
        this.name=name;
        this.password=password;
        this.email=email;
    }

    @SerializedName("user")
    @Expose
    private String user;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("is_admin")
    @Expose
    private String is_admin;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("address")
    @Expose
    private String address;

    protected User(Parcel in) {
        user = in.readString();
        password = in.readString();
        id = in.readString();
        is_admin = in.readString();
        name = in.readString();
        email = in.readString();
        phone = in.readString();
        address = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    //------------------------------
    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public String getIs_admin() {
        return is_admin;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress(){
        return address;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user);
        dest.writeString(password);
        dest.writeString(id);
        dest.writeString(is_admin);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(address);
    }
}
