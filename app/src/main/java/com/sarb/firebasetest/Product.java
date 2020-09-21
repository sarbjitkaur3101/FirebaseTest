package com.sarb.firebasetest;

public class Product {

    String productName;
    String productMemory;
    String productPrice;
    String productImage;

    public Product(String productName, String productMemory, String productPrice, String productImage) {
        this.productName = productName;
        this.productMemory = productMemory;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductMemory() {
        return productMemory;
    }

    public void setProductMemory(String productMemory) {
        this.productMemory = productMemory;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
