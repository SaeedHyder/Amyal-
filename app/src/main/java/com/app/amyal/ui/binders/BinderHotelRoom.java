package com.app.amyal.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.app.amyal.R;
import com.app.amyal.entities.HotelRoomEnt;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.app.amyal.interfaces.IHotelRoomBtnListner;
import com.app.amyal.ui.viewbinders.abstracts.ViewBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.CustomRatingBar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by khan_muhammad on 12/5/2017.
 */

public class BinderHotelRoom extends ViewBinder<HotelRoomEnt> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;
    private IHotelRoomBtnListner iHotelRoomBtnListner;

    public BinderHotelRoom(Context context, BasePreferenceHelper prefHelper, IHotelRoomBtnListner iHotelRoomBtnListner) {
        super(R.layout.fragment_hotel_rooms_item);
        this.context = context;
        this.preferenceHelper = prefHelper;
        this.iHotelRoomBtnListner = iHotelRoomBtnListner;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new BinderHotelRoom.ViewHolder(view);
    }

    @Override
    public void bindView(HotelRoomEnt entity, final int position, int grpPosition, View view, Activity activity) {

        BinderHotelRoom.ViewHolder viewHolder = (BinderHotelRoom.ViewHolder) view.getTag();

        imageLoader.displayImage(entity.getImageRoom(), viewHolder.ivRoom);

        viewHolder.tvRoomName.setText(entity.getRoomName());
        viewHolder.tvRatting.setText(entity.getRatting()+"");
        viewHolder.tvRattingInWord.setText(entity.getRattingInWord());
        viewHolder.tvPrice.setText(entity.getPrice());
        viewHolder.CrRating.setScore(entity.getRatting());

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

    public static class ViewHolder extends BaseViewHolder {

        RoundedImageView ivRoom;
        AnyTextView tvRoomName;
        AnyTextView tvRatting;
        AnyTextView tvRattingInWord;
        AnyTextView tvPrice;
        CustomRatingBar CrRating;

        AnyTextView BtnMoreInfo;
        Button btnBook;

        public ViewHolder(View view) {

            ivRoom = (RoundedImageView) view.findViewById(R.id.ivRoom);
            tvRoomName = (AnyTextView) view.findViewById(R.id.tvRoomName);
            tvRatting = (AnyTextView) view.findViewById(R.id.tvRatting);
            tvRattingInWord = (AnyTextView) view.findViewById(R.id.tvRattingInWord);
            tvPrice = (AnyTextView) view.findViewById(R.id.tvPrice);
            CrRating = (CustomRatingBar) view.findViewById(R.id.CrRating);

            BtnMoreInfo = (AnyTextView) view.findViewById(R.id.BtnMoreInfo);
            btnBook = (Button) view.findViewById(R.id.btnBook);
        }
    }
}
