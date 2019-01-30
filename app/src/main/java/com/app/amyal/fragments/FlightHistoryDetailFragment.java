package com.app.amyal.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.app.amyal.R;
import com.app.amyal.entities.BookingDetailEnt;
import com.app.amyal.entities.Detail;
import com.app.amyal.entities.FlightBookingHistory;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by waq on 11/29/2017.
 */

public class FlightHistoryDetailFragment extends BaseFragment {

    boolean isReturnTrip = false;
    @BindView(R.id.vLeftIndecator)
    View vLeftIndecator;
    @BindView(R.id.llLeftTab)
    LinearLayout llLeftTab;
    @BindView(R.id.tvReturn)
    AnyTextView tvReturn;
    @BindView(R.id.vRightIndecator)
    View vRightIndecator;
    @BindView(R.id.llRightTab)
    LinearLayout llRightTab;
    @BindView(R.id.llTabs)
    LinearLayout llTabs;
    @BindView(R.id.ivFlight)
    ImageView ivFlight;
    @BindView(R.id.tvAirlineName)
    AnyTextView tvAirlineName;
    @BindView(R.id.tvTotalDuaration)
    AnyTextView tvTotalDuaration;
    @BindView(R.id.tvTotalDuarationValue)
    AnyTextView tvTotalDuarationValue;
    @BindView(R.id.CrRating)
    CustomRatingBar CrRating;
    @BindView(R.id.tvPrice)
    AnyTextView tvPrice;
    @BindView(R.id.tvPerson)
    AnyTextView tvPerson;
    @BindView(R.id.tvFlightDate)
    AnyTextView tvFlightDate;
    @BindView(R.id.lvFlightDetail)
    ListView lvFlightDetail;
    Unbinder unbinder;
    @BindView(R.id.tvTotalBill)
    AnyTextView tvTotalBill;
    @BindView(R.id.btnAddReview)
    Button btnAddReview;
    @BindView(R.id.btnCancelBooking)
    Button btnCancelBooking;
    private List<BookingDetailEnt> bookingDetailEnts;
    private ArrayListAdapter<BookingDetailEnt> adapter;

    boolean isOutbound = true;
    FlightBookingHistory flightBookingHistory;
    boolean isManageBoking = false;

    public static FlightHistoryDetailFragment newInstance(boolean isRondTrip) {

        FlightHistoryDetailFragment flightHistoryDetailFragment = new FlightHistoryDetailFragment();
        Bundle orderBundle = new Bundle();
        orderBundle.putBoolean(AppConstants.IsReturnTrip, isRondTrip);
        flightHistoryDetailFragment.setArguments(orderBundle);

        return flightHistoryDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new BinderBookingItem(getDockActivity(), prefHelper));

