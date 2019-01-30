package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.app.amyal.R;
import com.app.amyal.entities.AirlinesEnt;
import com.app.amyal.entities.FlightListEnt;
import com.app.amyal.entities.FlightSearchModel;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.Utils;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.RangeSeekBar;
import com.app.amyal.ui.views.TitleBar;
import com.github.florent37.androidslidr.DurationSlidr;
import com.github.florent37.androidslidr.Slidr;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class FlightFilterFragment extends BaseFragment {

    @BindView(R.id.sbDuration)
    Slidr sbDuration;
    @BindView(R.id.llDuration)
    LinearLayout llDuration;
    @BindView(R.id.spStops)
    Spinner spStops;
    @BindView(R.id.llStopsSpinner)
    LinearLayout llStopsSpinner;
    @BindView(R.id.sbDepartureTime)
    DurationSlidr sbDepartureTime;
    @BindView(R.id.llDepartureTime)
    LinearLayout llDepartureTime;
    @BindView(R.id.sbArrivalTime)
    DurationSlidr sbArrivalTime;
    @BindView(R.id.llArrivalTime)
    LinearLayout llArrivalTime;
    @BindView(R.id.spFlights)
    Spinner spFlights;
    @BindView(R.id.llFlightsSpinner)
    LinearLayout llFlightsSpinner;
    Unbinder unbinder;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    ArrayList<String> stopsList = new ArrayList<>();
    ArrayAdapter<String> stopsAdapter;

    ArrayList<String> airLinesList = new ArrayList<>();
    ArrayAdapter<String> airLinesAdapter;
    @BindView(R.id.rvPrice)
    RangeSeekBar rvPrice;
    @BindView(R.id.tvStartPrice)
    AnyTextView tvStartPrice;
    @BindView(R.id.tvEndPrice)
    AnyTextView tvEndPrice;
    @BindView(R.id.llPriceRange)
    LinearLayout llPriceRange;

    String minPrice = ""; //0
    String maxPrice = ""; //10000

    String stop = "";

    String duration = ""; //0H 10M
    String depTime = ""; //01:00
    String ariTime = "";  //01:00

    List<AirlinesEnt> airlinesEnts;
    String AirlineId = "";

    ItemClickListener itemClickListener;

    public void setListner(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public static FlightFilterFragment newInstance() {
        return new FlightFilterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.showRightButton(R.drawable.reload, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reset();
                //UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.will_be_implemented_in_beta));
            }
        });
        titleBar.setSubHeading(getString(R.string.filter));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        getMainActivity().setBackground(R.drawable.bg_flight);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setStopsSpinnerData();

        serviceHelper.enqueueCall(webService.GetAirlines(), WebServiceConstants.GetAirlines);

        tvStartPrice.setText(prefHelper.getFlightSearchModel().getCurrencyCode() + " "+String.valueOf(0) + ".00");
        tvEndPrice.setText(prefHelper.getFlightSearchModel().getCurrencyCode() + " "+String.valueOf(10000) + ".00");

        rvPrice.setNotifyWhileDragging(true);
        rvPrice.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Number minValue, Number maxValue) {
                tvStartPrice.setText(prefHelper.getFlightSearchModel().getCurrencyCode() + " "+String.valueOf(minValue) + ".00");
                tvEndPrice.setText(prefHelper.getFlightSearchModel().getCurrencyCode() + " "+String.valueOf(maxValue) + ".00");
                minPrice = String.valueOf(minValue);
                maxPrice = String.valueOf(maxValue);
            }
        });

        spStops.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if(position == 0){

                    stop = "";

                }else{

                    stop = position-1+"";

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                stop = "";
            }
        });

        spFlights.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){

                    AirlineId = "";

                }else{

                    AirlineId = airlinesEnts.get(position-1).getIATA();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        sbDuration.setListener(new Slidr.Listener() {
            @Override
            public void valueChanged(Slidr slidr, float currentValue) {

                if(currentValue < 60){
                    duration = String.format("0H %dM", (int) currentValue);
                }else{
                    duration  = String.format("%dH 0M", (int) currentValue/60);
                }

            }
        });

        sbDepartureTime.setListener(new DurationSlidr.Listener() {
            @Override
            public void valueChanged(DurationSlidr slidr, float currentValue) {

                if(currentValue < 10){
                    depTime = "0" +(int)currentValue+":00";
                }else{
                    depTime = (int)currentValue+":00";
                }


            }
        });

        sbArrivalTime.setListener(new DurationSlidr.Listener() {
            @Override
            public void valueChanged(DurationSlidr slidr, float currentValue) {
                if(currentValue < 10){
                    ariTime = "0" +(int)currentValue+":00";
                }else{
                    ariTime = (int)currentValue+":00";
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void Reset(){

        //spStops.setSelection(0);
        rvPrice.setSelectedMinValue(0);
        rvPrice.setSelectedMaxValue(10000);
        tvStartPrice.setText(prefHelper.getFlightSearchModel().getCurrencyCode() + " "+ String.valueOf(0) + ".00");
        tvEndPrice.setText(prefHelper.getFlightSearchModel().getCurrencyCode() + " "+ String.valueOf(10000) + ".00");
        String minPrice = ""; //0
        String maxPrice = ""; //10000
        sbDuration.setCurrentValue(10);
        sbDepartureTime.setCurrentValue(1);
        sbArrivalTime.setCurrentValue(1);

        String stop = "";
        duration = ""; //0H 10M
        depTime = ""; //01:00
        ariTime = ""; //01:00
        AirlineId = "";

    }

    public void getFlightsList(FlightSearchModel flightSearchModel) {

        prefHelper.putFlightSearchModel(flightSearchModel);

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
                0,
                prefHelper.getUser().getAuthToken()
        ), WebServiceConstants.GetFlightList);
    }


    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.GetFlightList:

                FlightListEnt flightListEnt = (FlightListEnt) result;

                if (flightListEnt != null && itemClickListener!= null) {
                    itemClickListener.itemClicked(flightListEnt, 0);
                    getDockActivity().popFragment();
                }

                break;

            case WebServiceConstants.GetAirlines:

                airlinesEnts = (List<AirlinesEnt>) result;

                if (airlinesEnts != null && airlinesEnts.size() > 0) {
                    setAirlinesSpinnerData(airlinesEnts);
                }

                break;
        }
    }

    private void setStopsSpinnerData() {

        stopsList = new ArrayList<>();
        stopsList.add("Any");
        stopsList.add("0 Stop");
        stopsList.add("1 Stop");
        stopsList.add("2 Stops");
        stopsList.add("3 Stops");

        stopsAdapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner_item, stopsList);
        stopsAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spStops.setAdapter(stopsAdapter);
        spStops.setSelection(0);
        stopsAdapter.notifyDataSetChanged();

    }

    private void setAirlinesSpinnerData(List<AirlinesEnt> airlinesEnts) {

        airLinesList = new ArrayList<>();

        airLinesList.add("All");

        for(AirlinesEnt airlinesEnt : airlinesEnts){
            airLinesList.add(airlinesEnt.getName());
        }

        /*airLinesList.add("American Airlines");
        airLinesList.add("Emirates");
        airLinesList.add("Alaska Airlines");*/

        airLinesAdapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner_item, airLinesList);
        airLinesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spFlights.setAdapter(airLinesAdapter);
        spFlights.setSelection(0);

        airLinesAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {

        getFlightsList(new FlightSearchModel(prefHelper.getFlightSearchModel().getSourceLocationCode(),prefHelper.getFlightSearchModel().getDestinationLocationCode(),prefHelper.getFlightSearchModel().getDepartureDate(),
                prefHelper.getFlightSearchModel().getReturnDate(), prefHelper.getFlightSearchModel().getNofAdult(),prefHelper.getFlightSearchModel().getNofChild()
                ,prefHelper.getFlightSearchModel().getNofInfant(),prefHelper.getFlightSearchModel().getCurrencyCode(),prefHelper.getFlightSearchModel().getLanguageCode(),
                prefHelper.getFlightSearchModel().getDirection(),prefHelper.getFlightSearchModel().getCabinType(),stop,duration,depTime,
                ariTime,AirlineId,prefHelper.getFlightSearchModel().getRatePlanCode(),prefHelper.getFlightSearchModel().getSequenceNumber(),prefHelper.getFlightSearchModel().getFromLocationCode(),
                prefHelper.getFlightSearchModel().getToLocationCode(),minPrice + "-" + maxPrice));

    }
}
