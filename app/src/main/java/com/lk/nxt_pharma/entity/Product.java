package com.lk.nxt_pharma.entity;

public class Product {


    String productname;
    String description;
    String imgurl;
    String brand;
    String category;
    Integer price;
    Integer discount;
    Integer priceafterdiscount;
    Integer stock;

    public Product() {
    }

    public Product(String productname, String description, String imgurl, String brand, String category, Integer price, Integer discount, Integer priceafterdiscount, Integer stock) {
        this.productname = productname;
        this.description = description;
        this.imgurl = imgurl;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.discount = discount;
        this.priceafterdiscount = priceafterdiscount;
        this.stock = stock;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getPriceafterdiscount() {
        return priceafterdiscount;
    }

    public void setPriceafterdiscount(Integer priceafterdiscount) {
        this.priceafterdiscount = priceafterdiscount;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
