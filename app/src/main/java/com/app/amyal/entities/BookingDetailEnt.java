package com.app.amyal.entities;

/**
 * Created by khan_muhammad on 11/29/2017.
 */

public class BookingDetailEnt {

    String name;
    String value;

    public BookingDetailEnt( String name, String value){
        setName(name);
        setValue(value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
