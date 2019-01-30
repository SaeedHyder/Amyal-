
package com.app.amyal.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlightsList {

    @SerializedName("AirlineName")
    @Expose
    private String airlineName;
    @SerializedName("TotalDuration")
    @Expose
    private String totalDuration;
    @SerializedName("RatePlanCode")
    @Expose
    private String ratePlanCode;
    @SerializedName("ReturnRatePlanCode")
    @Expose
    private String returnRatePlanCode;
    @SerializedName("Direction")
    @Expose
    private String direction;
    @SerializedName("Thumb")
    @Expose
    private String thumb;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Amount")
    @Expose
    private double amount;
    @SerializedName("CurrencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("NoOfStops")
    @Expose
    private String noOfStops;
    @SerializedName("ReturnNoOfStops")
    @Expose
    private String returnNoOfStops;
    @SerializedName("FlightStop")
    @Expose
    private List<FlightStop> flightStop = null;
    @SerializedName("ReturnFlightStop")
    @Expose
    private List<ReturnFlightStop> returnFlightStop = null;
    @SerializedName("Stops")
    @Expose
    private List<Stop> stops = null;
    @SerializedName("ReturnStops")
    @Expose
    private List<ReturnStop> returnStops = null;
    @SerializedName("Rating")
    @Expose
    private String rating;

    @SerializedName("TotalReviewCount")
    @Expose
    private String totalReviewCount;

    @SerializedName("CabinType")
    @Expose
    private String CabinType;

    private String BookingCode;
    private String ExpiryDate;
    private String OutboundDuration;
    private String InboundDuration;

    public String getCabinType() {
        return CabinType;
    }

    public void setCabinType(String cabinType) {
        CabinType = cabinType;
    }

    public String getOutboundDuration() {
        return OutboundDuration;
    }

    public void setOutboundDuration(String outboundDuration) {
        OutboundDuration = outboundDuration;
    }

    public String getInboundDuration() {
        return InboundDuration;
    }

    public void setInboundDuration(String inboundDuration) {
        InboundDuration = inboundDuration;
    }

    public String getBookingCode() {
        return BookingCode;
    }

    public void setBookingCode(String bookingCode) {
        BookingCode = bookingCode;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }

    public String getReturnRatePlanCode() {
        return returnRatePlanCode;
    }

    public void setReturnRatePlanCode(String returnRatePlanCode) {
        this.returnRatePlanCode = returnRatePlanCode;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getNoOfStops() {
        return noOfStops;
    }

    public void setNoOfStops(String noOfStops) {
        this.noOfStops = noOfStops;
    }

    public String getReturnNoOfStops() {
        return returnNoOfStops;
    }

    public void setReturnNoOfStops(String returnNoOfStops) {
        this.returnNoOfStops = returnNoOfStops;
    }

    public List<FlightStop> getFlightStop() {
        return flightStop;
    }

    public void setFlightStop(List<FlightStop> flightStop) {
        this.flightStop = flightStop;
    }

    public List<ReturnFlightStop> getReturnFlightStop() {
        return returnFlightStop;
    }

    public void setReturnFlightStop(List<ReturnFlightStop> returnFlightStop) {
        this.returnFlightStop = returnFlightStop;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public List<ReturnStop> getReturnStops() {
        return returnStops;
    }

    public void setReturnStops(List<ReturnStop> returnStops) {
        this.returnStops = returnStops;
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

}
