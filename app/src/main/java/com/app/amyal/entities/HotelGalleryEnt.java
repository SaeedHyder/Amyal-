package com.app.amyal.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HotelGalleryEnt {

    @SerializedName("Images")
    @Expose
    private List<String> images = null;
    @SerializedName("Descriptions")
    @Expose
    private List<Descriptions> descriptions = null;
    @SerializedName("Amenities")
    @Expose
    private List<String> amenities = null;
    @SerializedName("NearByPlaces")
    @Expose
    private List<NearByPlace> nearByPlaces = null;
    @SerializedName("CheckInTime")
    @Expose
    private String checkInTime;
    @SerializedName("CheckOutTime")
    @Expose
    private String checkOutTime;

    @SerializedName("RoomRating")
    @Expose
    private String RoomRating;

    @SerializedName("TotalReviewsCount")
    @Expose
    private String TotalReviewsCount;

    @SerializedName("Facilities")
    @Expose
    private List<Policy> Facilities = null;

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<Descriptions> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<Descriptions> descriptions) {
        this.descriptions = descriptions;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    public List<NearByPlace> getNearByPlaces() {
        return nearByPlaces;
    }

    public void setNearByPlaces(List<NearByPlace> nearByPlaces) {
        this.nearByPlaces = nearByPlaces;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getRoomRating() {
        return RoomRating;
    }

    public void setRoomRating(String roomRating) {
        RoomRating = roomRating;
    }

    public String getTotalReviewsCount() {
        return TotalReviewsCount;
    }

    public void setTotalReviewsCount(String totalReviewsCount) {
        TotalReviewsCount = totalReviewsCount;
    }

    public List<Policy> getFacilities() {
        return Facilities;
    }

    public void setFacilities(List<Policy> facilities) {
        Facilities = facilities;
    }
}
