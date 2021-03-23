package com.lk.nxt_pharma.entity;

import java.util.Date;

public class Prescription {

    String imgurl;
    String description;
    String uploadstatus;

    String patientname;
    String patientemail;
    Date  submitdate;

    public Date getSubmitdate() {
        return submitdate;
    }

    public void setSubmitdate(Date submitdate) {
        this.submitdate = submitdate;
    }

    public Prescription() {
    }


    public Prescription(String imgurl, String description, String uploadstatus, String patientname, String patientemail, Date submitdate) {
        this.imgurl = imgurl;
        this.description = description;
        this.uploadstatus = uploadstatus;
        this.patientname = patientname;
        this.patientemail = patientemail;
        this.submitdate = submitdate;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUploadstatus() {
        return uploadstatus;
    }

    public void setUploadstatus(String uploadstatus) {
        this.uploadstatus = uploadstatus;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getPatientemail() {
        return patientemail;
    }

    public void setPatientemail(String patientemail) {
        this.patientemail = patientemail;
    }



}
