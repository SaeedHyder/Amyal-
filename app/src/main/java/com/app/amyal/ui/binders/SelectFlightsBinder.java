package com.app.amyal.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.app.amyal.R;
import com.app.amyal.entities.FlightEnt;
import com.app.amyal.global.AppConstants;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.ui.viewbinders.abstracts.ViewBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.CustomRatingBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Locale;

/**
 * Created by waq on 11/27/2017.
 */

public class SelectFlightsBinder extends ViewBinder<FlightEnt> {

    private final double curDiff;
    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;
    private String curCode;

    public SelectFlightsBinder(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.fragment_select_flights_item);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
        curCode = preferenceHelper.getUser().getUser().getCurrencyCode();
        curDiff = preferenceHelper.getCurrency().getSAR();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new SelectFlightsBinder.ViewHolder(view);
    }

    @Override
    public void bindView(FlightEnt entity, int position, int grpPosition, View view, Activity activity) {

        SelectFlightsBinder.ViewHolder viewHolder = (SelectFlightsBinder.ViewHolder) view.getTag();

        if (entity.getFlighLogo() != null && entity.getFlighLogo().length() > 0) {
            imageLoader.displayImage(entity.getFlighLogo(), viewHolder.ivFlight);
        }

        viewHolder.tvAirlineName.setText(entity.getFlightName());

        String numberOfStops = AppConstants.Nonstop;
        if (entity.getStops() > 0) {
            numberOfStops = entity.getStops() > 1 ? entity.getStops() + " Stops" : entity.getStops() + " Stop";
        }

        String val = String.format(Locale.US, "%s (%s)", entity.getFlightDuration(), numberOfStops);
        viewHolder.tvFlightDuraion.setText(val);

        viewHolder.tvFlightType.setText(entity.getFlightType());

        if (entity.getRatting() != null && entity.getRatting().length() > 0) {
            viewHolder.CrRating.setScore(Float.parseFloat(entity.getRatting()));
        } else {
            viewHolder.CrRating.setVisibility(View.GONE);
        }

        if (!curCode.equals(entity.getCurrencyCode()))
            viewHolder.tvPrice.setText(curCode + " " + (curDiff * entity.getTotalAmount()));
        else
            viewHolder.tvPrice.setText(entity.getCurrencyCode() + " " + entity.getTotalAmount());

        if (entity.getStopList() != null && entity.getStopList().size() > 0) {

            if (entity.getStops() == 0) {

                viewHolder.tvStop1.setText(entity.getStopList().get(0).getDepartureAirportCode());
                viewHolder.tvStop2.setText(entity.getStopList().get(0).getArrivalAirportCode());
                viewHolder.tvStop3.setVisibility(View.GONE);

            } else if (entity.getStops() == 1) {
                viewHolder.tvStop3.setVisibility(View.VISIBLE);
                viewHolder.tvStop1.setText(entity.getStopList().get(0).getDepartureAirportCode());
                viewHolder.tvStop2.setText(entity.getStopList().get(0).getArrivalAirportCode());
                if (entity.getStopList().size() > 1)
                    viewHolder.tvStop3.setText(entity.getStopList().get(1).getArrivalAirportCode());
            } else if (entity.getStops() > 1) {
                viewHolder.tvStop3.setVisibility(View.VISIBLE);
                viewHolder.tvStop1.setText(entity.getStopList().get(0).getDepartureAirportCode());
                viewHolder.tvStop2.setText(entity.getStopList().get(0).getArrivalAirportCode());
                if (entity.getStopList().size() > 1)
                    viewHolder.tvStop3.setText(entity.getStopList().get(1).getArrivalAirportCode() + " ...");
            }

        }

        /*if(entity.getStopList()!= null && entity.getStopList().size() > 0){

            if(entity.getStops() == 0){

                viewHolder.tvStop1.setText(entity.getStopList().get(0).getAirportCode());
                viewHolder.tvStop2.setText(entity.getStopList().get(1).getAirportCode());
                viewHolder.tvStop3.setVisibility(View.GONE);

            }
            else  if(entity.getStops() == 1){
                viewHolder.tvStop3.setVisibility(View.VISIBLE);
                viewHolder.tvStop1.setText(entity.getStopList().get(0).getAirportCode());
                viewHolder.tvStop2.setText(entity.getStopList().get(1).getAirportCode());
                viewHolder.tvStop3.setText(entity.getStopList().get(2).getAirportCode());
            }
            else  if(entity.getStops() > 1){
                viewHolder.tvStop3.setVisibility(View.VISIBLE);
                viewHolder.tvStop1.setText(entity.getStopList().get(0).getAirportCode());
                viewHolder.tvStop2.setText(entity.getStopList().get(1).getAirportCode());
                viewHolder.tvStop3.setText(entity.getStopList().get(2).getAirportCode() + " ...");
            }

        }*/

    }

    public static class ViewHolder extends BaseViewHolder {

        ImageView ivFlight;
        AnyTextView tvAirlineName;
        AnyTextView tvStop1;
        AnyTextView tvStop2;
        AnyTextView tvStop3;
        AnyTextView tvFlightDuraion;
        AnyTextView tvFlightType;
        CustomRatingBar CrRating;
        AnyTextView tvPrice;


        public ViewHolder(View view) {

            ivFlight = (ImageView) view.findViewById(R.id.ivFlight);
            tvStop1 = (AnyTextView) view.findViewById(R.id.tvStop1);
            tvStop2 = (AnyTextView) view.findViewById(R.id.tvStop2);
            tvStop3 = (AnyTextView) view.findViewById(R.id.tvStop3);
            tvAirlineName = (AnyTextView) view.findViewById(R.id.tvAirlineName);
            tvFlightDuraion = (AnyTextView) view.findViewById(R.id.tvFlightDuraion);
            tvFlightType = (AnyTextView) view.findViewById(R.id.tvFlightType);
            CrRating = (CustomRatingBar) view.findViewById(R.id.CrRating);
            tvPrice = (AnyTextView) view.findViewById(R.id.tvPrice);
        }
    }
}
