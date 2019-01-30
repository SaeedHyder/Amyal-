package com.app.amyal.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.app.amyal.R;
import com.app.amyal.entities.CarList;
import com.app.amyal.entities.HotelEnt;
import com.app.amyal.global.Utils;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.ui.viewbinders.abstracts.ViewBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.CustomRatingBar;
import com.nostra13.universalimageloader.core.ImageLoader;

public class BinderSelectCar extends ViewBinder<CarList> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;

    public BinderSelectCar(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.item_fragment_select_car);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new BinderSelectCar.ViewHolder(view);
    }

    @Override
    public void bindView(CarList entity, int position, int grpPosition, View view, Activity activity) {

        BinderSelectCar.ViewHolder viewHolder = (BinderSelectCar.ViewHolder) view.getTag();

        if (entity.getImage() != null && entity.getImage().length() > 0)
            imageLoader.displayImage(entity.getImage(), viewHolder.ivCar);

        viewHolder.tvCarName.setText(entity.getCarName());

        viewHolder.tvPrice.setText(entity.getCurrencyCode() + " " + entity.getTotalAmount());

        if (entity.getTotalReviewCount() != null && entity.getTotalReviewCount().length() > 0 && Integer.parseInt(entity.getTotalReviewCount()) >= 10 && entity.getRating() != null && entity.getRating().length() > 0) {
            viewHolder.CrRating.setVisibility(View.VISIBLE);

            viewHolder.CrRating.setScore(Integer.parseInt(entity.getRating()));

            String ratting = Utils.getTextRatting(Float.parseFloat(entity.getRating()));
            if (ratting.length() > 0) {
                viewHolder.tvReviews.setVisibility(View.VISIBLE);
                viewHolder.tvReviews.setText(entity.getRating() + " " + ratting);
            } else {
                viewHolder.tvReviews.setVisibility(View.GONE);
            }

        } else {
            viewHolder.CrRating.setVisibility(View.GONE);
            viewHolder.tvReviews.setVisibility(View.GONE);
        }

    }

    public static class ViewHolder extends BaseViewHolder {

        ImageView ivCar;
        AnyTextView tvCarName;
        AnyTextView tvReviews;
        AnyTextView tvPrice;
        CustomRatingBar CrRating;

        public ViewHolder(View view) {

            ivCar = (ImageView) view.findViewById(R.id.ivCar);
            tvCarName = (AnyTextView) view.findViewById(R.id.tvCarName);
            tvReviews = (AnyTextView) view.findViewById(R.id.tvReviews);
            tvPrice = (AnyTextView) view.findViewById(R.id.tvPrice);
            CrRating = (CustomRatingBar) view.findViewById(R.id.CrRating);
        }
    }
}


