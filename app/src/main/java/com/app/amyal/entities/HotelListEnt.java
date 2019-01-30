package com.app.amyal.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HotelListEnt {

    @SerializedName("SequenceNumber")
    @Expose
    private String sequenceNumber;
    @SerializedName("TotalCount")
    @Expose
    private String TotalCount;

    @SerializedName("responseModel")
    @Expose
    private ArrayList<HotelListModel> responseModel = new ArrayList<>();

    public ArrayList<HotelListModel> getResponseModel() {
        return responseModel;
    }

    public void setResponseModel(ArrayList<HotelListModel> responseModel) {
        this.responseModel = responseModel;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }




    public String getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(String totalCount) {
        TotalCount = totalCount;
    }
}
