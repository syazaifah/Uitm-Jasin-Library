package com.heroku.Modal;

public class Book {
    private String bookid;
    private String booktitle;
    private String author;
    private String status;
    private int bookquantity;


    public Book() {
    }
    
    public Book(String bookid, String booktitle, String author, String status, int bookquantity) {
        this.bookid = bookid;
        this.booktitle = booktitle;
        this.author = author;
        this.status = status;
        this.bookquantity = bookquantity;
    }

    public Book(String bookid, String booktitle, String author) {
        this.bookid = bookid;
        this.booktitle = booktitle;
        this.author = author;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getBooktitle() {
        return booktitle;
    }

    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

     public void setStatus(String status) {
        this.status = status;
    }

    public int getBookquantity() {
        return bookquantity;
    }

    public void setBookquantity(int bookquantity) {
        this.bookquantity = bookquantity;
    }
   
}
    

