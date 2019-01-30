package com.app.amyal.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.app.amyal.R;
import com.app.amyal.entities.AmentityEnt;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.ui.viewbinders.abstracts.ViewBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.AnyTextView1;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by khan_muhammad on 12/5/2017.
 */

public class BinderAmentities extends ViewBinder<AmentityEnt> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;

    public BinderAmentities(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.item_amentities);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new BinderAmentities.ViewHolder(view);
    }

    @Override
    public void bindView(AmentityEnt entity, int position, int grpPosition, View view, Activity activity) {

        BinderAmentities.ViewHolder viewHolder = (BinderAmentities.ViewHolder) view.getTag();

        imageLoader.displayImage(entity.getImage(),viewHolder.ivAmentitiy);

        viewHolder.tvAmentitiy.setText(entity.getName());

    }

    public static class ViewHolder extends BaseViewHolder {

        ImageView ivAmentitiy;
        AnyTextView1 tvAmentitiy;


        public ViewHolder(View view) {

            ivAmentitiy = (ImageView) view.findViewById(R.id.ivAmentitiy);
            tvAmentitiy = (AnyTextView1) view.findViewById(R.id.tvAmentitiy);

        }
    }
}
