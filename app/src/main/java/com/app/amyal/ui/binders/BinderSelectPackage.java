package com.app.amyal.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.amyal.R;
import com.app.amyal.entities.PackageListEnt;
import com.app.amyal.global.Utils;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.helpers.DateHelper;
import com.app.amyal.interfaces.IHotelRoomBtnListner;
import com.app.amyal.ui.viewbinders.abstracts.ViewBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.CustomRatingBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class BinderSelectPackage extends ViewBinder<PackageListEnt> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;
    private IHotelRoomBtnListner iHotelRoomBtnListner;

    public BinderSelectPackage(Context context, BasePreferenceHelper prefHelper, IHotelRoomBtnListner iHotelRoomBtnListner) {
        super(R.layout.fragment_select_package_item);
        this.context = context;
        this.preferenceHelper = prefHelper;
        this.iHotelRoomBtnListner = iHotelRoomBtnListner;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new BinderSelectPackage.ViewHolder(view);
    }

    @Override
    public void bindView(PackageListEnt entity, final int position, int grpPosition, View view, Activity activity) {

        BinderSelectPackage.ViewHolder viewHolder = (BinderSelectPackage.ViewHolder) view.getTag();

        if (entity.getImages() != null && entity.getImages().size() > 0 && entity.getImages().get(0).toString()!= null && entity.getImages().get(0).toString().length() > 0)
            imageLoader.displayImage(entity.getImages().get(0).toString(), viewHolder.ivPackage);

        viewHolder.tvPackageName.setText(entity.getPackageName());

        viewHolder.tvDays.setText(getDays(entity.getStartDate(),entity.getEndDate()));

        viewHolder.tvPrice.setText(entity.getCurrencyCode() + " " + entity.getTotalAmount());

        if (entity.getTotalReviewCount() != null&& entity.getTotalReviewCount().length() > 0 && Integer.parseInt(entity.getTotalReviewCount()) >= 10) {
            viewHolder.CrRating.setScore(Integer.parseInt(entity.getRating()));
            viewHolder.llTextRatting.setVisibility(View.VISIBLE);
            String ratting = Utils.getTextRatting(Float.parseFloat(entity.getRating()));
            if (ratting.length() > 0) {
                viewHolder.tvRatting.setText(entity.getRating() + " ");
                viewHolder.tvRattingInWord.setText(ratting);
            }
        } else {
            viewHolder.CrRating.setVisibility(View.INVISIBLE);
            viewHolder.llTextRatting.setVisibility(View.INVISIBLE);
        }

        viewHolder.btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iHotelRoomBtnListner.onClick(position);
            }
        });

        viewHolder.BtnMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iHotelRoomBtnListner.onClick(position);
            }
        });
    }

    public String getDays(String startDate, String endDate){

        try {

            SimpleDateFormat sdf = new SimpleDateFormat(DateHelper.DATE_FORMAT3, Locale.ENGLISH);
            Date firstDate = sdf.parse(startDate);
            Date secondDate = sdf.parse(endDate);

            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            return diff+1 +" Days";

        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static class ViewHolder extends BaseViewHolder {

        ImageView ivPackage;
        AnyTextView tvPackageName;
        AnyTextView tvRatting;
        AnyTextView tvRattingInWord;
        AnyTextView tvDays;
        AnyTextView tvPrice;
        CustomRatingBar CrRating;

        AnyTextView BtnMoreInfo;
        Button btnBook;
        LinearLayout llTextRatting;


        public ViewHolder(View view) {

            ivPackage = (ImageView) view.findViewById(R.id.ivPackage);
            tvPackageName = (AnyTextView) view.findViewById(R.id.tvPackageName);
            tvRatting = (AnyTextView) view.findViewById(R.id.tvRatting);
            tvRattingInWord = (AnyTextView) view.findViewById(R.id.tvRattingInWord);
            tvDays = (AnyTextView) view.findViewById(R.id.tvDays);
            tvPrice = (AnyTextView) view.findViewById(R.id.tvPrice);
            CrRating = (CustomRatingBar) view.findViewById(R.id.CrRating);

            BtnMoreInfo = (AnyTextView) view.findViewById(R.id.BtnMoreInfo);
            btnBook = (Button) view.findViewById(R.id.btnBook);
            llTextRatting = (LinearLayout) view.findViewById(R.id.llTextRatting);
        }
    }
}