        if (getArguments() != null) {
            isManageBoking = getArguments().getBoolean(AppConstants.IsManageBook);
            isReturnTrip = getArguments().getBoolean(AppConstants.IsReturnTrip);
            flightBookingHistory = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.FlightBookingHistory), FlightBookingHistory.class);
        }

    }

   /* @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.getNotification:
                bindData((ArrayList<NotificationEnt>) result);
                break;
            case WebServiceConstants.NotificaitonCount:
                prefHelper.setNotificationCount(0);
                break;
        }
    }*/

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.tripDetail));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flight_history_detail, container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //serviceHelper.enqueueCall(webService.getNotificationCount(prefHelper.getMerchantId()), WebServiceConstants.NotificaitonCount);

        if (isReturnTrip) {
            llTabs.setVisibility(View.VISIBLE);
        } else {
            llTabs.setVisibility(View.GONE);
        }

        if (flightBookingHistory != null && flightBookingHistory.getOutbound().getDetails() != null) {
            bindData(flightBookingHistory.getOutbound().getDetails());
            Utils.justifyListViewHeightBasedOnChildren(lvFlightDetail);

            tvAirlineName.setText(flightBookingHistory.getOutbound().getName());
            tvTotalDuarationValue.setText(flightBookingHistory.getOutbound().getTotalDuration());
            tvFlightDate.setText(flightBookingHistory.getOutbound().getDate());
            tvPrice.setText(flightBookingHistory.getCurrencyCode() + " " + flightBookingHistory.getTotalAmount());
            tvTotalBill.setText(flightBookingHistory.getCurrencyCode() + " " + flightBookingHistory.getTotalAmount());

            if(flightBookingHistory.getOutbound().getRating()!= null && flightBookingHistory.getOutbound().getRating().length() > 0)
                CrRating.setScore(Float.parseFloat(flightBookingHistory.getOutbound().getRating()));
            else
                CrRating.setVisibility(View.GONE);

            if (isManageBoking) {
                btnCancelBooking.setVisibility(View.VISIBLE);
                btnAddReview.setVisibility(View.GONE);
            } else {
                if (flightBookingHistory.getIsReviewed().equalsIgnoreCase("1")) {
                    btnAddReview.setVisibility(View.GONE);
                } else {
                    btnAddReview.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void bindData(List<Detail> result) {

        bookingDetailEnts = new ArrayList<>();

        for (Detail detail : result) {
            bookingDetailEnts.add(new BookingDetailEnt(detail.getKey(), detail.getValue()));
        }

        adapter.clearList();
        lvFlightDetail.setAdapter(adapter);
        adapter.addAll(bookingDetailEnts);
        adapter.notifyDataSetChanged();

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
                orderBundle.putInt(AppConstants.tabPosition,0);
                historyFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(historyFragment, HistoryFragment.class.getSimpleName());

                break;

            case WebServiceConstants.CancelFlightBooking:

                UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.booking_canceled_success),false);
                btnCancelBooking.setVisibility(View.GONE);
                getDockActivity().popFragment();
                ManageBookingFragment manageBookingFragment = new ManageBookingFragment();
                orderBundle = new Bundle();
                orderBundle.putInt(AppConstants.tabPosition,0);
                manageBookingFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(manageBookingFragment, ManageBookingFragment.class.getSimpleName());

                break;
        }
    }

    @OnClick({R.id.llLeftTab, R.id.llRightTab,R.id.btnAddReview, R.id.btnCancelBooking})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llLeftTab:
                isOutbound = true;
                vLeftIndecator.setVisibility(View.VISIBLE);
                vRightIndecator.setVisibility(View.GONE);
                llLeftTab.setAlpha(1);
                llRightTab.setAlpha(0.5f);

                if (flightBookingHistory != null && flightBookingHistory.getOutbound().getDetails() != null) {
                    bindData(flightBookingHistory.getOutbound().getDetails());
                    Utils.justifyListViewHeightBasedOnChildren(lvFlightDetail);

                    tvAirlineName.setText(flightBookingHistory.getOutbound().getName());
                    tvTotalDuarationValue.setText(flightBookingHistory.getOutbound().getTotalDuration());
                    tvFlightDate.setText(flightBookingHistory.getOutbound().getDate());
                    tvPrice.setText(flightBookingHistory.getCurrencyCode() + " " + flightBookingHistory.getTotalAmount());
                    tvTotalBill.setText(flightBookingHistory.getCurrencyCode() + " " + flightBookingHistory.getTotalAmount());

                    if(flightBookingHistory.getOutbound().getRating()!= null && flightBookingHistory.getOutbound().getRating().length() > 0)
                        CrRating.setScore(Float.parseFloat(flightBookingHistory.getOutbound().getRating()));
                    else
                        CrRating.setVisibility(View.GONE);
                }

                break;
            case R.id.llRightTab:
                isOutbound = false;
                vLeftIndecator.setVisibility(View.GONE);
                vRightIndecator.setVisibility(View.VISIBLE);
                llRightTab.setAlpha(1);
                llLeftTab.setAlpha(0.5f);

                if (flightBookingHistory != null && flightBookingHistory.getInbound().getDetails() != null) {
                    bindData(flightBookingHistory.getInbound().getDetails());
                    Utils.justifyListViewHeightBasedOnChildren(lvFlightDetail);

                    tvAirlineName.setText(flightBookingHistory.getInbound().getName());
                    tvTotalDuarationValue.setText(flightBookingHistory.getInbound().getTotalDuration());
                    tvFlightDate.setText(flightBookingHistory.getInbound().getDate());
                    tvPrice.setText(flightBookingHistory.getCurrencyCode() + " " + flightBookingHistory.getTotalAmount());
                    tvTotalBill.setText(flightBookingHistory.getCurrencyCode() + " " + flightBookingHistory.getTotalAmount());

                    if(flightBookingHistory.getInbound().getRating()!= null && flightBookingHistory.getInbound().getRating().length() > 0)
                        CrRating.setScore(Float.parseFloat(flightBookingHistory.getInbound().getRating()));
                    else
                        CrRating.setVisibility(View.GONE);
                }

                break;


            case R.id.btnAddReview:

                final DialogHelper addReviewDialog = new DialogHelper(getDockActivity());
                addReviewDialog.initAddReviewDialog(R.layout.dialog_add_review, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (addReviewDialog.getReview(R.id.edtReview).length() > 0) {

                            serviceHelper.enqueueCall(webService.AddReviewHotel(
                                    addReviewDialog.getReview(R.id.edtReview),
                                    addReviewDialog.getRatting(R.id.CrRating),
                                    flightBookingHistory.getOutbound().getReferenceID(),
                                    "",
                                    flightBookingHistory.getReservationID(),
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
                                serviceHelper.enqueueCall(webService.CancelReservation(
                                        flightBookingHistory.getReservationID(),
                                        "Bearer " + prefHelper.getUser().getAuthToken()
                                ), WebServiceConstants.CancelFlightBooking);

                            }
                        }, R.string.are_you_sure_cancel_booking).show();



                break;
        }
    }
}
