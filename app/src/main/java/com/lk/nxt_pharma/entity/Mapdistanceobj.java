package com.lk.nxt_pharma.entity;

public class Mapdistanceobj {

    String distancetext;
    double distancevalue;

    public Mapdistanceobj(String distancetext, double distancevalue) {
        this.distancetext = distancetext;
        this.distancevalue = distancevalue;
    }



    public String getDistancetext() {
        return distancetext;
    }

    public void setDistancetext(String distancetext) {
        this.distancetext = distancetext;
    }

    public double getDistancevalue() {
        return distancevalue;
    }

    public void setDistancevalue(double distancevalue) {
        this.distancevalue = distancevalue;
    }
}
