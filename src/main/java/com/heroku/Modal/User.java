package com.heroku.Modal;

public class User {
    private String studid;
    private String studname;
    private String studphonenumber;
    private String studemail;
    private String studpassword;

    public User(String studid, String studname, String studphonenumber, String studemail, String studpassword) {
        this.studid = studid;
        this.studname = studname;
        this.studphonenumber= studphonenumber;
        this.studemail = studemail;
        this.studpassword = studpassword;
    }

    public String getId() {
        return studid;
    }

    public void setId(String studid) {
        this.studid = studid;
    }

    public String getName() {
        return studname;
    }

    public void setName(String studname) {
        this.studname = studname;
    }
    public String getPhone() {
        return studphonenumber;
    }

    public void setPhone(String studphonenumber) {
        this.studphonenumber = studphonenumber;
    }

    public String getEmail() {
        return studemail;
    }

    public void setEmail(String studemail) {
        this.studemail = studemail;
    }

    public String getPassword() {
        return studpassword;
    }

    public void setPassword(String studpassword) {
        this.studpassword = studpassword;
    }
}