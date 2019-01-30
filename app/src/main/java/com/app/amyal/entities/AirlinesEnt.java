package com.app.amyal.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AirlinesEnt {

    @SerializedName("AirlineID")
    @Expose
    private Integer airlineID;
    @SerializedName("IATA")
    @Expose
    private String iATA;
    @SerializedName("ICAO")
    @Expose
    private String iCAO;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Logo")
    @Expose
    private String logo;
    @SerializedName("Country")
    @Expose
    private String country;

    public Integer getAirlineID() {
        return airlineID;
    }

    public void setAirlineID(Integer airlineID) {
        this.airlineID = airlineID;
    }

    public String getIATA() {
        return iATA;
    }

    public void setIATA(String iATA) {
        this.iATA = iATA;
    }

    public String getICAO() {
        return iCAO;
    }

    public void setICAO(String iCAO) {
        this.iCAO = iCAO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}