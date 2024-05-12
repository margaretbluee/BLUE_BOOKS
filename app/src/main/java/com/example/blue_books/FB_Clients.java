package com.example.blue_books;


import java.io.Serializable;

public class FB_Clients implements Serializable {
    private int fb_client_id;
    private String fb_client_name;
    private String fb_client_city;

    public String getFb_client_city() {
        return fb_client_city;
    }

    public void setFb_client_city(String fb_client_city) {
        this.fb_client_city = fb_client_city;
    }

    public int getFb_client_id() {
        return fb_client_id;
    }

    public void setFb_client_id(int fb_client_id) {
        this.fb_client_id = fb_client_id;
    }

    public String getFb_client_name() {
        return fb_client_name;
    }

    public void setFb_client_name(String fb_client_name) {
        this.fb_client_name = fb_client_name;
    }

    public FB_Clients(int fb_client_id, String fb_client_name, String fb_client_city, float fb_client_rating) {
        this.fb_client_id = fb_client_id;
        this.fb_client_name = fb_client_name;
        this.fb_client_city = fb_client_city;
        this.fb_client_rating = fb_client_rating;
    }

    public float getFb_client_rating() {
        return fb_client_rating;
    }

    public void setFb_client_rating(float fb_client_rating) {
        this.fb_client_rating = fb_client_rating;
    }


    private float fb_client_rating;


    public FB_Clients(){}

}
