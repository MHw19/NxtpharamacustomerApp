package com.lk.nxt_pharma.entity;

public class Doctor {

    String fullname;
    String email;
    String mobile;
    String qualification;
    String university;
    String specification;
    String experience;
    String status;
    String docImgurl;

    public Doctor() {
    }

    public Doctor(String fullname, String email, String mobile, String qualification, String university, String specification, String experience, String status, String docImgurl) {
        this.fullname = fullname;
        this.email = email;
        this.mobile = mobile;
        this.qualification = qualification;
        this.university = university;
        this.specification = specification;
        this.experience = experience;
        this.status = status;
        this.docImgurl = docImgurl;
    }

    public String getDocImgurl() {
        return docImgurl;
    }

    public void setDocImgurl(String docImgurl) {
        this.docImgurl = docImgurl;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
