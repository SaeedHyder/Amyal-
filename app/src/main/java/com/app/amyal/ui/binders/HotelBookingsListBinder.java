package com.app.amyal.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.amyal.R;
import com.app.amyal.entities.HotelBookingEnt;
import com.app.amyal.global.Utils;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.ui.viewbinders.abstracts.ViewBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.AnyTextView1;
import com.app.amyal.ui.views.CustomRatingBar;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HotelBookingsListBinder extends ViewBinder<HotelBookingEnt> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;

    public HotelBookingsListBinder(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.item_fragment_select_hotels);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new HotelBookingsListBinder.ViewHolder(view);
    }

    @Override
    public void bindView(HotelBookingEnt entity, int position, int grpPosition, View view, Activity activity) {

        HotelBookingsListBinder.ViewHolder viewHolder = (HotelBookingsListBinder.ViewHolder) view.getTag();

        imageLoader.displayImage(entity.getThumbImage(), viewHolder.ivHotel);

        viewHolder.tvHotelName.setSelected(true);
        viewHolder.tvHotelName.setText(entity.getName());

        viewHolder.tvLocationName.setText(entity.getCategory());
        viewHolder.tvReviews.setText(entity.getCurrencyCode());

        viewHolder.tvPrice.setText(entity.getCurrencyCode() + " " + entity.getAmount());

        if (entity.getCategory().length() > 0 && !entity.getCategory().contains("false"))
            viewHolder.CrRating.setScore(Integer.parseInt(entity.getCategory()));

        if (entity.getHotelReviewsCount() != null && entity.getHotelReviewsCount().length() > 0 && Integer.parseInt(entity.getHotelReviewsCount()) >= 10) {
            viewHolder.llTextRatting.setVisibility(View.VISIBLE);
            String ratting = Utils.getTextRatting(Float.parseFloat(entity.getHotelRating()));
            if (ratting.length() > 0) {
                viewHolder.tvRatting.setText(entity.getHotelRating() + " ");
                viewHolder.tvReviews.setText(ratting);
            }
        } else {
            viewHolder.llTextRatting.setVisibility(View.GONE);
        }

    }

    public static class ViewHolder extends BaseViewHolder {

        ImageView ivHotel;
        AnyTextView1 tvHotelName;
        AnyTextView tvLocationName;
        AnyTextView tvReviews;
        AnyTextView tvPrice;
        AnyTextView tvRatting;
        CustomRatingBar CrRating;
        LinearLayout llTextRatting;

        public ViewHolder(View view) {

            ivHotel = (ImageView) view.findViewById(R.id.ivHotel);
            tvHotelName = (AnyTextView1) view.findViewById(R.id.tvHotelName);
            tvLocationName = (AnyTextView) view.findViewById(R.id.tvLocationName);
            tvReviews = (AnyTextView) view.findViewById(R.id.tvReviews);
            tvPrice = (AnyTextView) view.findViewById(R.id.tvPrice);
            tvRatting = (AnyTextView) view.findViewById(R.id.tvRatting);
            CrRating = (CustomRatingBar) view.findViewById(R.id.CrRating);
            llTextRatting = (LinearLayout) view.findViewById(R.id.llTextRatting);
        }
    }
}



