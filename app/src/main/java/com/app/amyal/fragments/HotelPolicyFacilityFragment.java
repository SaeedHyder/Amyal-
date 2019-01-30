package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.amyal.R;
import com.app.amyal.entities.BookingDetailEnt;
import com.app.amyal.entities.HotelGalleryEnt;
import com.app.amyal.entities.HotelListModel;
import com.app.amyal.entities.HotelPoliciesEnt;
import com.app.amyal.entities.Policy;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.retrofit.GsonFactory;
import com.app.amyal.ui.adapters.ArrayListAdapter;
import com.app.amyal.ui.binders.BinderBookingItem;
import com.app.amyal.ui.binders.BinderHotelPolicyFacility;
import com.app.amyal.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by khan_muhammad on 12/4/2017.
 */

public class HotelPolicyFacilityFragment extends BaseFragment {


    @BindView(R.id.lvData)
    ListView lvData;
    Unbinder unbinder;
    private List<BookingDetailEnt> bookingDetailEnts;
    private ArrayListAdapter<BookingDetailEnt> adapter;

    boolean isFacilities = false;

    HotelPoliciesEnt hotelPoliciesEnt;
    HotelGalleryEnt hotelGalleryEnt;

    public static HotelPolicyFacilityFragment newInstance() {
        return new HotelPolicyFacilityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new BinderHotelPolicyFacility(getDockActivity(), prefHelper));
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();

        if (isFacilities)
            titleBar.setSubHeading(getString(R.string.hotel_facilities));
        else
            titleBar.setSubHeading(getString(R.string.hotel_policies));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_policy_facility, container, false);

        getMainActivity().setBackground(R.drawable.bg_hotel);

        if (getArguments() != null) {
            isFacilities = getArguments().getBoolean(AppConstants.IsFacilities);
            hotelPoliciesEnt = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.HotelPolicyModel), HotelPoliciesEnt.class);
            hotelGalleryEnt = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.HotelListModel), HotelGalleryEnt.class);
        }

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (hotelPoliciesEnt != null) {
            if (isFacilities)
                bindData(hotelGalleryEnt.getFacilities());
            else
                bindData(hotelPoliciesEnt.getPolicies());
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void bindData(List<Policy> result) {

        bookingDetailEnts = new ArrayList<>();

        for (Policy policy : result) {
            if (!isFacilities) {
                bookingDetailEnts.add(new BookingDetailEnt(policy.getKey(), policy.getValue()));
            } else {
                bookingDetailEnts.add(new BookingDetailEnt(policy.getKey(), policy.getValue()));
            }
        }

        adapter.clearList();
        lvData.setAdapter(adapter);
        adapter.addAll(bookingDetailEnts);
        adapter.notifyDataSetChanged();

    }

}
