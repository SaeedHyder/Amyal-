package com.app.amyal.Comparators;

import com.app.amyal.entities.HotelListModel;

import java.util.Comparator;

public class SortBasedOnHotelName implements Comparator<HotelListModel> {

    public int compare(HotelListModel o1, HotelListModel o2)
    {
        return o1.getHotelName().compareToIgnoreCase(o2.getHotelName());
    }

}
