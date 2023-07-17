package com.heroku.Modal;

import java.sql.Date;

public class Borrow {
    private String studid;
    private String borrowid;
    private String bookid;
    private int bookquantity;
    private Date dateborrow;
    private Date datereturn;
    
    public Borrow(String studid, String borrowid, String bookid, int bookquantity, Date dateborrow,
            Date datereturn) {
        this.studid = studid;
        this.borrowid = borrowid;
        this.bookid = bookid;
        this.bookquantity = bookquantity;
        this.dateborrow = dateborrow;
        this.datereturn = datereturn;
    }

    public Borrow(){

    }

    public String getStudid() {
        return studid;
    }

    public void setStudid(String studid) {
        this.studid = studid;
    }

    public String getBorrowid() {
        return borrowid;
    }

    public void setBorrowid(String borrowid) {
        this.borrowid = borrowid;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public int getBookquantity() {
        return bookquantity;
    }

    public void setBookquantity(int bookquantity) {
        this.bookquantity = bookquantity;
    }

    public Date getDateborrow() {
        return dateborrow;
    }

    public void setDateborrow(Date dateborrow) {
        this.dateborrow = dateborrow;
    }

    public Date getDatereturn() {
        return datereturn;
    }

    public void setDatereturn(Date datereturn) {
        this.datereturn = datereturn;
    }

    
    
}