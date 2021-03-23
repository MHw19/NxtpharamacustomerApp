package com.lk.nxt_pharma.entity;

public class Event {

    String title;
    double discount;
    String category;
    String message;
    int duration;

    public Event() {
    }

    public Event(String title, double discount, String category, String message, int duration) {
        this.title = title;
        this.discount = discount;
        this.category = category;
        this.message = message;
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
