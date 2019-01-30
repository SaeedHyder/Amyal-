package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.amyal.R;
import com.app.amyal.entities.HotelBookingEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.interfaces.IHotelRoomBtnListner;
import com.app.amyal.ui.adapters.FilterableListAdapter;
import com.app.amyal.ui.binders.BinderHistoryPackage;
import com.app.amyal.ui.views.TitleBar;
import com.google.common.base.Function;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by khan_muhammad on 5/3/2018.
 */

public class PackageManageBokFragment extends BaseFragment implements IHotelRoomBtnListner {

    Unbinder unbinder;
    @BindView(R.id.lvHistory)
    ListView lvHistory;

    private List<HotelBookingEnt> hotelBookingEnts;
    private FilterableListAdapter<HotelBookingEnt> adapter;

    public PackageManageBokFragment() {
        // Required empty public constructor
    }

    public static PackageManageBokFragment newInstance() {

        return new PackageManageBokFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new FilterableListAdapter<>(getDockActivity(), new BinderHistoryPackage(getDockActivity(), prefHelper, this));
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setSubHeading(getString(R.string.manage_bookings));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flights_history, container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceHelper.enqueueCall(webService.GetPackageBookingHistory("1", "Bearer " + prefHelper.getUser().getAuthToken()), WebServiceConstants.GetBookingHistory);

        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PackageHistoryDetailFragment packageHistoryDetailFragment = new PackageHistoryDetailFragment();
                Bundle orderBundle = new Bundle();
                orderBundle.putBoolean(AppConstants.IsManageBook, true);
                orderBundle.putString(AppConstants.HotelBookingEnt, new Gson().toJson(hotelBookingEnts.get(position)));
                packageHistoryDetailFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(packageHistoryDetailFragment, PackageHistoryDetailFragment.class.getSimpleName());
            }
        });

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.GetBookingHistory:
                hotelBookingEnts = (List<HotelBookingEnt>) result;
                if (hotelBookingEnts != null && hotelBookingEnts.size() > 0)
                    bindData(hotelBookingEnts);
                break;
        }
    }

    public void bindData(List<HotelBookingEnt> result) {

        adapter.clearList();
        adapter = new FilterableListAdapter<HotelBookingEnt>(getDockActivity(), new ArrayList<HotelBookingEnt>(), new BinderHistoryPackage(getDockActivity(), prefHelper, this),
                new Function<HotelBookingEnt, String>() {
                    @Override
                    @Nullable
                    public String apply(
                            @Nullable HotelBookingEnt arg0) {
                        return arg0.getName();
                    }
                });
        lvHistory.setAdapter(adapter);
        adapter.addAll(result);
        adapter.notifyDataSetChanged();

    }

    private void filterList(String string) {
        adapter.getFilter().filter(string);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(int position) {
        PackageHistoryDetailFragment packageHistoryDetailFragment = new PackageHistoryDetailFragment();
        Bundle orderBundle = new Bundle();
        orderBundle.putBoolean(AppConstants.IsManageBook, true);
        orderBundle.putString(AppConstants.HotelBookingEnt, new Gson().toJson(hotelBookingEnts.get(position)));
        packageHistoryDetailFragment.setArguments(orderBundle);
        getDockActivity().replaceDockableFragment(packageHistoryDetailFragment, PackageHistoryDetailFragment.class.getSimpleName());
    }

}
