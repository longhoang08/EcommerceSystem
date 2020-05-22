package com.example.mobile_ui.Model;

import java.util.List;

public class CartShop {
    private String name;
    List<OrderProduct> listOrderProduct;

    public CartShop(String name, List<OrderProduct> listOrderProduct) {
        this.name = name;
        this.listOrderProduct = listOrderProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OrderProduct> getListOrderProduct() {
        return listOrderProduct;
    }

    public void setListOrderProduct(List<OrderProduct> listOrderProduct) {
        this.listOrderProduct = listOrderProduct;
    }
}
