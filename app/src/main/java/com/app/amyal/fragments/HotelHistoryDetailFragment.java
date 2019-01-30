package com.app.amyal.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.app.amyal.R;
import com.app.amyal.entities.BookingDetailEnt;
import com.app.amyal.entities.Detail;
import com.app.amyal.entities.HotelBookingEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.Utils;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.DialogHelper;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.retrofit.GsonFactory;
import com.app.amyal.ui.adapters.ArrayListAdapter;
import com.app.amyal.ui.binders.BinderBookingItem;
import com.app.amyal.ui.dialogs.DialogFactory;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.CustomRatingBar;
import com.app.amyal.ui.views.TitleBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HotelHistoryDetailFragment extends BaseFragment {

    @BindView(R.id.ivHotel)
    ImageView ivHotel;
    @BindView(R.id.tvHotelName)
    AnyTextView tvHotelName;
    @BindView(R.id.tvRatting)
    AnyTextView tvRatting;
    @BindView(R.id.tvRattingInWord)
    AnyTextView tvRattingInWord;
    @BindView(R.id.CrHotelRating)
    CustomRatingBar CrHotelRating;
    @BindView(R.id.tvPrice)
    AnyTextView tvPrice;
    @BindView(R.id.lvBookDetail)
    ListView lvBookDetail;

    @BindView(R.id.btnAddReview)
    Button btnAddReview;

    Unbinder unbinder;

    @BindView(R.id.tvTotalBill)
    AnyTextView tvTotalBill;
    @BindView(R.id.btnCancelBooking)
    Button btnCancelBooking;


    private List<BookingDetailEnt> bookingDetailEnts;
    private ArrayListAdapter<BookingDetailEnt> adapter;

    HotelBookingEnt hotelBookingEnt;

    boolean isManageBoking = false;

    public static HotelHistoryDetailFragment newInstance() {
        Bundle args = new Bundle();

        HotelHistoryDetailFragment fragment = new HotelHistoryDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new BinderBookingItem(getDockActivity(), prefHelper));

        if (getArguments() != null) {
            isManageBoking = getArguments().getBoolean(AppConstants.IsManageBook);
            hotelBookingEnt = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.HotelBookingEnt), HotelBookingEnt.class);
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(hotelBookingEnt.getName());
    }

    public void bindData(List<Detail> result) {

        bookingDetailEnts = new ArrayList<>();

        for (Detail detail : result) {
            bookingDetailEnts.add(new BookingDetailEnt(detail.getKey(), detail.getValue()));
        }

        adapter.clearList();
        lvBookDetail.setAdapter(adapter);
        adapter.addAll(bookingDetailEnts);
        adapter.notifyDataSetChanged();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel_history_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (hotelBookingEnt != null && hotelBookingEnt.getDetails() != null && hotelBookingEnt.getDetails().size() > 0) {
            bindData(hotelBookingEnt.getDetails());
            setData();
            Utils.justifyListViewHeightBasedOnChildren(lvBookDetail);

        }
    }

    public void setData() {

        if (hotelBookingEnt.getImage() != null && hotelBookingEnt.getImage().length() > 0) {
            Picasso.with(getDockActivity())
                    .load(hotelBookingEnt.getImage())
                    .into(ivHotel);
        }

        tvHotelName.setText(hotelBookingEnt.getDetailName());
        tvPrice.setText(hotelBookingEnt.getCurrencyCode() + " " + hotelBookingEnt.getAmount());

        if (hotelBookingEnt.getHotelReviewsCount() != null && hotelBookingEnt.getHotelReviewsCount().length() > 0 && Integer.parseInt(hotelBookingEnt.getHotelReviewsCount()) >= 10) {
            tvRatting.setVisibility(View.VISIBLE);
            tvRattingInWord.setVisibility(View.VISIBLE);

            String ratting = "";
            if (hotelBookingEnt.getRoomRating().length() > 0) {
                ratting = Utils.getTextRatting(Float.parseFloat(hotelBookingEnt.getRoomRating()));
                CrHotelRating.setScore(Float.parseFloat(hotelBookingEnt.getRoomRating()));
            } else {
                CrHotelRating.setVisibility(View.GONE);
            }

            if (ratting.length() > 0) {
                tvRatting.setText(hotelBookingEnt + " ");
                tvRattingInWord.setText(ratting);
            }

        } else {
            CrHotelRating.setVisibility(View.GONE);
            tvRatting.setVisibility(View.GONE);
            tvRattingInWord.setVisibility(View.GONE);
        }

        tvTotalBill.setText(hotelBookingEnt.getCurrencyCode() + " " + hotelBookingEnt.getAmount());

        if(isManageBoking) {
            btnCancelBooking.setVisibility(View.VISIBLE);
        }else{
            if (hotelBookingEnt.getIsReviewed().equalsIgnoreCase("1")) {
                btnAddReview.setVisibility(View.GONE);
            } else {
                btnAddReview.setVisibility(View.VISIBLE);
            }
        }

    }


    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.AddReview:

                btnAddReview.setVisibility(View.GONE);
                UIHelper.showLongToastInCenter(getDockActivity(),getString(R.string.review_added_success),false);
                getDockActivity().popFragment();
                HistoryFragment historyFragment = new HistoryFragment();
                Bundle orderBundle = new Bundle();
                orderBundle.putInt(AppConstants.tabPosition,1);
                historyFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(historyFragment, HistoryFragment.class.getSimpleName());

                break;

            case WebServiceConstants.CancelHotelReservation:

                UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.booking_canceled_success),false);
                btnCancelBooking.setVisibility(View.GONE);
                getDockActivity().popFragment();
                ManageBookingFragment manageBookingFragment = new ManageBookingFragment();
                orderBundle = new Bundle();
                orderBundle.putInt(AppConstants.tabPosition,1);
                manageBookingFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(manageBookingFragment, ManageBookingFragment.class.getSimpleName());

                break;
        }
    }


    @OnClick({R.id.btnAddReview, R.id.btnCancelBooking})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btnAddReview:

                final DialogHelper addReviewDialog = new DialogHelper(getDockActivity());
                addReviewDialog.initAddReviewDialog(R.layout.dialog_add_review, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (addReviewDialog.getReview(R.id.edtReview).length() > 0) {

                            serviceHelper.enqueueCall(webService.AddReviewHotel(
                                    addReviewDialog.getReview(R.id.edtReview),
                                    addReviewDialog.getRatting(R.id.CrRating),
                                    hotelBookingEnt.getReferenceID(),
                                    hotelBookingEnt.getDetailName(),
                                    hotelBookingEnt.getReservationID(),
                                    "Bearer " + prefHelper.getUser().getAuthToken()
                            ), WebServiceConstants.AddReview);

                            addReviewDialog.hideDialog();
                        } else {
                            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_enter_review));
                        }
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addReviewDialog.hideDialog();
                    }
                });
                addReviewDialog.setCancelable(false);
                addReviewDialog.showDialog();

                break;

            case R.id.btnCancelBooking:

                DialogFactory.createQuitDialog(getMainActivity(),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                serviceHelper.enqueueCall(webService.CancelHotelReservation(
                                        hotelBookingEnt.getReservationID(),
                                        "Bearer " + prefHelper.getUser().getAuthToken()
                                ), WebServiceConstants.CancelHotelReservation);

                            }
                        }, R.string.are_you_sure_cancel_booking).show();


                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}