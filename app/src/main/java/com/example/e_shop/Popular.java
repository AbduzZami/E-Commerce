package com.example.e_shop;

public class Popular {
    private String item_name , item_price , item_image ;

    public Popular(String item_name, String item_price, String item_image) {
        this.item_name = item_name;
        this.item_price = item_price;
        this.item_image = item_image;
    }

    public Popular() {
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
}
