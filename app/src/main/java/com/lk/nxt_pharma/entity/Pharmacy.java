package com.lk.nxt_pharma.entity;

public class Pharmacy {

    double shopLat;
    double shopLati;

    public Pharmacy() {
    }

    public Pharmacy(double shopLat, double shopLati) {
        this.shopLat = shopLat;
        this.shopLati = shopLati;
    }

    public double getShopLat() {
        return shopLat;
    }

    public void setShopLat(double shopLat) {
        this.shopLat = shopLat;
    }

    public double getShopLati() {
        return shopLati;
    }

    public void setShopLati(double shopLati) {
        this.shopLati = shopLati;
    }
}
