
package com.app.amyal.entities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HotelListModel {

    @SerializedName("HotelName")
    @Expose
    private String hotelName;
    @SerializedName("HotelCode")
    @Expose
    private String hotelCode;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longitude")
    @Expose
    private String longitude;
    @SerializedName("Zone")
    @Expose
    private String zone;
    @SerializedName("Thumb")
    @Expose
    private String thumb;
    @SerializedName("Descriptions")
    @Expose
    private String description;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("CategoryCode")
    @Expose
    private String categoryCode;
    @SerializedName("HotelPriceMin")
    @Expose
    private double hotelPriceMin;
    @SerializedName("HotelPriceMax")
    @Expose
    private double hotelPriceMax;
    @SerializedName("CurrencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("Rating")
    @Expose
    private String rating;
    @SerializedName("TotalReviewCount")
    @Expose
    private String totalReviewCount;
    @SerializedName("TopReviews")
    @Expose
    private List<TopReview> topReviews = null;
    @SerializedName("RoomCombinations")
    @Expose
    private List<RoomCombination> roomCombinations = null;

    private HotelGalleryEnt hotelGalleryEnt = null;

    private HotelGalleryEnt roomGalleryEnt = null;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public double getHotelPriceMin() {
        return hotelPriceMin;
    }

    public void setHotelPriceMin(double hotelPriceMin) {
        this.hotelPriceMin = hotelPriceMin;
    }

    public double getHotelPriceMax() {
        return hotelPriceMax;
    }

    public void setHotelPriceMax(double hotelPriceMax) {
        this.hotelPriceMax = hotelPriceMax;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTotalReviewCount() {
        return totalReviewCount;
    }

    public void setTotalReviewCount(String totalReviewCount) {
        this.totalReviewCount = totalReviewCount;
    }

    public List<TopReview> getTopReviews() {
        return topReviews;
    }

    public void setTopReviews(List<TopReview> topReviews) {
        this.topReviews = topReviews;
    }

    public List<RoomCombination> getRoomCombinations() {
        return roomCombinations;
    }

    public void setRoomCombinations(List<RoomCombination> roomCombinations) {
        this.roomCombinations = roomCombinations;
    }

    public HotelGalleryEnt getHotelGalleryEnt() {
        return hotelGalleryEnt;
    }

    public void setHotelGalleryEnt(HotelGalleryEnt hotelGalleryEnt) {
        this.hotelGalleryEnt = hotelGalleryEnt;
    }

    public HotelGalleryEnt getRoomGalleryEnt() {
        return roomGalleryEnt;
    }

    public void setRoomGalleryEnt(HotelGalleryEnt roomGalleryEnt) {
        this.roomGalleryEnt = roomGalleryEnt;
    }

}
