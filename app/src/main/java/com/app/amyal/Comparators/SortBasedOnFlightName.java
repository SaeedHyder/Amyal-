package com.app.amyal.Comparators;

import com.app.amyal.entities.FlightsList;

import java.util.Comparator;

/**
 * Created by waq on 1/30/2018.
 */

public class SortBasedOnFlightName implements Comparator<FlightsList> {

    public int compare(FlightsList o1, FlightsList o2)
    {
        return o1.getAirlineName().compareToIgnoreCase(o2.getAirlineName());
    }

}
