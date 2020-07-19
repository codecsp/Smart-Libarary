package com.example.librarymanagement.Model;

import java.util.List;
public class Request {

    private String prn,bookId;

    public Request() {

    }

    public Request(String prn, String bookId) {
        this.prn = prn;
        this.bookId = bookId;
    }

    public String getPrn() {
        return prn;
    }

    public void setPrn(String prn) {
        this.prn = prn;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
