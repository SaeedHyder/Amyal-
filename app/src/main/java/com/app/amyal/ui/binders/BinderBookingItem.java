package com.app.amyal.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.app.amyal.R;
import com.app.amyal.entities.BookingDetailEnt;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.ui.viewbinders.abstracts.ViewBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.AnyTextView1;
import com.app.amyal.ui.views.CustomRatingBar;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by waq on 11/29/2017.
 */

public class BinderBookingItem extends ViewBinder<BookingDetailEnt> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;

    public BinderBookingItem(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.booking_history_item);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new BinderBookingItem.ViewHolder(view);
    }

    @Override
    public void bindView(BookingDetailEnt entity, int position, int grpPosition, View view, Activity activity) {

        BinderBookingItem.ViewHolder viewHolder = (BinderBookingItem.ViewHolder) view.getTag();

        viewHolder.tvTitle.setSelected(true);
        viewHolder.tvTitleValue.setSelected(true);
        viewHolder.tvTitle.setText(entity.getName());
        viewHolder.tvTitleValue.setText(entity.getValue());

    }

    public static class ViewHolder extends BaseViewHolder {

        AnyTextView1 tvTitle;
        AnyTextView1 tvTitleValue;


        public ViewHolder(View view) {

            tvTitle = (AnyTextView1) view.findViewById(R.id.tvTitle);
            tvTitleValue = (AnyTextView1) view.findViewById(R.id.tvTitleValue);

        }
    }
}
