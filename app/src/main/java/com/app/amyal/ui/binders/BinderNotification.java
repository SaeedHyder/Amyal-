package com.app.amyal.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.app.amyal.R;
import com.app.amyal.entities.NotificationEnt;
import com.app.amyal.global.AppConstants;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.helpers.DateHelper;
import com.app.amyal.ui.viewbinders.abstracts.ViewBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by khan_muhammad on 9/15/2017.
 */

public class BinderNotification extends ViewBinder<NotificationEnt> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;

    public BinderNotification(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.fragment_notification_item);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new BinderNotification.ViewHolder(view);
    }

    @Override
    public void bindView(NotificationEnt entity, int position, int grpPosition, View view, Activity activity) {

        BinderNotification.ViewHolder viewHolder = (BinderNotification.ViewHolder) view.getTag();

        viewHolder.tv_date.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMDHMS, AppConstants.DateFormat_DMY2,
                entity.getCreationDate()));
        viewHolder.tv_time.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMDHMS, AppConstants.DateFormat_HM,
                entity.getCreationDate()));

        viewHolder.tv_msg.setText(entity.getNotificationMessage());

    }

    public static class ViewHolder extends BaseViewHolder {

        AnyTextView tv_msg;
        AnyTextView tv_date;
        AnyTextView tv_time;

        public ViewHolder(View view) {

            tv_msg = (AnyTextView) view.findViewById(R.id.tv_msg);
            tv_date = (AnyTextView) view.findViewById(R.id.tv_date);
            tv_time = (AnyTextView) view.findViewById(R.id.tv_time);
        }
    }
}
