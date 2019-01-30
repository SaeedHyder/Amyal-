package com.app.amyal.entities;

/**
 * Created by khan_muhammad on 12/2/2017.
 */

public class HotelEnt {

    String hotelImage;
    String hotelName;
    float ratting;
    String hotelLocation;
    String hotelReview;
    String price;

    public HotelEnt(String hotelImage,String hotelName,float ratting,String hotelLocation,String hotelReview,String price){

        setHotelImage(hotelImage);
        setHotelName(hotelName);
        setRatting(ratting);
        setHotelLocation(hotelLocation);
        setHotelReview(hotelReview);
        setPrice(price);
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelImage() {
        return hotelImage;
    }

    public void setHotelImage(String hotelImage) {
        this.hotelImage = hotelImage;
    }

    public float getRatting() {
        return ratting;
    }

    public void setRatting(float ratting) {
        this.ratting = ratting;
    }

    public String getHotelLocation() {
        return hotelLocation;
    }

    public void setHotelLocation(String hotelLocation) {
        this.hotelLocation = hotelLocation;
    }

    public String getHotelReview() {
        return hotelReview;
    }

    public void setHotelReview(String hotelReview) {
        this.hotelReview = hotelReview;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
