package com.example.mobile_ui.Model;

public class Customer {
    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private int img;
    private String username;
    private String sex;
    private String dateOfBirth;
    private String address;
    private String phone;
    private String password;

    public Customer(int img, String username, String sex, String dateOfBirth,String address,String phone,String password) {
        this.img=img;
        this.username=username;
        this.sex = sex;
        this.dateOfBirth=dateOfBirth;
        this.address=address;
        this.phone=phone;
        this.password=password;
    }


}

