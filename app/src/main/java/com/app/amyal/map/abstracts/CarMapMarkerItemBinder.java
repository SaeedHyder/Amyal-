package com.app.amyal.map.abstracts;

import com.app.amyal.R;
import com.app.amyal.activities.DockActivity;
import com.app.amyal.entities.HotelMapEnt;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by waq on 12/6/2017.
 */

public class CarMapMarkerItemBinder extends MapMarkerBinder<HotelMapEnt> {

    DockActivity dockActivity;

    public CarMapMarkerItemBinder( DockActivity dockActivity) {

        this.dockActivity = dockActivity;
    }

    @Override
    public void addMarker(final GoogleMap googleMap, final HotelMapEnt entity, final int position) {

        try {
            Marker marker=googleMap.addMarker(new MarkerOptions()
                    // .title(String.valueOf(position))
                    .position(new LatLng(Double.valueOf(entity.getLatitude()), Double.valueOf(entity.getLongitude())))
                    .title(entity.getPrice())
                    .snippet(entity.getPrice())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.carpin)));

            marker.setTag(entity.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
