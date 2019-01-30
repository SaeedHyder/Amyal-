package com.app.amyal.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by waq on 2/6/2018.
 */

public class FlightDetailsEnt {

    @SerializedName("BookingCode")
    @Expose
    private String BookingCode;

    @SerializedName("ExpiryDate")
    @Expose
    private String ExpiryDate;

    @SerializedName("PickUpLocationDetails")
    @Expose
    private PickupDropLocDetails PickUpLocationDetails;

    @SerializedName("DropOffLocationDetails")
    @Expose
    private PickupDropLocDetails DropOffLocationDetails;


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

    public PickupDropLocDetails getPickUpLocationDetails() {
        return PickUpLocationDetails;
    }

    public void setPickUpLocationDetails(PickupDropLocDetails pickUpLocationDetails) {
        PickUpLocationDetails = pickUpLocationDetails;
    }

    public PickupDropLocDetails getDropOffLocationDetails() {
        return DropOffLocationDetails;
    }

    public void setDropOffLocationDetails(PickupDropLocDetails dropOffLocationDetails) {
        DropOffLocationDetails = dropOffLocationDetails;
    }
}
