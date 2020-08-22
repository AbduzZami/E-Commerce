package com.example.e_shop;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Product {
    private String item_name , item_price , item_image ,item_cat , item_cart , item_id , item_popular;

    public Product(String item_name, String item_price, String item_image ,
                   String item_cat , String item_cart , String item_id , String item_popular) {
        this.item_name = item_name;
        this.item_price = item_price;
        this.item_image = item_image;
        this.item_cat = item_cat ;
        this.item_cart = item_cart ;
        this.item_id = item_id ;
        this.item_popular = item_popular ;
    }

    public Product() {
    }

    public String getItem_popular() {
        return item_popular;
    }

    public void setItem_popular(String item_popular) {
        this.item_popular = item_popular;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_cat() {
        return item_cat;
    }

    public void setItem_cat(String item_cat) {
        this.item_cat = item_cat;
    }

    public String getItem_cart() {
        return item_cart;
    }

    public void setItem_cart(String item_cart) {
        this.item_cart = item_cart;
    }

}
