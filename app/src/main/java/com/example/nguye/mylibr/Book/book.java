package com.example.nguye.mylibr.Book;

public class book {
    public String bookId;
    public String bookName;
    public String kind;
    public String pH;;
    public String author;
    public int price;
    public String picPre;
    public String note;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getpH() {
        return pH;
    }

    public void setpH(String pH) {
        this.pH = pH;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPicPre() {
        return picPre;
    }

    public void setPicPre(String picPre) {
        this.picPre = picPre;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public book(String bookId, String bookName, String kind, String pH, String author, int price, String picPre, String note) {

        this.bookId = bookId;
        this.bookName = bookName;
        this.kind = kind;
        this.pH = pH;
        this.author = author;
        this.price = price;
        this.picPre = picPre;
        this.note = note;
    }

    public book() {

    }
}
