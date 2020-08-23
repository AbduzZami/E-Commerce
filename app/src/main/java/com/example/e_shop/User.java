package com.example.e_shop;

public class User {
    String username;
    String phone;
    String address;
    String imageurlext;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageurlext() {
        return imageurlext;
    }

    public void setImageurlext(String imageurlext) {
        this.imageurlext = imageurlext;
    }

    public User() {
    }

    public User(String username, String phone, String address,String imageurl) {
        this.username = username;
        this.phone = phone;
        this.address = address;
        this.imageurlext = imageurl ;
    }
}
