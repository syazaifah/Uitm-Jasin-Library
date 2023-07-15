package com.heroku.Modal;

public class Book {
    private String bookid;
    private String booktitle;
    private String author;
    private int bookquantity;
    
    public Book(String bookid, String booktitle, String author, int bookquantity) {
        this.bookid = bookid;
        this.booktitle = booktitle;
        this.author = author;
        this.bookquantity = bookquantity;
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

    public int getBookquantity() {
        return bookquantity;
    }

    public void setBookquantity(int bookquantity) {
        this.bookquantity = bookquantity;
    }
   
}
    

