package com.example.mobile_ui.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MyBuyRecord {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String state;
    private ArrayList<Product> buyProduct;
    private ArrayList<Integer> buyNum;
    private int money;
    private String nameOfShop;

    public MyBuyRecord(int id, String nameOfShop, String state, ArrayList<Product> product, ArrayList<Integer> buyNum, int money) {
        this.id = id;
        this.nameOfShop = nameOfShop;
        this.state = state;
        this.buyProduct=product;
        this.buyNum=buyNum;
        this.money=money;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<Product> getBuyProduct() {
        return buyProduct;
    }

    public void setBuyProduct(ArrayList<Product> buyProduct) {
        this.buyProduct = buyProduct;
    }

    public ArrayList<Integer> getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(ArrayList<Integer> buyNum) {
        this.buyNum = buyNum;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getNameOfShop() {
        return nameOfShop;
    }

    public void setNameOfShop(String nameOfShop) {
        this.nameOfShop = nameOfShop;
    }

}

