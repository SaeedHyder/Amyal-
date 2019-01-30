package com.app.amyal.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.app.amyal.R;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.ui.viewbinders.abstracts.ViewBinder;
import com.app.amyal.ui.views.AnyTextView1;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by khan_muhammad on 3/21/2018.
 */

public class itemPackageInclusion extends ViewBinder<String> {
    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;

    public itemPackageInclusion(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.item_amentities);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public ViewBinder.BaseViewHolder createViewHolder(View view) {
        return new itemPackageInclusion.ViewHolder(view);
    }

    @Override
    public void bindView(String entity, int position, int grpPosition, View view, Activity activity) {

        itemPackageInclusion.ViewHolder viewHolder = (itemPackageInclusion.ViewHolder) view.getTag();

        //viewHolder.ivAmentitiy.setVisibility(View.GONE);
        viewHolder.tvAmentitiy.setText(entity);
    }

    public static class ViewHolder extends ViewBinder.BaseViewHolder {

        ImageView ivAmentitiy;
        AnyTextView1 tvAmentitiy;


        public ViewHolder(View view) {

            ivAmentitiy = (ImageView) view.findViewById(R.id.ivAmentitiy);
            tvAmentitiy = (AnyTextView1) view.findViewById(R.id.tvAmentitiy);

        }
    }
}

