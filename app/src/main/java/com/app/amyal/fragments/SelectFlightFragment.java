package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.app.amyal.Comparators.SortBasedOnFlightName;
import com.app.amyal.R;
import com.app.amyal.entities.FlightEnt;
import com.app.amyal.entities.FlightListEnt;
import com.app.amyal.entities.FlightSearchModel;
import com.app.amyal.entities.FlightsList;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.Utils;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.retrofit.GsonFactory;
import com.app.amyal.ui.adapters.FilterableListAdapter;
import com.app.amyal.ui.binders.SelectFlightsBinder;
import com.app.amyal.ui.views.AnyEditTextView;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.TitleBar;
import com.google.common.base.Function;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SelectFlightFragment extends BaseFragment implements ItemClickListener {

    @BindView(R.id.spSort)
    Spinner spSort;

    @BindView(R.id.lvFlights)
    ListView lvFlights;
    Unbinder unbinder;
    @BindView(R.id.llSpinner)
    LinearLayout llSpinner;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.edt_search)
    AnyEditTextView edtSearch;
    @BindView(R.id.ivClose)
    ImageView ivClose;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvFlightName)
    AnyTextView tvFlightName;
    @BindView(R.id.tvFlightDuraion)
    AnyTextView tvFlightDuraion;
    @BindView(R.id.tvFlightStops)
    AnyTextView tvFlightStops;
    @BindView(R.id.tvOutbound)
    AnyTextView tvOutbound;
    @BindView(R.id.llOutbound)
    LinearLayout llOutbound;
    ArrayList<String> sort_list = new ArrayList<>();
    ArrayAdapter<String> sortAdapter;
    boolean isReturnTrip = false;
    boolean isInBound = false;
    FlightListEnt flightListEnt;
    SelectFlightFragment context;
    private List<FlightEnt> flightEntList;
    //private ArrayListAdapter<FlightEnt> adapter;
    private FilterableListAdapter<FlightEnt> adapter;
    private int pageIndex;
    private int preLast;

    public static SelectFlightFragment newInstance(boolean isRondTrip, boolean isInBound) {

        SelectFlightFragment selectFlightFragment = new SelectFlightFragment();
        Bundle orderBundle = new Bundle();
        orderBundle.putBoolean(AppConstants.IsReturnTrip, isRondTrip);
        orderBundle.putBoolean(AppConstants.IsInBound, isInBound);
        selectFlightFragment.setArguments(orderBundle);

        return selectFlightFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new FilterableListAdapter<>(getDockActivity(), new SelectFlightsBinder(getDockActivity(), prefHelper));

        if (getArguments() != null && flightListEnt == null) {
            flightListEnt = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.FlightListEnt), FlightListEnt.class);
            isReturnTrip = getArguments().getBoolean(AppConstants.IsReturnTrip);
            isInBound = getArguments().getBoolean(AppConstants.IsInBound);
        }

        context = this;

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.GetFlightList:

                FlightListEnt flightListEnt = (FlightListEnt) result;

                FlightSearchModel flightSearchModel = prefHelper.getFlightSearchModel();
                flightSearchModel.setSequenceNumber(flightListEnt.getSequenceNumber());

                if (flightListEnt != null) {
                    flightEntList = new ArrayList<>();

                    for (FlightsList flightsList : flightListEnt.getFlightsList()) {
                        flightEntList.add(new FlightEnt(flightsList.getThumb(), flightsList.getAirlineName(), flightsList.getRating(), flightsList.getTotalDuration(), Integer.parseInt(flightsList.getNoOfStops()), prefHelper.getFlightSearchModel().getCabinType(), (flightsList.getAmount()), flightsList.getFlightStop(), prefHelper.getFlightSearchModel().getCurrencyCode()));
                    }
                    adapter.addAll(flightEntList);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        //titleBar.setSubHeading(getString(R.string.flights));
        titleBar.showRight1Button(R.drawable.search, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSpinner.setVisibility(View.GONE);
                llSearch.setVisibility(View.VISIBLE);
            }
        });

        titleBar.showRightButton(R.drawable.filter, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlightFilterFragment flightFilterFragment = new FlightFilterFragment();
                flightFilterFragment.setListner(context);
                getDockActivity().replaceDockableFragment(flightFilterFragment, FlightFilterFragment.class.getSimpleName());
            }
        });


        if (isReturnTrip) {
            /*if (isInBound) {
                llOutbound.setVisibility(View.VISIBLE);
                titleBar.setSubHeading("Inbound to LAS");

            } else {
                llOutbound.setVisibility(View.GONE);
                titleBar.setSubHeading("Outbound to FLO");

            }*/

            llOutbound.setVisibility(View.GONE);
            titleBar.setSubHeading("Select Flight");

        } else {
            if (isInBound) {
                titleBar.setSubHeading("Inbound to " + prefHelper.getFlightSearchModel().getFromLocationCode());
            } else {
                titleBar.setSubHeading("Outbound to " + prefHelper.getFlightSearchModel().getToLocationCode());
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_flights, container, false);

        unbinder = ButterKnife.bind(this, view);

        getMainActivity().setBackground(R.drawable.bg_flight);
        pagenation();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (flightListEnt != null && flightListEnt.getFlightsList() != null && flightListEnt.getFlightsList().size() > 0) {
            bindData(flightListEnt.getFlightsList());
        }

        setSortSpinnerData();

        edtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterList(s.toString());
            }

        });

        lvFlights.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (isReturnTrip) {

                    RoundTripFlightDetailsFragment roundTripFlightDetailsFragment = new RoundTripFlightDetailsFragment();
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.FlightsList, new Gson().toJson(flightListEnt.getFlightsList().get(position)));
                    roundTripFlightDetailsFragment.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(roundTripFlightDetailsFragment, RoundTripFlightDetailsFragment.class.getSimpleName());

                } else {
                    SingleFlightDetailFragment singleFlightDetailFragment = new SingleFlightDetailFragment();
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.FlightsList, new Gson().toJson(flightListEnt.getFlightsList().get(position)));
                    orderBundle.putBoolean(AppConstants.IsReturnTrip, isReturnTrip);
                    orderBundle.putBoolean(AppConstants.IsInBound, isInBound);
                    singleFlightDetailFragment.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(singleFlightDetailFragment, SelectFlightFragment.class.getSimpleName() + "OutBound");
                }
            }
        });

        spSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 1) {

                    if (flightListEnt.getFlightsList() != null) {

                        Collections.sort(flightListEnt.getFlightsList(), new SortBasedOnFlightName());
                        bindData(flightListEnt.getFlightsList());
                    }

                } else if (position == 2) {

                    Collections.sort(flightListEnt.getFlightsList(), new SortBasedOnFlightName());
                    Collections.reverse(flightListEnt.getFlightsList());
                    bindData(flightListEnt.getFlightsList());

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void filterList(String string) {
        adapter.getFilter().filter(string);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //unbinder.unbind();

    }

    public void bindData(List<FlightsList> result) {

        flightEntList = new ArrayList<>();

        for (FlightsList flightsList : result) {
            flightEntList.add(new FlightEnt(flightsList.getThumb(), flightsList.getAirlineName(), flightsList.getRating(), flightsList.getTotalDuration(), Integer.parseInt(flightsList.getNoOfStops()), prefHelper.getFlightSearchModel().getCabinType(), (flightsList.getAmount()), flightsList.getFlightStop(), flightsList.getCurrencyCode()));
        }
        adapter.clearList();
        adapter = new FilterableListAdapter<>(getDockActivity(), new ArrayList<FlightEnt>(), new SelectFlightsBinder(getDockActivity(), prefHelper),
                new Function<FlightEnt, String>() {
                    @Override
                    @Nullable
                    public String apply(
                            @Nullable FlightEnt arg0) {
                        return arg0.getFlightName();
                    }
                });
        lvFlights.setAdapter(adapter);
        adapter.addAll(flightEntList);
        adapter.notifyDataSetChanged();

//        lvFlights.setOnScrollListener(new AbsListView.OnScrollListener(){
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {}
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem,
//                                 int visibleItemCount, int totalItemCount) {
//
//                int lastInScreen = firstVisibleItem + visibleItemCount;
//                if((lastInScreen == totalItemCount) && !(loadingMore)){
//                    getData();
//                }
//            }
//        });

    }

    private void setSortSpinnerData() {

        sort_list = new ArrayList<>();
        sort_list.add("Sort by");
        sort_list.add("A to Z");
        sort_list.add("Z to A");
        //sort_list.add("Recently Added");

        sortAdapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner_item_2, sort_list);
        sortAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_2);
        spSort.setAdapter(sortAdapter);
        spSort.setSelection(0);

        sortAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.ivClose)
    public void onViewClicked() {
        if (edtSearch.getText().toString().length() == 0) {
            llSearch.setVisibility(View.GONE);
            llSpinner.setVisibility(View.VISIBLE);
            Utils.HideKeyBoard(getDockActivity());
        } else {
            edtSearch.setText("");
        }
    }

    @Override
    public void itemClicked(Object object, int position) {
        pageIndex = 0;
        flightListEnt = (FlightListEnt) object;
    }

    @Override
    public void itemClicked(Object object, boolean isfrom, int i) {

    }

    public void getFlightsList() {

        FlightSearchModel flightSearchModel = prefHelper.getFlightSearchModel();

        serviceHelper.enqueueCall(webService.GetFlightList(
                flightSearchModel.getSourceLocationCode(),
                flightSearchModel.getDestinationLocationCode(),
                flightSearchModel.getDepartureDate(),
                flightSearchModel.getReturnDate(),
                flightSearchModel.getNofAdult(),
                flightSearchModel.getNofChild(),
                flightSearchModel.getNofInfant(),
//                flightSearchModel.getCurrencyCode(),
                prefHelper.getUser().getUser().getCurrencyCode(),
                flightSearchModel.getLanguageCode(),
                flightSearchModel.getDirection(),
                flightSearchModel.getCabinType(),
                flightSearchModel.getNoOfStops(),
                flightSearchModel.getDuration(),
                flightSearchModel.getDepartureTime(),
                flightSearchModel.getArrivalTime(),
                flightSearchModel.getPriceRange(),
                flightSearchModel.getAirlineCodes(),
                pageIndex,
                prefHelper.getUser().getAuthToken()
        ), WebServiceConstants.GetFlightList);
    }

    private void pagenation() {

        lvFlights.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) {
                        //to avoid multiple calls for last item
                        pageIndex++;
                        getFlightsList();
                        preLast = lastItem;
                    }
                }
            }
        });

    }
}
