package com.example.mobile_ui.Model;

public class Payment {
    String nameShop, imageProduct, nameProduct;
    int pricePro, buyNumPro, feeTransport, feeSale;

    public Payment(String nameShop, String imageProduct, String nameProduct, int pricePro, int buyNumPro, int feeTransport, int feeSale) {
        this.nameShop = nameShop;
        this.imageProduct = imageProduct;
        this.nameProduct = nameProduct;
        this.pricePro = pricePro;
        this.buyNumPro = buyNumPro;
        this.feeTransport = feeTransport;
        this.feeSale = feeSale;
    }

    public String getNameShop() {
        return nameShop;
    }

    public void setNameShop(String nameShop) {
        this.nameShop = nameShop;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getPricePro() {
        return pricePro;
    }

    public void setPricePro(int pricePro) {
        this.pricePro = pricePro;
    }

    public int getBuyNumPro() {
        return buyNumPro;
    }

    public void setBuyNumPro(int buyNumPro) {
        this.buyNumPro = buyNumPro;
    }

    public int getFeeTransport() {
        return feeTransport;
    }

    public void setFeeTransport(int feeTransport) {
        this.feeTransport = feeTransport;
    }

    public int getFeeSale() {
        return feeSale;
    }

    public void setFeeSale(int feeSale) {
        this.feeSale = feeSale;
    }
}
