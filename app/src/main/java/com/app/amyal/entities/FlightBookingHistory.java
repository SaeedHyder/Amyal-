
package com.app.amyal.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlightBookingHistory {

    @SerializedName("Outbound")
    @Expose
    private Outbound outbound;
    @SerializedName("Inbound")
    @Expose
    private Inbound inbound;
    @SerializedName("TotalAmount")
    @Expose
    private String totalAmount;
    @SerializedName("IsReviewed")
    @Expose
    private String isReviewed;
    @SerializedName("ReservationID")
    @Expose
    private String reservationID;

    @SerializedName("CurrencyCode")
    @Expose
    private String CurrencyCode;

    @SerializedName("TotalDuration")
    @Expose
    private String TotalDuration;

    public String getTotalDuration() {
        return TotalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        TotalDuration = totalDuration;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public Outbound getOutbound() {
        return outbound;
    }

    public void setOutbound(Outbound outbound) {
        this.outbound = outbound;
    }

    public Inbound getInbound() {
        return inbound;
    }

    public void setInbound(Inbound inbound) {
        this.inbound = inbound;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getIsReviewed() {
        return isReviewed;
    }

    public void setIsReviewed(String isReviewed) {
        this.isReviewed = isReviewed;
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

}
