package com.example.blue_books;


import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;

public class FB_Sales implements Serializable {
    private int fb_sales_id;
    private DocumentReference fb_client_id;
    private DocumentReference fb_item_id;
    private String fb_sales_date;
    private int fb_sales_count;
    private int fb_sales_profit;

    public DocumentReference getFb_client_id() {
        return fb_client_id;
    }

    public FB_Sales() {
    }

    public int getFb_sales_id() {
        return fb_sales_id;
    }

    public void setFb_sales_id(int fb_sales_id) {
        this.fb_sales_id = fb_sales_id;
    }



    public void setFb_client_id(DocumentReference fb_client_id) {
        this.fb_client_id = fb_client_id;
    }

    public DocumentReference getFb_item_id() {
        return fb_item_id;
    }

    public void setFb_item_id(DocumentReference fb_item_id) {
        this.fb_item_id = fb_item_id;
    }

    public String getFb_sales_date() {
        return fb_sales_date;
    }

    public void setFb_sales_date(String fb_sales_date) {
        this.fb_sales_date = fb_sales_date;
    }

    public int getFb_sales_count() {
        return fb_sales_count;
    }

    public void setFb_sales_count(int fb_sales_count) {
        this.fb_sales_count = fb_sales_count;
    }

    public int getFb_sales_profit() {
        return fb_sales_profit;
    }

    public void setFb_sales_profit(int fb_sales_profit) {
        this.fb_sales_profit = fb_sales_profit;
    }
}