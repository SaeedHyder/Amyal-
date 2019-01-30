package com.app.amyal.Comparators;

import com.app.amyal.entities.HotelListModel;

import java.util.Comparator;

public class SortBasedOnHotelCategory implements Comparator<HotelListModel> {

    public int compare(HotelListModel o1, HotelListModel o2)
    {
        return o1.getCategoryCode().compareToIgnoreCase(o2.getCategoryCode());
    }

}
