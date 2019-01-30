package com.app.amyal.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.app.amyal.R;
import com.app.amyal.entities.RoomCombination;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.ui.viewbinders.abstracts.ViewBinder;
import com.app.amyal.ui.views.AnyTextView1;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HotelDetailRoomItemBinder extends ViewBinder<RoomCombination> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;

    public HotelDetailRoomItemBinder(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.hotel_rooms_item);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new HotelDetailRoomItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(RoomCombination entity, int position, int grpPosition, View view, Activity activity) {

        HotelDetailRoomItemBinder.ViewHolder viewHolder = (HotelDetailRoomItemBinder.ViewHolder) view.getTag();

        viewHolder.tvRoomName.setSelected(true);
        viewHolder.tvRoomName.setText(entity.getRoomType());

        viewHolder.tvPrice.setText(entity.getCurrencyCode() + " " + entity.getAmountWithTax());

        if (entity.getPromotions() != null && entity.getPromotions().size() > 0 && entity.getPromotions().get(0).getName() != null && entity.getPromotions().get(0).getName().length() > 0) {
            viewHolder.llDiscount.setVisibility(View.VISIBLE);
            viewHolder.tvDiscountTitle.setText(entity.getPromotions().get(0).getName());
            viewHolder.tvDiscountDesc.setText(entity.getPromotions().get(0).getDescription());
        } else {
            viewHolder.llDiscount.setVisibility(View.GONE);
        }

    }

    public static class ViewHolder extends BaseViewHolder {

        AnyTextView1 tvRoomName;
        AnyTextView1 tvPrice;
        AnyTextView1 tvDiscountTitle;
        AnyTextView1 tvDiscountDesc;
        LinearLayout llDiscount;


        public ViewHolder(View view) {

            tvRoomName = (AnyTextView1) view.findViewById(R.id.tvRoomName);
            tvPrice = (AnyTextView1) view.findViewById(R.id.tvPrice);
            tvDiscountTitle = (AnyTextView1) view.findViewById(R.id.tvDiscountTitle);
            tvDiscountDesc = (AnyTextView1) view.findViewById(R.id.tvDiscountDesc);
            llDiscount = (LinearLayout) view.findViewById(R.id.llDiscount);

        }
    }
}
