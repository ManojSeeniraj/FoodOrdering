package com.example.smanoj.androideatit.Model;

/**
 * Created by s.manoj on 09-01-2018.
 */

public class Category {

    private String NAME;
    private  String Image;

    public Category() {
    }

    public Category(String name, String image) {
        NAME = name;
        Image = image;
    }

    public String getName() {
        return NAME;
    }

    public void setName(String name) {
        NAME = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
