package com.example.mobile_ui.Model;

public class BuyRecord {
    private String state;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    private Customer customer;
    private Product buyProduct;
    private int buyNum;
    private int money;

    public BuyRecord(String state,Customer customer,Product product,int buyNum,int money) {
        this.state = state;
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

    public Product getBuyProduct() {
        return buyProduct;
    }

    public void setBuyProduct(Product buyProduct) {
        this.buyProduct = buyProduct;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}

