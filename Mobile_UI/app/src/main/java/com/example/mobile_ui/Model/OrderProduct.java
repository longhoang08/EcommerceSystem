package com.example.mobile_ui.Model;

public class OrderProduct {
    private String imageRepresent;
    private String nameProduct;
    private int price;
    private int quantity;
    private int quantityInStock;
    boolean state = false;
    String nameShop;
    String id;

    public String getNameShop() {
        return nameShop;
    }

    public void setNameShop(String nameShop) {
        this.nameShop = nameShop;
    }

    public OrderProduct(String nameShop, String imageRepresent, String nameProduct, int price, int quantity, int quantityInStock, String id) {
        this.nameShop = nameShop;
        this.imageRepresent = imageRepresent;
        this.nameProduct = nameProduct;
        this.price = price;
        this.quantity = quantity;
        this.quantityInStock = quantityInStock;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageRepresent() {
        return imageRepresent;
    }

    public void setImageRepresent(String imageRepresent) {
        this.imageRepresent = imageRepresent;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
}
