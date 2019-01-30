
package com.app.amyal.entities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlightListEnt {

    @SerializedName("SequenceNumber")
    @Expose
    private String sequenceNumber;
    @SerializedName("Results")
    @Expose
    private List<FlightsList> flightsList = null;

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public List<FlightsList> getFlightsList() {
        return flightsList;
    }

    public void setFlightsList(List<FlightsList> flightsList) {
        this.flightsList = flightsList;
    }


}
