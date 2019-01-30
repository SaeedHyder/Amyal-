package com.app.amyal.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.amyal.R;
import com.app.amyal.entities.Hotel;
import com.app.amyal.global.Utils;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.ui.viewbinders.abstracts.ViewBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.AnyTextView1;
import com.app.amyal.ui.views.CustomRatingBar;
import com.nostra13.universalimageloader.core.ImageLoader;


public class BinderSelectHotels extends ViewBinder<Hotel> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;

    public BinderSelectHotels(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.item_fragment_select_hotels);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new BinderSelectHotels.ViewHolder(view);
    }

    @Override
    public void bindView(Hotel entity, int position, int grpPosition, View view, Activity activity) {

        BinderSelectHotels.ViewHolder viewHolder = (BinderSelectHotels.ViewHolder) view.getTag();

        viewHolder.tvPrice.setVisibility(View.INVISIBLE);
        if (entity.getThumb() != null && entity.getThumb().length() > 0)
            imageLoader.displayImage(entity.getThumb(), viewHolder.ivHotel);

        viewHolder.tvHotelName.setSelected(true);
        viewHolder.tvHotelName.setText(entity.getHotelName());

        if (entity.getRating() != null && entity.getRating().length() > 0)
            viewHolder.CrRating.setScore(Integer.parseInt(entity.getRating()));

        if (entity.getTotalReviewsCount() != null && entity.getTotalReviewsCount().length() > 0 && Integer.parseInt(entity.getTotalReviewsCount()) >= 10) {
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
        AnyTextView tvRatting;
        AnyTextView tvPrice;
        CustomRatingBar CrRating;
        LinearLayout llTextRatting;

        public ViewHolder(View view) {

            ivHotel = (ImageView) view.findViewById(R.id.ivHotel);
            tvHotelName = (AnyTextView1) view.findViewById(R.id.tvHotelName);
            tvLocationName = (AnyTextView) view.findViewById(R.id.tvLocationName);
            tvReviews = (AnyTextView) view.findViewById(R.id.tvReviews);
            tvRatting = (AnyTextView) view.findViewById(R.id.tvRatting);
            tvPrice = (AnyTextView) view.findViewById(R.id.tvPrice);
            CrRating = (CustomRatingBar) view.findViewById(R.id.CrRating);
            llTextRatting = (LinearLayout) view.findViewById(R.id.llTextRatting);
        }
    }
}

