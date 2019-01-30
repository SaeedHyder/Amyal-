package com.app.amyal.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.amyal.R;
import com.app.amyal.entities.HotelListModel;
import com.app.amyal.global.Utils;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.ui.viewbinders.abstracts.ViewBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.AnyTextView1;
import com.app.amyal.ui.views.CustomRatingBar;
import com.nostra13.universalimageloader.core.ImageLoader;


public class HotelListBinder extends ViewBinder<HotelListModel> {

    private final String myCurrencyCode;
    private final double currRate;
    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;

    public HotelListBinder(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.item_fragment_select_hotels);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
        myCurrencyCode = preferenceHelper.getUser().getUser().getCurrencyCode();
        currRate = prefHelper.getCurrency().getSAR();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new HotelListBinder.ViewHolder(view);
    }

    @Override
    public void bindView(HotelListModel entity, int position, int grpPosition, View view, Activity activity) {

        HotelListBinder.ViewHolder viewHolder = (HotelListBinder.ViewHolder) view.getTag();

        imageLoader.displayImage(entity.getThumb(), viewHolder.ivHotel);

        viewHolder.tvHotelName.setSelected(true);
        viewHolder.tvHotelName.setText(entity.getHotelName());

        viewHolder.tvLocationName.setText(entity.getZone());
        viewHolder.tvReviews.setText(entity.getCategory());
        if (myCurrencyCode.equals(entity.getCurrencyCode()))
            viewHolder.tvPrice.setText(entity.getCurrencyCode() + " " + entity.getHotelPriceMin() + " - " + entity.getHotelPriceMax());
        else
            viewHolder.tvPrice.setText(myCurrencyCode + " " + (currRate * entity.getHotelPriceMin() + " - " + currRate * entity.getHotelPriceMax()));
        viewHolder.CrRating.setScore(Float.parseFloat(entity.getRating().equals("") ? "0" : entity.getRating()));

        if (entity.getTotalReviewCount() != null && entity.getTotalReviewCount().length() > 0 && Integer.parseInt(entity.getTotalReviewCount()) >= 10) {
            viewHolder.llTextRatting.setVisibility(View.VISIBLE);
            String ratting = Utils.getTextRatting(Float.parseFloat(entity.getRating()));
            if (ratting.length() > 0) {
                viewHolder.tvRatting.setText(entity.getRating() + " ");
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