
package com.app.amyal.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReturnStop {

    @SerializedName("FlightNumber")
    @Expose
    private String flightNumber;
    @SerializedName("CabinType")
    @Expose
    private String cabinType;
    @SerializedName("Airport")
    @Expose
    private String airport;
    @SerializedName("AirportCode")
    @Expose
    private String airportCode;
    @SerializedName("AirplanType")
    @Expose
    private String airplanType;
    @SerializedName("ArrivalDateTime")
    @Expose
    private String arrivalDateTime;
    @SerializedName("DepartureDateTime")
    @Expose
    private String departureDateTime;

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getCabinType() {
        return cabinType;
    }

    public void setCabinType(String cabinType) {
        this.cabinType = cabinType;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirplanType() {
        return airplanType;
    }

    public void setAirplanType(String airplanType) {
        this.airplanType = airplanType;
    }

    public String getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(String arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public String getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(String departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

}
