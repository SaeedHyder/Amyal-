package com.app.amyal.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopReview {

    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("UserImage")
    @Expose
    private String userImage;
    @SerializedName("Review")
    @Expose
    private String review;
    @SerializedName("Rating")
    @Expose
    private String rating;
    @SerializedName("Date")
    @Expose
    private String date;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}