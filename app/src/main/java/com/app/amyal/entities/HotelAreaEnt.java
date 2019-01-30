
package com.app.amyal.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HotelAreaEnt {

    @SerializedName("ZoneCode")
    @Expose
    private int zoneCode;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("ParentID")
    @Expose
    private int parentID;
    @SerializedName("IATA")
    @Expose
    private String iATA;
    @SerializedName("ZoneType")
    @Expose
    private String zoneType;

    public int getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(int zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public String getIATA() {
        return iATA;
    }

    public void setIATA(String iATA) {
        this.iATA = iATA;
    }

    public String getZoneType() {
        return zoneType;
    }

    public void setZoneType(String zoneType) {
        this.zoneType = zoneType;
    }

}
