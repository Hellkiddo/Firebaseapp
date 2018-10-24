package com.example.user.shemulapp;

/**
 * Created by USER on 1/7/2018.
 */

public class posteditem {
    String description;
    String title;
    String image;

    public posteditem(){

    }


    public posteditem(String description, String title, String image) {
        this.description = description;
        this.title = title;
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
