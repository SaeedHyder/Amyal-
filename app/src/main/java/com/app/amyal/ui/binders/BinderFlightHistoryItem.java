package com.app.amyal.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.app.amyal.R;
import com.app.amyal.entities.FlightBookingHistory;
import com.app.amyal.entities.FlightHistoryEnt;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.ui.viewbinders.abstracts.ViewBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.CustomRatingBar;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by khan_muhammad on 11/29/2017.
 */

public class BinderFlightHistoryItem extends ViewBinder<FlightBookingHistory> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;

    public BinderFlightHistoryItem(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.fragment_flight_history_item);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new BinderFlightHistoryItem.ViewHolder(view);
    }

    @Override
    public void bindView(FlightBookingHistory entity, int position, int grpPosition, View view, Activity activity) {

        BinderFlightHistoryItem.ViewHolder viewHolder = (BinderFlightHistoryItem.ViewHolder) view.getTag();

        if(entity.getInbound()!= null && entity.getInbound().getName()!= null) {
            imageLoader.displayImage("drawable://" + R.drawable.round, viewHolder.ivFlightType);
            viewHolder.tvEndDate.setText("- "+entity.getInbound().getDate());
        }
        else {
            imageLoader.displayImage("drawable://" + R.drawable.oneway, viewHolder.ivFlightType);
            viewHolder.tvEndDate.setVisibility(View.GONE);
        }

        viewHolder.tvTripName.setText(entity.getOutbound().getSourceCode() + " to " +  entity.getOutbound().getDestinationCode());
        viewHolder.tvStartDate.setText(entity.getOutbound().getDate());

        viewHolder.tvTotalPrice.setText(entity.getCurrencyCode() + " " + entity.getTotalAmount());
        viewHolder.tvFlightDuraion.setText(entity.getTotalDuration());

    }

    public static class ViewHolder extends BaseViewHolder {

        ImageView ivFlightType;
        AnyTextView tvTripName;
        AnyTextView tvStartDate;
        AnyTextView tvEndDate;
        AnyTextView tvTotalPrice;
        AnyTextView tvFlightDuraion;

        public ViewHolder(View view) {

            ivFlightType = (ImageView) view.findViewById(R.id.ivFlightType);
            tvTripName = (AnyTextView) view.findViewById(R.id.tvTripName);
            tvStartDate = (AnyTextView) view.findViewById(R.id.tvStartDate);
            tvEndDate = (AnyTextView) view.findViewById(R.id.tvEndDate);
            tvTotalPrice = (AnyTextView) view.findViewById(R.id.tvTotalPrice);
            tvFlightDuraion = (AnyTextView) view.findViewById(R.id.tvFlightDuraion);
        }
    }
}
