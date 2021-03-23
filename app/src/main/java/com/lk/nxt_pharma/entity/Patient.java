package com.lk.nxt_pharma.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class Patient implements  Serializable {


    private String fullName;
    private String email;
    private String mobile;


     private   Date dob;
    private int logStatus;
    private String  userStatus;
   private String authid;
   private String fcmId;
   private String password;


    public String getFcmId() {
        return fcmId;
    }

    public void setFcmId(String fcmId) {
        this.fcmId = fcmId;
    }

    public String getPassword() {
        return password;
    }

    public Patient(String fullName, String email, String mobile, Date dob, int logStatus, String userStatus, String authid, String fcmId, String password) {
        this.fullName = fullName;
        this.email = email;
        this.mobile = mobile;
        this.dob = dob;
        this.logStatus = logStatus;
        this.userStatus = userStatus;
        this.authid = authid;
        this.fcmId = fcmId;
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Patient() {
    }

    public String getAuthid() {
        return authid;
    }

    public void setAuthid(String authid) {
        this.authid = authid;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(int logStatus) {
        this.logStatus = logStatus;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
