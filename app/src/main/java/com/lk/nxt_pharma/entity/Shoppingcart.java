package com.lk.nxt_pharma.entity;

public class Shoppingcart {

    String cusID;
    String productID;

    String productname;

    double qty;

    double itemCost;

    public Shoppingcart(String cusID, String productID, String productname, double qty, double itemCost) {
        this.cusID = cusID;
        this.productID = productID;
        this.productname = productname;
        this.qty = qty;
        this.itemCost = itemCost;
    }

    public Shoppingcart() {
    }

    public String getCusID() {
        return cusID;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getItemCost() {
        return itemCost;
    }

    public void setItemCost(double itemCost) {
        this.itemCost = itemCost;
    }
}
