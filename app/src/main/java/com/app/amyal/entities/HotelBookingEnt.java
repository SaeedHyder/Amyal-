
package com.app.amyal.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HotelBookingEnt {

    @SerializedName("ReservationID")
    @Expose
    private String reservationID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("DetailName")
    @Expose
    private String detailName;
    @SerializedName("ReferenceID")
    @Expose
    private String referenceID;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("RoomRating")
    @Expose
    private String roomRating;
    @SerializedName("RoomReviewsCount")
    @Expose
    private String roomReviewsCount;
    @SerializedName("HotelRating")
    @Expose
    private String hotelRating;
    @SerializedName("HotelReviewsCount")
    @Expose
    private String hotelReviewsCount;
    @SerializedName("Amount")
    @Expose
    private double amount;
    @SerializedName("CurrencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("BookingStatus")
    @Expose
    private String bookingStatus;
    @SerializedName("ThumbImage")
    @Expose
    private String thumbImage;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("TravellingFor")
    @Expose
    private String travellingFor;
    @SerializedName("IsReviewed")
    @Expose
    private String isReviewed;

    @SerializedName("NoOfDays")
    @Expose
    private String NoOfDays;

    @SerializedName("Details")
    @Expose
    private List<Detail> details = null;

    public String getNoOfDays() {
        return NoOfDays;
    }

    public void setNoOfDays(String noOfDays) {
        NoOfDays = noOfDays;
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public String getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(String referenceID) {
        this.referenceID = referenceID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRoomRating() {
        return roomRating;
    }

    public void setRoomRating(String roomRating) {
        this.roomRating = roomRating;
    }

    public String getRoomReviewsCount() {
        return roomReviewsCount;
    }

    public void setRoomReviewsCount(String roomReviewsCount) {
        this.roomReviewsCount = roomReviewsCount;
    }

    public String getHotelRating() {
        return hotelRating;
    }

    public void setHotelRating(String hotelRating) {
        this.hotelRating = hotelRating;
    }

    public String getHotelReviewsCount() {
        return hotelReviewsCount;
    }

    public void setHotelReviewsCount(String hotelReviewsCount) {
        this.hotelReviewsCount = hotelReviewsCount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTravellingFor() {
        return travellingFor;
    }

    public void setTravellingFor(String travellingFor) {
        this.travellingFor = travellingFor;
    }

    public String getIsReviewed() {
        return isReviewed;
    }

    public void setIsReviewed(String isReviewed) {
        this.isReviewed = isReviewed;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

}
