package com.app.amyal.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.amyal.R;
import com.app.amyal.entities.FlightBookingHistory;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.ui.adapters.FilterableListAdapter;
import com.app.amyal.ui.binders.BinderFlightHistoryItem;
import com.app.amyal.ui.views.TitleBar;
import com.google.common.base.Function;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FlightManageBookFragment extends BaseFragment {

    @BindView(R.id.lvHistory)
    ListView lvHistory;
    Unbinder unbinder;
    private List<FlightBookingHistory> flightHistoryEnts;
    private FilterableListAdapter<FlightBookingHistory> adapter;
    boolean isRondTrip = false;

    public FlightManageBookFragment() {
        // Required empty public constructor
    }

    public static FlightManageBookFragment newInstance() {

        return new FlightManageBookFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new FilterableListAdapter<>(getDockActivity(), new BinderFlightHistoryItem(getDockActivity(), prefHelper));
    }


    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.GetFlightBookingHistory:

                flightHistoryEnts = (List<FlightBookingHistory>) result;

                if (flightHistoryEnts != null && flightHistoryEnts.size() > 0)
                    bindData((List<FlightBookingHistory>) result);

                break;
        }
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

        serviceHelper.enqueueCall(webService.GetFlightBookingHistory("1", "Bearer " + prefHelper.getUser().getAuthToken()), WebServiceConstants.GetFlightBookingHistory);

        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented_in_beta));

                if(flightHistoryEnts.get(position).getInbound()!= null && flightHistoryEnts.get(position).getInbound().getName()!= null)
                    isRondTrip = true;
                else
                    isRondTrip = false;

                FlightHistoryDetailFragment flightHistoryDetailFragment = new FlightHistoryDetailFragment();
                Bundle orderBundle = new Bundle();
                orderBundle.putString(AppConstants.FlightBookingHistory, new Gson().toJson(flightHistoryEnts.get(position)));
                orderBundle.putInt(AppConstants.position, position);
                orderBundle.putBoolean(AppConstants.IsManageBook, true);
                orderBundle.putBoolean(AppConstants.IsReturnTrip, isRondTrip);
                flightHistoryDetailFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(flightHistoryDetailFragment, SelectFlightFragment.class.getSimpleName());

            }
        });

    }

    private void filterList(String string) {
        adapter.getFilter().filter(string);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public void bindData(List<FlightBookingHistory> result) {

        adapter.clearList();
        adapter = new FilterableListAdapter<FlightBookingHistory>(getDockActivity(), new ArrayList<FlightBookingHistory>(), new BinderFlightHistoryItem(getDockActivity(), prefHelper),
                new Function<FlightBookingHistory, String>() {
                    @Override
                    @Nullable
                    public String apply(
                            @Nullable FlightBookingHistory arg0) {
                        return arg0.getTotalAmount();
                    }
                });
        lvHistory.setAdapter(adapter);
        adapter.addAll(result);
        adapter.notifyDataSetChanged();

    }

}
