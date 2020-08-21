package com.example.e_shop;

public class Category {
    String cat_name;
    String cat_icon_url;
    String cat_bg;

    public Category(String cat_name, String cat_icon_url, String cat_bg) {
        this.cat_name = cat_name;
        this.cat_icon_url = cat_icon_url;
        this.cat_bg = cat_bg;
    }

    public Category() {
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_icon_url() {
        return cat_icon_url;
    }

    public void setCat_icon_url(String cat_icon_url) {
        this.cat_icon_url = cat_icon_url;
    }

    public String getCat_bg() {
        return cat_bg;
    }

    public void setCat_bg(String cat_bg) {
        this.cat_bg = cat_bg;
    }
}
