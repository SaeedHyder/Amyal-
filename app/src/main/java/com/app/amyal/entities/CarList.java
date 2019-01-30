
package com.app.amyal.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarList {

    @SerializedName("CarCode")
    @Expose
    private String carCode;
    @SerializedName("RatePlanCode")
    @Expose
    private String ratePlanCode;
    @SerializedName("CarName")
    @Expose
    private String carName;
    @SerializedName("Descriptions")
    @Expose
    private String description;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("TotalAmount")
    @Expose
    private String totalAmount;
    @SerializedName("CurrencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("PickUpLocationCode")
    @Expose
    private String pickUpLocationCode;
    @SerializedName("DropOffLocationCode")
    @Expose
    private String dropOffLocationCode;
    @SerializedName("CarCategory")
    @Expose
    private String carCategory;
    @SerializedName("CarType")
    @Expose
    private String carType;
    @SerializedName("AirConditioned")
    @Expose
    private String airConditioned;
    @SerializedName("FuelType")
    @Expose
    private String fuelType;
    @SerializedName("Transmission")
    @Expose
    private String transmission;
    @SerializedName("Rating")
    @Expose
    private String rating;
    @SerializedName("TotalReviewCount")
    @Expose
    private String totalReviewCount;
    @SerializedName("TopReviews")
    @Expose
    private List<TopReview> topReviews = null;

    private FlightDetailsEnt carDetailsEnt;

    public FlightDetailsEnt getCarDetailsEnt() {
        return carDetailsEnt;
    }

    public void setCarDetailsEnt(FlightDetailsEnt carDetailsEnt) {
        this.carDetailsEnt = carDetailsEnt;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPickUpLocationCode() {
        return pickUpLocationCode;
    }

    public void setPickUpLocationCode(String pickUpLocationCode) {
        this.pickUpLocationCode = pickUpLocationCode;
    }

    public String getDropOffLocationCode() {
        return dropOffLocationCode;
    }

    public void setDropOffLocationCode(String dropOffLocationCode) {
        this.dropOffLocationCode = dropOffLocationCode;
    }

    public String getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(String carCategory) {
        this.carCategory = carCategory;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getAirConditioned() {
        return airConditioned;
    }

    public void setAirConditioned(String airConditioned) {
        this.airConditioned = airConditioned;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
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

}
