
package com.app.amyal.entities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Inbound {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Duration")
    @Expose
    private String totalDuration;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Rating")
    @Expose
    private String rating;
    @SerializedName("ReviewsCount")
    @Expose
    private String reviewsCount;
    @SerializedName("ReferenceID")
    @Expose
    private String referenceID;
    @SerializedName("Source")
    @Expose
    private String source;
    @SerializedName("Destination")
    @Expose
    private String destination;
    @SerializedName("ThumbImage")
    @Expose
    private String thumbImage;
    @SerializedName("Details")
    @Expose
    private List<Detail> details = null;

    @SerializedName("DestinationCode")
    @Expose
    private String DestinationCode;

    @SerializedName("SourceCode")
    @Expose
    private String SourceCode;

    public String getDestinationCode() {
        return DestinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        DestinationCode = destinationCode;
    }

    public String getSourceCode() {
        return SourceCode;
    }

    public void setSourceCode(String sourceCode) {
        SourceCode = sourceCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(String reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public String getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(String referenceID) {
        this.referenceID = referenceID;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

}
