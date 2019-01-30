package com.app.amyal.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.app.amyal.R;
import com.app.amyal.entities.BookingDetailEnt;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.ui.viewbinders.abstracts.ViewBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.AnyTextView1;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by khan_muhammad on 11/30/2017.
 */

public class BinderSearchItem extends ViewBinder<BookingDetailEnt> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;

    public BinderSearchItem(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.fragment_search_city_airport_item);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new BinderSearchItem.ViewHolder(view);
    }

    @Override
    public void bindView(BookingDetailEnt entity, int position, int grpPosition, View view, Activity activity) {

        BinderSearchItem.ViewHolder viewHolder = (BinderSearchItem.ViewHolder) view.getTag();

        viewHolder.tvName.setText(entity.getName());

    }

    public static class ViewHolder extends BaseViewHolder {

        AnyTextView1 tvName;


        public ViewHolder(View view) {

            tvName = (AnyTextView1) view.findViewById(R.id.tvName);

        }
    }
}
