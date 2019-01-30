package com.app.amyal.entities;

/**
 * Created by khan_muhammad on 11/28/2017.
 */

public class FlightStopEnt {

    String stopName;
    String time;
    String placeCode;
    String planeName;
    String planeCode;
    String flightType;
    String layOverTime;
    String departureLocation;
    String ArrivalDateTime;
    String DepartureDateTime;

    public FlightStopEnt(String stopName, String time, String placeCode, String planeName, String planeCode, String flightType, String layOverTime, String departureLocation) {

        setStopName(stopName);
        setTime(time);
        setPlaceCode(placeCode);
        setPlaneName(planeName);
        setPlaneCode(planeCode);
        setFlightType(flightType);
        setLayOverTime(layOverTime);
        setDepartureLocation(departureLocation);

    }

    public String getArrivalDateTime() {
        return ArrivalDateTime;
    }

    public void setArrivalDateTime(String arrivalDateTime) {
        ArrivalDateTime = arrivalDateTime;
    }

    public String getDepartureDateTime() {
        return DepartureDateTime;
    }

    public void setDepartureDateTime(String departureDateTime) {
        DepartureDateTime = departureDateTime;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getLayOverTime() {
        return layOverTime;
    }

    public void setLayOverTime(String layOverTime) {
        this.layOverTime = layOverTime;
    }

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeName) {
        this.placeCode = placeName;
    }

    public String getPlaneCode() {
        return planeCode;
    }

    public void setPlaneCode(String planeCode) {
        this.planeCode = planeCode;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlaneName() {
        return planeName;
    }

    public void setPlaneName(String planeName) {
        this.planeName = planeName;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }
}
