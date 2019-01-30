package com.app.amyal.entities;

/**
 * Created by khan_muhammad on 12/5/2017.
 */

public class AmentityEnt {

    String image;
    String name;


    public AmentityEnt(String image,String name){

        setImage(image);
        setName(name);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
