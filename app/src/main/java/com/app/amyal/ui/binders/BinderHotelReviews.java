package com.app.amyal.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.app.amyal.R;
import com.app.amyal.entities.MyReviewsEnt;
import com.app.amyal.global.AppConstants;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.helpers.DateHelper;
import com.app.amyal.ui.viewbinders.abstracts.ViewBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.CustomRatingBar;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by khan_muhammad on 12/4/2017.
 */

public class BinderHotelReviews extends ViewBinder<MyReviewsEnt> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;

    public BinderHotelReviews(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.fragment_hotel_review_item);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new BinderHotelReviews.ViewHolder(view);
    }

    @Override
    public void bindView(MyReviewsEnt entity, int position, int grpPosition, View view, Activity activity) {

        BinderHotelReviews.ViewHolder viewHolder = (BinderHotelReviews.ViewHolder) view.getTag();

        /*viewHolder.tv_date.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMDHMS, AppConstants.DateFormat_DMY2,
                entity.getCreatedAt()));*/

        //viewHolder.tvDate.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMDHMS, AppConstants.DateFormat_DMY3, entity.getDate()));

        viewHolder.tvUserName.setText(entity.getCompanyName());
        viewHolder.tvDate.setText(entity.getDate());
        viewHolder.tvReview.setText(entity.getReview());
        viewHolder.CrRating.setScore(entity.getRatting());


    }

    public static class ViewHolder extends BaseViewHolder {

        ImageView ivUser;
        AnyTextView tvUserName;
        AnyTextView tvDate;
        AnyTextView tvReview;
        CustomRatingBar CrRating;

        public ViewHolder(View view) {

            ivUser = (ImageView) view.findViewById(R.id.ivUser);
            tvUserName = (AnyTextView) view.findViewById(R.id.tvUserName);
            tvDate = (AnyTextView) view.findViewById(R.id.tvDate);
            tvReview = (AnyTextView) view.findViewById(R.id.tvReview);
            CrRating = (CustomRatingBar) view.findViewById(R.id.CrRating);
        }
    }

}
