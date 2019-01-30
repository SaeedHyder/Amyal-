package com.app.amyal.entities;

/**
 * Created by waq on 11/25/2017.
 */

public class FlightTypeEnt {

    int id;
    String FlightTypeImageSelected;
    String FlightTypeImageUnSelected;
    String FlightType;
    boolean isSelected;

    public FlightTypeEnt( int id, String FlightTypeImageSelected, String FlightTypeImageUnSelected, String FlightType, boolean isSelected){
        setId(id);
        setFlightType(FlightType);
        setFlightTypeImageSelected(FlightTypeImageSelected);
        setFlightTypeImageUnSelected(FlightTypeImageUnSelected);
        setSelected(isSelected);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlightTypeImageSelected() {
        return FlightTypeImageSelected;
    }

    public void setFlightTypeImageSelected(String flightTypeImageSelected) {
        FlightTypeImageSelected = flightTypeImageSelected;
    }

    public String getFlightTypeImageUnSelected() {
        return FlightTypeImageUnSelected;
    }

    public void setFlightTypeImageUnSelected(String flightTypeImageUnSelected) {
        FlightTypeImageUnSelected = flightTypeImageUnSelected;
    }

    public String getFlightType() {
        return FlightType;
    }

    public void setFlightType(String flightType) {
        FlightType = flightType;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
