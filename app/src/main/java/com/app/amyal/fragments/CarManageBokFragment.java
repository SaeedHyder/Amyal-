package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.amyal.R;
import com.app.amyal.entities.CarHistoryEnt;
import com.app.amyal.entities.CarList;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.ui.adapters.FilterableListAdapter;
import com.app.amyal.ui.binders.BinderCarHistoryItem;
import com.app.amyal.ui.binders.BinderSelectCar;
import com.app.amyal.ui.views.TitleBar;
import com.google.common.base.Function;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by khan_muhammad on 3/6/2018.
 */

public class CarManageBokFragment extends BaseFragment {

    @BindView(R.id.lvHistory)
    ListView lvHistory;
    Unbinder unbinder;
    private List<CarList> hotelEntList;

    private FilterableListAdapter<CarHistoryEnt> adapter;

    List<CarHistoryEnt> carHistoryEnts;

    public CarManageBokFragment() {
        // Required empty public constructor
    }

    public static CarManageBokFragment newInstance() {

        return new CarManageBokFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new FilterableListAdapter<>(getDockActivity(), new BinderCarHistoryItem(getDockActivity(), prefHelper));
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

        serviceHelper.enqueueCall(webService.GetCarBookingHistory("1", "Bearer " + prefHelper.getUser().getAuthToken()), WebServiceConstants.GetBookingHistory);

        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CarHistoryDetailFragment carHistoryDetailFragment = new CarHistoryDetailFragment();
                Bundle orderBundle = new Bundle();
                orderBundle.putBoolean(AppConstants.IsManageBook, true);
                orderBundle.putString(AppConstants.CarHistoryEnt, new Gson().toJson(carHistoryEnts.get(position)));
                carHistoryDetailFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(carHistoryDetailFragment, CarHistoryDetailFragment.class.getSimpleName());

            }
        });

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.GetBookingHistory:
                carHistoryEnts = (List<CarHistoryEnt>) result;
                if (carHistoryEnts != null && carHistoryEnts.size() > 0)
                    bindData(carHistoryEnts);
                break;
        }
    }

    public void bindData(List<CarHistoryEnt> result) {


        adapter.clearList();
        adapter = new FilterableListAdapter<CarHistoryEnt>(getDockActivity(), new ArrayList<CarHistoryEnt>(), new BinderCarHistoryItem(getDockActivity(), prefHelper),
                new Function<CarHistoryEnt, String>() {
                    @Override
                    @Nullable
                    public String apply(
                            @Nullable CarHistoryEnt arg0) {
                        return arg0.getDetailName();
                    }
                });
        lvHistory.setAdapter(adapter);
        adapter.addAll(result);
        adapter.notifyDataSetChanged();

    }

    private void filterList(String string) {
        adapter.getFilter().filter(string);
    }

}
