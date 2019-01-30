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
 * Created by khan_muhammad on 12/7/2017.
 */

public class PackageBookDetailFragment extends BaseFragment {

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
    @BindView(R.id.lvBooking)
    ListView lvBooking;
    @BindView(R.id.tvTotalBill)
    AnyTextView tvTotalBill;
    @BindView(R.id.btnBookingHistory)
    Button btnBookingHistory;
    @BindView(R.id.btnHome)
    Button btnHome;
    private List<BookingDetailEnt> bookingDetailEnts;
    private ArrayListAdapter<BookingDetailEnt> adapter;

    ReservationEnt reservationEnt;

    Unbinder unbinder;

    public static PackageBookDetailFragment newInstance() {
        return new PackageBookDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new BinderBookingItem(getDockActivity(), prefHelper));

        if (getArguments() != null) {
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

        getMainActivity().setBackground(R.drawable.sp_dark);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        llTabs.setVisibility(View.GONE);
        if (reservationEnt != null) {
            bindData(reservationEnt.getDetails());
            Utils.justifyListViewHeightBasedOnChildren(lvBooking);
//            tvTotalBill.setText(reservationEnt.getCurrencyCode() + " " + reservationEnt.getBillAmount());
            if (prefHelper.getUser().getUser().getCurrencyCode().equals(reservationEnt.getCurrencyCode()))
                tvTotalBill.setText(reservationEnt.getCurrencyCode() + " " + reservationEnt.getBillAmount());
            else
                tvTotalBill.setText(prefHelper.getUser().getUser().getCurrencyCode() + " " + prefHelper.getCurrency().getSAR() * reservationEnt.getBillAmount());
        }

        tvTitleDetails.setText(getString(R.string.package_bok_deatils));

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

    @OnClick({R.id.btnBookingHistory, R.id.btnHome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBookingHistory:
                ManageBookingFragment manageBookingFragment = new ManageBookingFragment();
                Bundle orderBundle = new Bundle();
                orderBundle.putInt(AppConstants.tabPosition, 3);
                manageBookingFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(manageBookingFragment, ManageBookingFragment.class.getSimpleName());
                break;
            case R.id.btnHome:
                getDockActivity().popBackStackTillEntry(0);
                HomeFragment homeFragment = new HomeFragment();
                orderBundle = new Bundle();
                orderBundle.putInt(AppConstants.tabPosition, 3);
                homeFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(homeFragment, "HomeFragment");
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
