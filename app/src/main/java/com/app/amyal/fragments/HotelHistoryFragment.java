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
import com.app.amyal.ui.adapters.FilterableListAdapter;
import com.app.amyal.ui.binders.HotelBookingsListBinder;
import com.app.amyal.ui.views.TitleBar;
import com.google.common.base.Function;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by khan_muhammad on 12/5/2017.
 */

public class HotelHistoryFragment extends BaseFragment {

    @BindView(R.id.lvHistory)
    ListView lvHistory;
    Unbinder unbinder;

    private List<HotelBookingEnt> hotelBookingEnts;
    private FilterableListAdapter<HotelBookingEnt> adapter;

    public HotelHistoryFragment() {
        // Required empty public constructor
    }

    public static HotelHistoryFragment newInstance() {

        return new HotelHistoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new FilterableListAdapter<>(getDockActivity(), new HotelBookingsListBinder(getDockActivity(), prefHelper));
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setSubHeading(getString(R.string.booking_history));

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

        serviceHelper.enqueueCall(webService.GetBookingHistory("0", "Bearer " + prefHelper.getUser().getAuthToken()), WebServiceConstants.GetBookingHistory);

        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HotelHistoryDetailFragment hotelHistoryDetailFragment = new HotelHistoryDetailFragment();
                Bundle orderBundle = new Bundle();
                orderBundle.putBoolean(AppConstants.IsManageBook, false);
                orderBundle.putString(AppConstants.HotelBookingEnt, new Gson().toJson(hotelBookingEnts.get(position)));
                hotelHistoryDetailFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(hotelHistoryDetailFragment, HotelHistoryDetailFragment.class.getSimpleName());
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
        adapter = new FilterableListAdapter<HotelBookingEnt>(getDockActivity(), new ArrayList<HotelBookingEnt>(), new HotelBookingsListBinder(getDockActivity(), prefHelper),
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
    public void onResume() {
        super.onResume();

    }
}
