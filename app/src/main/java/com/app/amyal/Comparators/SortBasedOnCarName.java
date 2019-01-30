package com.app.amyal.Comparators;


import com.app.amyal.entities.CarList;

import java.util.Comparator;

public class SortBasedOnCarName implements Comparator<CarList> {

    public int compare(CarList o1, CarList o2)
    {
        return o1.getCarName().compareToIgnoreCase(o2.getCarName());
    }

}