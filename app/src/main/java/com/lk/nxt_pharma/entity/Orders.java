package com.lk.nxt_pharma.entity;

import java.util.Date;

public class Orders {

    String cusID;
    Date date;
    String productname;
    String productID;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;
    double qty;
    double itemcost;
    String payment;


    public Orders() {
    }

    public Orders(String cusID, Date date, String productname, String productID, String status, double qty, double itemcost, String payment) {
        this.cusID = cusID;
        this.date = date;
        this.productname = productname;
        this.productID = productID;
        this.status = status;
        this.qty = qty;
        this.itemcost = itemcost;
        this.payment = payment;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getCusID() {
        return cusID;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getItemcost() {
        return itemcost;
    }

    public void setItemcost(double itemcost) {
        this.itemcost = itemcost;
    }
}
