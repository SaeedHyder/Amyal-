package com.app.amyal.interfaces;

import com.app.amyal.entities.FlightTypeEnt;

/**
 * Created by lenovo on 2/23/2016.
 */
public interface ItemClickListener {
    void itemClicked(Object object, int position);
    void itemClicked(Object object,boolean isfrom,int guestNo);
}
