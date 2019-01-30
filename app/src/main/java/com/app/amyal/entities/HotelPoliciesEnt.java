
package com.app.amyal.entities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HotelPoliciesEnt {

    @SerializedName("Policies")
    @Expose
    private List<Policy> policies = null;
    @SerializedName("AmountWithTax")
    @Expose
    private String amountWithTax;
    @SerializedName("CurrencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("RatePlanCode")
    @Expose
    private String ratePlanCode;
    @SerializedName("BookingCode")
    @Expose
    private String BookingCode;
    @SerializedName("BookingExpiry")
    @Expose
    private String BookingExpiry;

    public String getBookingExpiry() {
        return BookingExpiry;
    }

    public void setBookingExpiry(String bookingExpiry) {
        BookingExpiry = bookingExpiry;
    }

    public String getBookingCode() {
        return BookingCode;
    }

    public void setBookingCode(String bookingCode) {
        BookingCode = bookingCode;
    }

    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }

    public String getAmountWithTax() {
        return amountWithTax;
    }

    public void setAmountWithTax(String amountWithTax) {
        this.amountWithTax = amountWithTax;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }

}
