
package com.app.amyal.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RoomCombination {

    @SerializedName("AmountWithTax")
    @Expose
    private String amountWithTax;
    @SerializedName("CurrencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("RatePlanCode")
    @Expose
    private String ratePlanCode;
    @SerializedName("RoomType")
    @Expose
    private String roomType;
    @SerializedName("NumberOfUnits")
    @Expose
    private String numberOfUnits;
    @SerializedName("RoomExtras")
    @Expose
    private List<RoomExtra> roomExtras = null;

    @SerializedName("RoomOccupancy")
    @Expose
    private String RoomOccupancy;

    @SerializedName("Promotions")
    @Expose
    private ArrayList<Promotions> promotions;

    public ArrayList<Promotions> getPromotions() {
        return promotions;
    }

    public void setPromotions(ArrayList<Promotions> promotions) {
        this.promotions = promotions;
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

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setNumberOfUnits(String numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    public List<RoomExtra> getRoomExtras() {
        return roomExtras;
    }

    public void setRoomExtras(List<RoomExtra> roomExtras) {
        this.roomExtras = roomExtras;
    }

    public String getRoomOccupancy() {
        return RoomOccupancy;
    }

    public void setRoomOccupancy(String roomOccupancy) {
        RoomOccupancy = roomOccupancy;
    }
}
