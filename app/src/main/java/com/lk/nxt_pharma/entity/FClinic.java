package com.lk.nxt_pharma.entity;

import java.util.Date;

public class FClinic {


    String  clinicDate;
    String  timeslot;
    String  spesification;
    String  Docname;
    String  DocID;
    String place;

    public FClinic() {
    }

    public FClinic(String clinicDate, String timeslot, String spesification, String docname, String docID, String place) {
        this.clinicDate = clinicDate;
        this.timeslot = timeslot;
        this.spesification = spesification;
        Docname = docname;
        DocID = docID;
        this.place = place;
    }

    public String getClinicDate() {
        return clinicDate;
    }

    public void setClinicDate(String clinicDate) {
        this.clinicDate = clinicDate;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public String getSpesification() {
        return spesification;
    }

    public void setSpesification(String spesification) {
        this.spesification = spesification;
    }

    public String getDocname() {
        return Docname;
    }

    public void setDocname(String docname) {
        Docname = docname;
    }

    public String getDocID() {
        return DocID;
    }

    public void setDocID(String docID) {
        DocID = docID;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
