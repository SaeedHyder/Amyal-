package com.app.amyal.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.app.amyal.R;
import com.app.amyal.entities.BookingDetailEnt;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.ui.viewbinders.abstracts.ViewBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by khan_muhammad on 12/4/2017.
 */

public class BinderHotelPolicyFacility extends ViewBinder<BookingDetailEnt> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;

    public BinderHotelPolicyFacility(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.fragment_policy_facility_item);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new BinderHotelPolicyFacility.ViewHolder(view);
    }

    @Override
    public void bindView(BookingDetailEnt entity, int position, int grpPosition, View view, Activity activity) {

        BinderHotelPolicyFacility.ViewHolder viewHolder = (BinderHotelPolicyFacility.ViewHolder) view.getTag();

        String Value = entity.getValue();
        if (Value.contains("*")) {
            Value = Value.replace("*", "\n*");
            if (Value.contains("\n"))
                Value = Value.replaceFirst("\n", "");
        }

        viewHolder.tvTitle.setText(entity.getName());
        viewHolder.tvDesc.setText(Value);
    }

    public static class ViewHolder extends BaseViewHolder {

        AnyTextView tvTitle;
        AnyTextView tvDesc;


        public ViewHolder(View view) {

            tvTitle = (AnyTextView) view.findViewById(R.id.tvTitle);
            tvDesc = (AnyTextView) view.findViewById(R.id.tvDesc);

        }
    }
}
