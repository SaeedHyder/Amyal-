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
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyReviewsBinder extends ViewBinder<MyReviewsEnt> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;

    public MyReviewsBinder(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.item_fragment_myreviews);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new MyReviewsBinder.ViewHolder(view);
    }

    @Override
    public void bindView(MyReviewsEnt entity, int position, int grpPosition, View view, Activity activity) {

        MyReviewsBinder.ViewHolder viewHolder = (MyReviewsBinder.ViewHolder) view.getTag();

        /*viewHolder.tv_date.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMDHMS, AppConstants.DateFormat_DMY2,
                entity.getCreatedAt()));*/

         viewHolder.tvDate.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMDHMS, AppConstants.DateFormat_DMY3, entity.getDate()));

        viewHolder.tvCompanyName.setText(entity.getCompanyName());
        //viewHolder.tvDate.setText(entity.getDate());
        viewHolder.tvReview.setText(entity.getReview());
        viewHolder.CrRating.setScore(entity.getRatting());

        if(preferenceHelper.getUser().getUser().getImage()!= null && preferenceHelper.getUser().getUser().getImage().length() > 0){
            Picasso.with(context)
                    .load(preferenceHelper.getUser().getUser().getImage())
                    .into(viewHolder.ivUser);
        }

    }

    public static class ViewHolder extends BaseViewHolder {

        CircleImageView ivUser;
        AnyTextView tvCompanyName;
        AnyTextView tvDate;
        AnyTextView tvReview;
        CustomRatingBar CrRating;

        public ViewHolder(View view) {

            ivUser = (CircleImageView) view.findViewById(R.id.ivUser);
            tvCompanyName = (AnyTextView) view.findViewById(R.id.tvCompanyName);
            tvDate = (AnyTextView) view.findViewById(R.id.tvDate);
            tvReview = (AnyTextView) view.findViewById(R.id.tvReview);
            CrRating = (CustomRatingBar) view.findViewById(R.id.CrRating);
        }
    }
}