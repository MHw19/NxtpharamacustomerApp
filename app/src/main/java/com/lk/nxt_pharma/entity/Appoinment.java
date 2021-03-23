package com.lk.nxt_pharma.entity;

public class Appoinment {

    String date;
    String timeslot;
    String cusID;
    String cusname;
    String docID;
    String docname;
    String status;


    public Appoinment() {
    }

    public Appoinment(String date, String timeslot, String cusID, String cusname, String docID, String docname, String status) {
        this.date = date;
        this.timeslot = timeslot;
        this.cusID = cusID;
        this.cusname = cusname;
        this.docID = docID;
        this.docname = docname;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public String getCusID() {
        return cusID;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }

    public String getCusname() {
        return cusname;
    }

    public void setCusname(String cusname) {
        this.cusname = cusname;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
