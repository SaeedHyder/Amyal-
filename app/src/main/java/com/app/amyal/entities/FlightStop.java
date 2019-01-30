
package com.app.amyal.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlightStop {

    @SerializedName("AirlineName")
    @Expose
    private String airlineName;
    @SerializedName("AirlineCode")
    @Expose
    private String airlineCode;
    @SerializedName("FlightNumber")
    @Expose
    private String flightNumber;
    @SerializedName("CabinType")
    @Expose
    private String cabinType;
    @SerializedName("ArrivalAirportCode")
    @Expose
    private String arrivalAirportCode;
    @SerializedName("DepartureAirportCode")
    @Expose
    private String departureAirportCode;
    @SerializedName("AirplanType")
    @Expose
    private String airplanType;
    @SerializedName("ArrivalDateTime")
    @Expose
    private String arrivalDateTime;
    @SerializedName("DepartureDateTime")
    @Expose
    private String departureDateTime;
    @SerializedName("Duration")
    @Expose
    private String duration;

    @SerializedName("DepartureLocation")
    @Expose
    private String DepartureLocation;

    @SerializedName("ArrivalLocation")
    @Expose
    private String ArrivalLocation;

    @SerializedName("GroundDuration")
    @Expose
    private String GroundDuration;

    public String getGroundDuration() {
        return GroundDuration;
    }

    public void setGroundDuration(String groundDuration) {
        GroundDuration = groundDuration;
    }

    public String getDepartureLocation() {
        return DepartureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        DepartureLocation = departureLocation;
    }

    public String getArrivalLocation() {
        return ArrivalLocation;
    }

    public void setArrivalLocation(String arrivalLocation) {
        ArrivalLocation = arrivalLocation;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

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

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}
