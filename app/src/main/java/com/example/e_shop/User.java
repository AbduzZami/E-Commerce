package com.example.e_shop;

public class User {
    String username;
    String phone;
    String address;

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

    public User() {
    }

    public User(String username, String phone, String address) {
        this.username = username;
        this.phone = phone;
        this.address = address;
    }
}
