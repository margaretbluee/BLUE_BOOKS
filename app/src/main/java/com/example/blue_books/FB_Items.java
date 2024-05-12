package com.example.blue_books;


import java.io.Serializable;

public class FB_Items implements Serializable {
    private int fb_item_id;
    private String fb_item_name;
    private int fb_item_count;
    private String fb_item_cat;
    private int fb_item_price;
    private float fb_item_rating;

    public int getFb_item_id() {
        return fb_item_id;
    }

    public void setFb_item_id(int fb_item_id) {
        this.fb_item_id = fb_item_id;
    }

    public String getFb_item_name() {
        return fb_item_name;
    }

    public void setFb_item_name(String fb_item_name) {
        this.fb_item_name = fb_item_name;
    }

    public int getFb_item_count() {
        return fb_item_count;
    }

    public void setFb_item_count(int fb_item_count) {
        this.fb_item_count = fb_item_count;
    }

    public String getFb_item_cat() {
        return fb_item_cat;
    }

    public void setFb_item_cat(String fb_item_cat) {
        this.fb_item_cat = fb_item_cat;
    }

    public int getFb_item_price() {
        return fb_item_price;
    }

    public void setFb_item_price(int fb_item_price) {
        this.fb_item_price = fb_item_price;
    }

    public float getFb_item_rating() {
        return fb_item_rating;
    }

    public void setFb_item_rating(float fb_item_rating) {
        this.fb_item_rating = fb_item_rating;
    }

    public FB_Items(){}

}
