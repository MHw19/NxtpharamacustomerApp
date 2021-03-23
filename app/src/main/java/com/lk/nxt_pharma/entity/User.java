package com.lk.nxt_pharma.entity;

import java.io.Serializable;

public class User  implements Serializable {



    private String password;
    private String status;
    private String logStatus;


    public User() {
    }

    public User(String password, String status, String logStatus) {
        this.password = password;
        this.status = status;
        this.logStatus = logStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(String logStatus) {
        this.logStatus = logStatus;
    }
}
