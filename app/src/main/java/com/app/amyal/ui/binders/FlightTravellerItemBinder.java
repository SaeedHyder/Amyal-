package com.app.amyal.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.app.amyal.R;
import com.app.amyal.entities.FlightTravellerEnt;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.ui.viewbinders.abstracts.ViewBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.AnyTextView1;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by khan_muhammad on 2/6/2018.
 */

public class FlightTravellerItemBinder extends ViewBinder<FlightTravellerEnt> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;

    public FlightTravellerItemBinder(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.flight_traveller_item);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new FlightTravellerItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(FlightTravellerEnt entity, int position, int grpPosition, View view, Activity activity) {

        FlightTravellerItemBinder.ViewHolder viewHolder = (FlightTravellerItemBinder.ViewHolder) view.getTag();

        viewHolder.tvTraveller.setText(entity.getTravellerNo());
        viewHolder.tvTravellerName.setText(entity.getName());

    }

    public static class ViewHolder extends BaseViewHolder {

        AnyTextView tvTraveller;
        AnyTextView1 tvTravellerName;


        public ViewHolder(View view) {

            tvTraveller = (AnyTextView) view.findViewById(R.id.tvTraveller);
            tvTravellerName = (AnyTextView1) view.findViewById(R.id.tvTravellerName);

        }
    }
}
