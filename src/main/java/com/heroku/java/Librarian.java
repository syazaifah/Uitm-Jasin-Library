package com.heroku.java;
import java.sql.Date;

public class Librarian {
    private String name;
    private String librarianID;
    private String phoneNumber;
    private String email;

    public Librarian(String name, String librarianID, String phoneNumber, String email) {
        this.name = name;
        this.librarianID = librarianID;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getlibrarianID() {
        return librarianID;
    }

    public void setlibrarianID(String librarianID) {
        this.librarianID = librarianID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}