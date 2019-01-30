package com.app.amyal.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.app.amyal.R;
import com.app.amyal.entities.HotelBookingEnt;
import com.app.amyal.global.Utils;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.interfaces.IHotelRoomBtnListner;
import com.app.amyal.ui.viewbinders.abstracts.ViewBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.CustomRatingBar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by khan_muhammad on 12/7/2017.
 */

public class BinderHistoryPackage extends ViewBinder<HotelBookingEnt> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;
    private IHotelRoomBtnListner iHotelRoomBtnListner;

    public BinderHistoryPackage(Context context, BasePreferenceHelper prefHelper, IHotelRoomBtnListner iHotelRoomBtnListner) {
        super(R.layout.binder_history_package);
        this.context = context;
        this.preferenceHelper = prefHelper;
        this.iHotelRoomBtnListner = iHotelRoomBtnListner;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new BinderHistoryPackage.ViewHolder(view);
    }

    @Override
    public void bindView(HotelBookingEnt entity, final int position, int grpPosition, View view, Activity activity) {

        BinderHistoryPackage.ViewHolder viewHolder = (BinderHistoryPackage.ViewHolder) view.getTag();

        imageLoader.displayImage(entity.getThumbImage(), viewHolder.ivPackage);

        viewHolder.tvPackageName.setText(entity.getName());
        viewHolder.tvDays.setText(entity.getNoOfDays());

        viewHolder.tvPrice.setText(entity.getCurrencyCode() + " " + entity.getAmount());

        if (entity.getHotelReviewsCount() != null && entity.getHotelReviewsCount().length() > 0 && Integer.parseInt(entity.getHotelReviewsCount()) >= 10) {
            viewHolder.tvRattingInWord.setVisibility(View.VISIBLE);
            viewHolder.CrRating.setScore(Integer.parseInt(entity.getHotelRating()));
            String ratting = Utils.getTextRatting(Float.parseFloat(entity.getHotelRating()));
            if (ratting.length() > 0) {
                viewHolder.tvRatting.setText(entity.getHotelRating() + " ");
                viewHolder.tvRattingInWord.setText(ratting);
            }
        } else {
            viewHolder.CrRating.setVisibility(View.INVISIBLE);
            viewHolder.tvRatting.setVisibility(View.GONE);
            viewHolder.tvRattingInWord.setVisibility(View.GONE);
        }

        viewHolder.BtnMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iHotelRoomBtnListner.onClick(position);
            }
        });
    }

    public static class ViewHolder extends BaseViewHolder {

        RoundedImageView ivPackage;
        AnyTextView tvPackageName;
        AnyTextView tvRatting;
        AnyTextView tvRattingInWord;
        AnyTextView tvPrice;
        AnyTextView tvDays;
        CustomRatingBar CrRating;

        AnyTextView BtnMoreInfo;

        public ViewHolder(View view) {

            ivPackage = (RoundedImageView) view.findViewById(R.id.ivPackage);
            tvPackageName = (AnyTextView) view.findViewById(R.id.tvPackageName);
            tvRatting = (AnyTextView) view.findViewById(R.id.tvRatting);
            tvRattingInWord = (AnyTextView) view.findViewById(R.id.tvRattingInWord);
            tvPrice = (AnyTextView) view.findViewById(R.id.tvPrice);
            tvDays = (AnyTextView) view.findViewById(R.id.tvDays);
            CrRating = (CustomRatingBar) view.findViewById(R.id.CrRating);

            BtnMoreInfo = (AnyTextView) view.findViewById(R.id.BtnMoreInfo);
        }
    }
}
