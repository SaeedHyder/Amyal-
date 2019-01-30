package com.app.amyal.entities;

import java.util.List;

/**
 * Created by khan_muhammad on 11/27/2017.
 */

public class FlightEnt {

    private String flighLogo;
    private String flightName;
    private String ratting;
    private String flightDuration;
    private int stops;
    private String flightType;
    private double totalAmount;
    private List<FlightStop> stopList;
    private String currencyCode;

    public FlightEnt(String flighLogo,String flightName,String ratting,String flightDuration,int stops,String flightType,double totalAmount,List<FlightStop> stopList,String currencyCode){
        setFlighLogo(flighLogo);
        setFlightName(flightName);
        setRatting(ratting);
        setFlightDuration(flightDuration);
        setStops(stops);
        setFlightType(flightType);
        setTotalAmount(totalAmount);
        setStopList(stopList);
        setCurrencyCode(currencyCode);
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<FlightStop> getStopList() {
        return stopList;
    }

    public void setStopList(List<FlightStop> stopList) {
        this.stopList = stopList;
    }

    public String getFlighLogo() {
        return flighLogo;
    }

    public void setFlighLogo(String flighLogo) {
        this.flighLogo = flighLogo;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getRatting() {
        return ratting;
    }

    public void setRatting(String ratting) {
        this.ratting = ratting;
    }

    public String getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(String flightDuration) {
        this.flightDuration = flightDuration;
    }

    public int getStops() {
        return stops;
    }

    public void setStops(int stops) {
        this.stops = stops;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }
}
