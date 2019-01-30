package com.app.amyal.entities;

/**
 * Created by khan_muhammad on 11/29/2017.
 */

public class FlightHistoryEnt {

    String tripName;
    String totalDuration;
    String totalPrize;
    String startDate;
    String endDate;
    boolean isRoundTrip;

    public FlightHistoryEnt(String tripName,String totalDuration,String totalPrize,String startDate,String endDate,boolean isRoundTrip){
        setTripName(tripName);
        setTotalDuration(totalDuration);
        setTotalPrize(totalPrize);
        setStartDate(startDate);
        setEndDate(endDate);
        setRoundTrip(isRoundTrip);
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getTotalPrize() {
        return totalPrize;
    }

    public void setTotalPrize(String totalPrize) {
        this.totalPrize = totalPrize;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isRoundTrip() {
        return isRoundTrip;
    }

    public void setRoundTrip(boolean roundTrip) {
        isRoundTrip = roundTrip;
    }
}
