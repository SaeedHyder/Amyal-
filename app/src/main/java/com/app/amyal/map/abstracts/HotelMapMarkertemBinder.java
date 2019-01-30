package com.app.amyal.map.abstracts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.amyal.R;
import com.app.amyal.activities.DockActivity;
import com.app.amyal.entities.HotelListModel;
import com.app.amyal.entities.HotelMapEnt;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class HotelMapMarkertemBinder extends MapMarkerBinder<HotelListModel> {

    DockActivity dockActivity;

    public HotelMapMarkertemBinder( DockActivity dockActivity) {

        this.dockActivity = dockActivity;
    }

    @Override
    public void addMarker(final GoogleMap googleMap, final HotelListModel entity, final int position) {

        try {
            Marker marker=googleMap.addMarker(new MarkerOptions()
                    // .title(String.valueOf(position))
                    .position(new LatLng(Double.valueOf(entity.getLatitude()), Double.valueOf(entity.getLongitude())))
                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.hotelpin,entity.getCurrencyCode() + " " +entity.getHotelPriceMin() + " - " + entity.getHotelPriceMax())))); //BitmapDescriptorFactory.fromResource(R.drawable.hotelpin) //getMarkerBitmapFromView(R.drawable.hotelpin,entity.getCategoryCode() + " " +entity.getHotelPriceMin() + " " + entity.getHotelPriceMax()))

            marker.setTag(entity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getMarkerBitmapFromView(int resId,String value) {

        View customMarkerView = ((LayoutInflater) dockActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.img_icon);
        TextView textView = (TextView) customMarkerView.findViewById(R.id.txt_pick_text);
        textView.setText(value);
        //textView.setTextColor(getResources().getColor(R.color.black));
        //textView.setBackgroundColor(getResources().getColor(R.color.white));
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

}
