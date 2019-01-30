package com.app.amyal.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.app.amyal.R;
import com.app.amyal.entities.FlightStopEnt;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.ui.viewbinders.abstracts.ViewBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.AnyTextView1;
import com.nostra13.universalimageloader.core.ImageLoader;

public class BinderFlightStop extends ViewBinder<FlightStopEnt> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;

    public BinderFlightStop(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.flight_stop_item);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new BinderFlightStop.ViewHolder(view);
    }

    @Override
    public void bindView(FlightStopEnt entity, int position, int grpPosition, View view, Activity activity) {

        BinderFlightStop.ViewHolder viewHolder = (BinderFlightStop.ViewHolder) view.getTag();

        /*viewHolder.tv_date.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMDHMS, AppConstants.DateFormat_DMY2,
                entity.getCreatedAt()));*/

        viewHolder.tvStopName.setSelected(true);
        viewHolder.tvStopName.setText(entity.getStopName());

        viewHolder.tvTime.setText(entity.getTime());
        viewHolder.tvPlaneName.setText("Flight No : " + entity.getPlaneCode());
        viewHolder.tvFlightType.setText(entity.getFlightType());

        if(entity.getLayOverTime()!= null && entity.getLayOverTime().length() >0) {
            viewHolder.tvLayover.setVisibility(View.VISIBLE);
            viewHolder.tvLayover.setText("Layover in " + entity.getDepartureLocation() + " \n" + entity.getLayOverTime());
        }else{
            viewHolder.tvLayover.setVisibility(View.GONE);
        }

    }

    public static class ViewHolder extends BaseViewHolder {

        AnyTextView1 tvStopName;
        AnyTextView tvTime;
        AnyTextView tvPlaneName;
        AnyTextView tvFlightType;
        AnyTextView tvLayover;


        public ViewHolder(View view) {

            tvStopName = (AnyTextView1) view.findViewById(R.id.tvStopName);
            tvTime = (AnyTextView) view.findViewById(R.id.tvTime);
            tvPlaneName = (AnyTextView) view.findViewById(R.id.tvPlaneName);
            tvFlightType = (AnyTextView) view.findViewById(R.id.tvFlightType);
            tvLayover = (AnyTextView) view.findViewById(R.id.tvLayover);
        }
    }
}