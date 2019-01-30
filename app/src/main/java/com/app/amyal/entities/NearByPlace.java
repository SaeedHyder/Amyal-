
package com.app.amyal.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NearByPlace {

    @SerializedName("Place")
    @Expose
    private String place;
    @SerializedName("Distance")
    @Expose
    private String distance;
    @SerializedName("DistanceUnit")
    @Expose
    private String distanceUnit;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

}
