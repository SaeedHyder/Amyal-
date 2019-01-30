
package com.app.amyal.entities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReservationEnt {

    @SerializedName("list")
    @Expose
    private List<Detail> details = null;

    @SerializedName("returnList")
    @Expose
    private List<Detail> return_details = null;

    @SerializedName("BillAmount")
    @Expose
    private float billAmount;
    @SerializedName("CurrencyCode")
    @Expose
    private String currencyCode;

    public List<Detail> getReturn_details() {
        return return_details;
    }

    public void setReturn_details(List<Detail> return_details) {
        this.return_details = return_details;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public float getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(float billAmount) {
        this.billAmount = billAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

}
