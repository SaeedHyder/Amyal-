package com.app.amyal.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.app.amyal.R;
import com.app.amyal.entities.PackageItineraryEnt;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.ui.viewbinders.abstracts.ViewBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by khan_muhammad on 12/7/2017.
 */

public class BinderPackageItineraryItem extends ViewBinder<PackageItineraryEnt> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;

    public BinderPackageItineraryItem(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.package_day_item);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new BinderPackageItineraryItem.ViewHolder(view);
    }

    @Override
    public void bindView(PackageItineraryEnt entity, int position, int grpPosition, View view, Activity activity) {

        BinderPackageItineraryItem.ViewHolder viewHolder = (BinderPackageItineraryItem.ViewHolder) view.getTag();

        imageLoader.displayImage(entity.getIconPath(),viewHolder.ivIcon);

        viewHolder.tvTitle.setText(entity.getTitle());
        viewHolder.tvDesc.setText(entity.getDesc());

        if(entity.isLastDay()){
            viewHolder.vLine.setVisibility(View.GONE);
        }else{
            viewHolder.vLine.setVisibility(View.VISIBLE);
        }


    }

    public static class ViewHolder extends BaseViewHolder {

        ImageView ivIcon;
        AnyTextView tvTitle;
        AnyTextView tvDesc;
        View vLine;

        public ViewHolder(View view) {

            ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
            tvTitle = (AnyTextView) view.findViewById(R.id.tvTitle);
            tvDesc = (AnyTextView) view.findViewById(R.id.tvDesc);
            vLine = (View) view.findViewById(R.id.vLine);
        }
    }
}
