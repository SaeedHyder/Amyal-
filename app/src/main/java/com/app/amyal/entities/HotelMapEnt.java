package com.app.amyal.entities;

/**
 * Created by khan_muhammad on 12/2/2017.
 */

public class HotelMapEnt {

    int id;
    String price;
    String longitude;
    String latitude;
    String markerPath;

    public HotelMapEnt(int id,String price,String latitude,String longitude,String markerPath){
        setId(id);
        setPrice(price);
        setLatitude(latitude);
        setLongitude(longitude);
        setMarkerPath(markerPath);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getMarkerPath() {
        return markerPath;
    }

    public void setMarkerPath(String markerPath) {
        this.markerPath = markerPath;
    }
}
