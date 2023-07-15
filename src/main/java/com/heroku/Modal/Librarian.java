package com.heroku.Modal;

public class Librarian {
    private String libid;
    private String libname;
    private String libphonenumber;
    private String libemail;
    private String libpassword;


    public Librarian(String libid, String libname, String libphonenumber, String libemail, String libpassword) {
        this.libid = libid;
        this.libname = libname;
        this.libphonenumber= libphonenumber;
        this.libemail = libemail;
        this.libpassword = libpassword;
    }

    public String getId() {
        return libid;
    }

    public void setId(String libid) {
        this.libid = libid;
    }

    public String getName() {
        return libname;
    }

    public void setName(String libname) {
        this.libname = libname;
    }

    public String getPhone() {
        return libphonenumber;
    }

    public void setPhone(String libphonenumber) {
        this.libphonenumber = libphonenumber;
    }

    public String getEmail() {
        return libemail;
    }

    public void setEmail(String libemail) {
        this.libemail = libemail;
    }

    public String getPassword() {
        return libpassword;
    }

    public void setPassword(String libpassword) {
        this.libpassword = libpassword;
    }
}