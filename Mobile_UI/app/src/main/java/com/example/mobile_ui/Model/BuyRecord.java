package com.example.mobile_ui.Model;

import java.util.ArrayList;

public class BuyRecord {
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String state;
    private int id;
    private Customer customer;
    private ArrayList<Product> buyProduct;
    private ArrayList<Integer> buyNum;
    private int money;

    public BuyRecord(String state, int id, Customer customer, ArrayList<Product> product, ArrayList<Integer> buyNum, int money) {
        this.state = state;
        this.id = id;
        this.customer=customer;
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
}

