package com.example.librarymanagement.Model;

public class Book {


    private String Image,MenuId,Name,Bookid,Copies,Author,Authkey,Publisher;

    public Book() {
    }

    /* },
    "01": {
      "Name": "Operating Systems",
      "Image": "https://images-na.ssl-images-amazon.com/images/I/51O9j97ay9L._SX373_BO1,204,203,200_.jpg",
      "MenuId": "02",
      "Bookid": "106975",
      "Copies": "3",
      "Author": "Godbole A.S",
      "Authkey": "GOD",
      "Publisher": "Mc Graw Hill Education(India) Pvt. Ltd."
    },*/


    public Book( String name, String image,  String menuId, String bookid, String copies,String author,  String authkey, String publisher)
    {
        this.Image = image;
        this.MenuId = menuId;
        this.Name = name;
        this.Bookid = bookid;
        this.Copies = copies;
        this.Author = author;
        this.Authkey = authkey;
        this.Publisher= publisher;
    }

    public String getImage() {
        return this.Image;
    }

    public String getMenuId() {
        return this.MenuId;
    }

    public String getName() {
        return this.Name;
    }

    public String getBookid() {
        return this.Bookid;
    }

    public String getCopies() {
        return this.Copies;
    }

    public String getAuthor() {
        return this.Author;
    }
    public String getAuthkey() {
        return this.Authkey;
    }

    public String getPublisher() {
        return this.Publisher;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public void setMenuId(String menuId) {
        this.MenuId = menuId;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setBookid(String bookid) {
        this.Bookid = bookid;
    }

    public void setCopies(String copies) {
        this.Copies = copies;
    }

    public void setAuthor(String author) {
        this.Author = author;
    }

    public void setAuthkey(String authkey) {
        this.Authkey = authkey;
    }

    public void setPublisher(String publisher) {
        this.Publisher = publisher;
    }




}
