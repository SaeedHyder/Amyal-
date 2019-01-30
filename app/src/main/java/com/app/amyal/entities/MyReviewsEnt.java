package com.app.amyal.entities;

/**
 * Created by khan_muhammad on 11/23/2017.
 */

public class MyReviewsEnt {

    String companyName;
    float ratting;
    String review;
    String Date;

    public MyReviewsEnt(String companyName, float ratting, String review, String Date){
        setCompanyName(companyName);
        setRatting(ratting);
        setReview(review);
        setDate(Date);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public float getRatting() {
        return ratting;
    }

    public void setRatting(float ratting) {
        this.ratting = ratting;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
