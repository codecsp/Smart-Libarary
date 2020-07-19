package com.example.admin.Model;

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
        Image = image;
        MenuId = menuId;
        Name = name;
        Bookid = bookid;
        Copies = copies;
        Author = author;
        Authkey = authkey;
        Publisher = publisher;
    }

    public String getImage() {
        return Image;
    }

    public String getMenuId() {
        return MenuId;
    }

    public String getName() {
        return Name;
    }

    public String getBookid() {
        return Bookid;
    }

    public String getCopies() {
        return Copies;
    }

    public String getAuthor() {
        return Author;
    }

    public String getAuthkey() {
        return Authkey;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setBookid(String bookid) {
        Bookid = bookid;
    }

    public void setCopies(String copies) {
        Copies = copies;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }




}
