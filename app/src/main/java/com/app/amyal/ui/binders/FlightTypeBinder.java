package com.app.amyal.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.amyal.R;
import com.app.amyal.entities.FlightTypeEnt;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by khan_muhammad on 11/25/2017.
 */

public class FlightTypeBinder extends RecyclerViewBinder<FlightTypeEnt> {


    private ImageLoader imageLoader;
    private ItemClickListener itemClickListener;

    public FlightTypeBinder(ItemClickListener itemClickListener) {

        this.itemClickListener = itemClickListener;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public RecyclerViewBinder.BaseViewHolder createViewHolder(
            View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final FlightTypeEnt entity, final int position, Object viewHolder, Context context) {
        final ViewHolder holder = (ViewHolder) viewHolder;

        bindItemId(entity.getId());

        if(entity.isSelected())
            imageLoader.displayImage(entity.getFlightTypeImageSelected(), holder.ivFlightType);
        else
            imageLoader.displayImage(entity.getFlightTypeImageUnSelected(), holder.ivFlightType);

        holder.tvFlightType.setText(entity.getFlightType());

        if(entity.isSelected()){
            holder.llMain.setBackgroundColor(context.getResources().getColor(R.color.item_select));
        }else{
            holder.llMain.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        }

        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.itemClicked(entity,position);
            }
        });

    }

    @Override
    public void bindItemId(int position) {

    }

    public static class ViewHolder extends BaseViewHolder {

        private ImageView ivFlightType;
        private AnyTextView tvFlightType;
        private LinearLayout llMain;

        public ViewHolder(View view) {
            super(view);
            llMain = (LinearLayout) view.findViewById(R.id.llMain);
            ivFlightType = (ImageView) view.findViewById(R.id.ivFlightType);
            tvFlightType = (AnyTextView) view.findViewById(R.id.tvFlightType);

        }
    }
}