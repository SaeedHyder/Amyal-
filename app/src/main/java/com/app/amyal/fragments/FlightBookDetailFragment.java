package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.app.amyal.R;
import com.app.amyal.entities.BookingDetailEnt;
import com.app.amyal.entities.Detail;
import com.app.amyal.entities.ReservationEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.Utils;
import com.app.amyal.retrofit.GsonFactory;
import com.app.amyal.ui.adapters.ArrayListAdapter;
import com.app.amyal.ui.binders.BinderBookingItem;
import com.app.amyal.ui.views.AnyTextView;
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

public class FlightBookDetailFragment extends BaseFragment {

    @BindView(R.id.lvBooking)
    ListView lvBooking;
    @BindView(R.id.tvTotalBill)
    AnyTextView tvTotalBill;
    @BindView(R.id.btnBookingHistory)
    Button btnBookingHistory;
    @BindView(R.id.btnHome)
    Button btnHome;
    Unbinder unbinder;
    @BindView(R.id.tvTitleDetails)
    AnyTextView tvTitleDetails;
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
    boolean isRondTrip = false;
    ReservationEnt reservationEnt;
    private List<BookingDetailEnt> bookingDetailEnts;
    private ArrayListAdapter<BookingDetailEnt> adapter;

    public static FlightBookDetailFragment newInstance(boolean isRondTrip) {

        FlightBookDetailFragment flightBookDetailFragment = new FlightBookDetailFragment();
        Bundle orderBundle = new Bundle();
        orderBundle.putBoolean(AppConstants.IsReturnTrip, isRondTrip);
        flightBookDetailFragment.setArguments(orderBundle);

        return flightBookDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new BinderBookingItem(getDockActivity(), prefHelper));

        if (getArguments() != null) {
            isRondTrip = getArguments().getBoolean(AppConstants.IsReturnTrip);
            reservationEnt = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.ReservationEnt), ReservationEnt.class);
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.booking_details));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flight_book_details, container, false);

        getMainActivity().setBackground(R.drawable.bg_flight);

        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //serviceHelper.enqueueCall(webService.getNotificationCount(prefHelper.getMerchantId()), WebServiceConstants.NotificaitonCount);

        if (isRondTrip && reservationEnt.getReturn_details() != null) {
            llTabs.setVisibility(View.VISIBLE);
        } else {
            llTabs.setVisibility(View.GONE);
        }

        if (reservationEnt != null) {
            bindData(reservationEnt.getDetails());
            Utils.justifyListViewHeightBasedOnChildren(lvBooking);
            if (prefHelper.getUser().getUser().getCurrencyCode().equals(reservationEnt.getCurrencyCode()))
                tvTotalBill.setText(reservationEnt.getCurrencyCode() + " " + reservationEnt.getBillAmount());
            else
                tvTotalBill.setText(prefHelper.getUser().getUser().getCurrencyCode() + " " + prefHelper.getCurrency().getSAR() * reservationEnt.getBillAmount());
        }

        tvTitleDetails.setText(getString(R.string.flight_book_details));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void bindData(List<Detail> result) {

        bookingDetailEnts = new ArrayList<>();

        for (Detail detail : result) {
            bookingDetailEnts.add(new BookingDetailEnt(detail.getKey(), detail.getValue()));
        }

        adapter.clearList();
        lvBooking.setAdapter(adapter);
        adapter.addAll(bookingDetailEnts);
        adapter.notifyDataSetChanged();

    }


    @OnClick({R.id.btnBookingHistory, R.id.btnHome, R.id.llLeftTab, R.id.llRightTab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBookingHistory:

                ManageBookingFragment manageBookingFragment = new ManageBookingFragment();
                Bundle orderBundle = new Bundle();
                orderBundle.putInt(AppConstants.tabPosition, 0);
                manageBookingFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(manageBookingFragment, ManageBookingFragment.class.getSimpleName());

                break;
            case R.id.btnHome:
                getDockActivity().popBackStackTillEntry(0);
                HomeFragment homeFragment = new HomeFragment();
                orderBundle = new Bundle();
                orderBundle.putInt(AppConstants.tabPosition, 0);
                homeFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(homeFragment, "HomeFragment");
                break;

            case R.id.llLeftTab:
                //isOutbound = true;
                vLeftIndecator.setVisibility(View.VISIBLE);
                vRightIndecator.setVisibility(View.GONE);
                llLeftTab.setAlpha(1);
                llRightTab.setAlpha(0.5f);
                bindData(reservationEnt.getDetails());
                break;
            case R.id.llRightTab:
                //isOutbound = false;
                vLeftIndecator.setVisibility(View.GONE);
                vRightIndecator.setVisibility(View.VISIBLE);
                llRightTab.setAlpha(1);
                llLeftTab.setAlpha(0.5f);
                bindData(reservationEnt.getReturn_details());
                break;
        }
    }
}
