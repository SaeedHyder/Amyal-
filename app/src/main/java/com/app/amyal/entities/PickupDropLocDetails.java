
package com.app.amyal.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PickupDropLocDetails {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longitude")
    @Expose
    private String longitude;
    @SerializedName("OperatingHoursStart")
    @Expose
    private String operatingHoursStart;
    @SerializedName("OperatingHoursEnd")
    @Expose
    private String operatingHoursEnd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getOperatingHoursStart() {
        return operatingHoursStart;
    }

    public void setOperatingHoursStart(String operatingHoursStart) {
        this.operatingHoursStart = operatingHoursStart;
    }

    public String getOperatingHoursEnd() {
        return operatingHoursEnd;
    }

    public void setOperatingHoursEnd(String operatingHoursEnd) {
        this.operatingHoursEnd = operatingHoursEnd;
    }

}
