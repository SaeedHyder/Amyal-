package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.app.amyal.R;
import com.app.amyal.entities.FlightListEnt;
import com.app.amyal.entities.FlightSearchModel;
import com.app.amyal.entities.FlightTypeEnt;
import com.app.amyal.entities.HotelAreaEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.DateHelper;
import com.app.amyal.helpers.DialogHelper;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.interfaces.IDateSelectListner;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.ui.adapters.RecyclerViewAdapter;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.AnyTextView1;
import com.app.amyal.ui.views.TitleBar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFlightsFragment extends BaseFragment implements ItemClickListener, IDateSelectListner {

    @BindView(R.id.tvRoundTrip)
    AnyTextView tvRoundTrip;
    @BindView(R.id.tvOneWay)
    AnyTextView tvOneWay;
    @BindView(R.id.tvFrom)
    AnyTextView tvFrom;
    @BindView(R.id.tvFromCityOrPort)
    AnyTextView tvFromCityOrPort;
    @BindView(R.id.llFrom)
    LinearLayout llFrom;
    @BindView(R.id.tvTo)
    AnyTextView tvTo;
    @BindView(R.id.tvToCityOrPort)
    AnyTextView tvToCityOrPort;
    @BindView(R.id.llTo)
    LinearLayout llTo;
    @BindView(R.id.ivSwap)
    ImageView ivSwap;
    @BindView(R.id.tvSelectDates)
    AnyTextView tvSelectDates;
    @BindView(R.id.tvFromDate)
    AnyTextView tvFromDate;
    @BindView(R.id.tvToDate)
    AnyTextView tvToDate;
    @BindView(R.id.tvTravellers)
    AnyTextView tvTravellers;
    @BindView(R.id.tvNoOfAdult)
    AnyTextView tvNoOfAdult;
    @BindView(R.id.tvNoOfChild)
    AnyTextView tvNoOfChild;
    @BindView(R.id.tvNoOfInfant)
    AnyTextView tvNoOfInfant;
    @BindView(R.id.rvFlightType)
    RecyclerView rvFlightType;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    Unbinder unbinder;
    @BindView(R.id.llSelectDates)
    LinearLayout llSelectDates;
    @BindView(R.id.llTravellers)
    LinearLayout llTravellers;
    @BindView(R.id.ivEconomy)
    ImageView ivEconomy;

    @BindView(R.id.ivBusiness)
    ImageView ivBusiness;
    @BindView(R.id.llBusiness)
    LinearLayout llBusiness;
    @BindView(R.id.ivFirst)
    ImageView ivFirst;
    @BindView(R.id.llFirst)
    LinearLayout llFirst;
    @BindView(R.id.llEconomy)
    LinearLayout llEconomy;
    @BindView(R.id.ivEconomyPrem)
    ImageView ivEconomyPrem;
    @BindView(R.id.tvEconomyPrem)
    AnyTextView1 tvEconomyPrem;
    @BindView(R.id.llEconomyPrem)
    LinearLayout llEconomyPrem;
    @BindView(R.id.tvBusiness)
    AnyTextView1 tvBusiness;
    @BindView(R.id.ivBusinessPrem)
    ImageView ivBusinessPrem;
    @BindView(R.id.tvBusinessPrem)
    AnyTextView tvBusinessPrem;
    @BindView(R.id.llBusinessPrem)
    LinearLayout llBusinessPrem;
    @BindView(R.id.tvFirst)
    AnyTextView tvFirst;
    @BindView(R.id.ivFirstPrem)
    ImageView ivFirstPrem;
    @BindView(R.id.tvFirstPrem)
    AnyTextView tvFirstPrem;
    @BindView(R.id.llFirstPerm)
    LinearLayout llFirstPerm;
    @BindView(R.id.tvEconomy)
    AnyTextView1 tvEconomy;
    @BindView(R.id.spStops)
    Spinner spStops;
    @BindView(R.id.llStopsSpinner)
    LinearLayout llStopsSpinner;
    boolean isRondTrip = true;
    int nofAdult = 1;
    int nofChild = 0;
    int nofInfant = 0;
    View view = null;
    HotelAreaEnt flightlFromAreaEnt;
    HotelAreaEnt flightlToAreaEnt;
    Date leftDate;
    Date rightDate;
    String CabinType = AppConstants.CabinType.Economy.toString();
    String Direction = AppConstants.Directions.RoundTrip.toString();
    ArrayList<String> stopsList = new ArrayList<>();
    ArrayAdapter<String> stopsAdapter;
    String stop = "";
    private RecyclerViewAdapter<FlightTypeEnt> rvAdapter;
    private List<FlightTypeEnt> flightTypeEntList;

    public HomeFlightsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setSubHeadingImage(R.drawable.logotop);
        titleBar.showNotificationButton(0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (view == null)
            view = inflater.inflate(R.layout.fragmnet_home_flight, container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setStopsSpinnerData();
        spStops.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 0) {

                    stop = "";

                } else {

                    stop = position - 1 + "";

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                stop = "";
            }
        });

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
                FlightSearchModel flightSearchModel = prefHelper.getFlightSearchModel();
                flightSearchModel.setSequenceNumber(flightListEnt.getSequenceNumber());
                prefHelper.putFlightSearchModel(flightSearchModel);

                if (flightListEnt != null) {

                    SelectFlightFragment selectFlightFragment = new SelectFlightFragment();
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.FlightListEnt, new Gson().toJson(flightListEnt));
                    orderBundle.putBoolean(AppConstants.IsReturnTrip, isRondTrip);
                    orderBundle.putBoolean(AppConstants.IsInBound, false);
                    selectFlightFragment.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(selectFlightFragment, SelectFlightFragment.class.getSimpleName() + "OutBound");
                }

                break;
        }
    }


    @OnClick({R.id.tvRoundTrip, R.id.tvOneWay, R.id.llFrom, R.id.llTo, R.id.ivSwap, R.id.btnSearch, R.id.llSelectDates, R.id.llTravellers, R.id.llEconomy, R.id.llBusiness, R.id.llFirst, R.id.llEconomyPrem, R.id.llBusinessPrem, R.id.llFirstPerm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvRoundTrip:
                isRondTrip = true;
                tvRoundTrip.setBackgroundResource(R.drawable.toggle_selected);
                tvOneWay.setBackgroundResource(R.drawable.toggle_unselected);
                tvToDate.setVisibility(View.VISIBLE);
                tvSelectDates.setText(getString(R.string.selectDates));
                Direction = AppConstants.Directions.RoundTrip.toString();
                break;
            case R.id.tvOneWay:
                isRondTrip = false;
                tvOneWay.setBackgroundResource(R.drawable.toggle_selected);
                tvRoundTrip.setBackgroundResource(R.drawable.toggle_unselected);
                tvToDate.setVisibility(View.INVISIBLE);
                tvSelectDates.setText(getString(R.string.selectDate));
                Direction = AppConstants.Directions.Outbound.toString();
                break;
            case R.id.llFrom:

                HotelAreaFragment hotelAreaFragment = HotelAreaFragment.newInstance();
                hotelAreaFragment.setItemClickListener(this, true, true, false);
                getDockActivity().replaceDockableFragment(hotelAreaFragment, HotelAreaFragment.class.getSimpleName());

                /*FlightSearchFragment flightSearchFragment = FlightSearchFragment.newInstance();
                flightSearchFragment.setItemClickListener(this, true);*/
               /* Bundle orderBundle = new Bundle();
                orderBundle.putBoolean(AppConstants.IsReturnTrip,isRondTrip);
                selectFlightDates.setArguments(orderBundle);*/
                /* getDockActivity().replaceDockableFragment(flightSearchFragment, FlightSearchFragment.class.getSimpleName());*/


                break;
            case R.id.llTo:

                hotelAreaFragment = HotelAreaFragment.newInstance();
                hotelAreaFragment.setItemClickListener(this, false, true, false);
                getDockActivity().replaceDockableFragment(hotelAreaFragment, HotelAreaFragment.class.getSimpleName());

                /*flightSearchFragment = FlightSearchFragment.newInstance();
                flightSearchFragment.setItemClickListener(this, false);*/
               /* Bundle orderBundle = new Bundle();
                orderBundle.putBoolean(AppConstants.IsReturnTrip,isRondTrip);
                selectFlightDates.setArguments(orderBundle);*/
                /* getDockActivity().replaceDockableFragment(flightSearchFragment, FlightSearchFragment.class.getSimpleName());*/


                break;
            case R.id.ivSwap:

                if (tvFromCityOrPort.getText().toString() != null) {
                    String from = tvFromCityOrPort.getText().toString();
                    if (tvToCityOrPort.getText().toString() != null) {
                        String to = tvToCityOrPort.getText().toString();
                        tvFromCityOrPort.setText(to);
                        tvToCityOrPort.setText(from);
                    }
                }

                break;
            case R.id.btnSearch:

                //UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented_in_beta));
                if (tvFromCityOrPort.getText().toString() != null && tvFromCityOrPort.getText().toString().length() > 0) {
                    if (tvToCityOrPort.getText().toString() != null && tvToCityOrPort.getText().toString().length() > 0) {
                        if (tvFromDate.getText().toString() != null && tvFromDate.getText().toString().length() > 0) {
                            if ((isRondTrip && (tvToDate.getText().toString() != null && tvToDate.getText().toString().length() > 0)) || !isRondTrip) {
                                if (!tvFromCityOrPort.getText().toString().equalsIgnoreCase(tvToCityOrPort.getText().toString())) {

                                    String fromDate = "";
                                    String toDate = "";
                                    if (leftDate != null) {
                                        fromDate = DateHelper.dateFormat(leftDate.toString(), DateHelper.DATE_FORMAT3, DateHelper.DATE_TIME_FORMAT2);
                                    }
                                    if (rightDate != null) {
                                        toDate = DateHelper.dateFormat(rightDate.toString(), DateHelper.DATE_FORMAT3, DateHelper.DATE_TIME_FORMAT2);
                                    }

                                    String fromLocationCode = flightlFromAreaEnt.getZoneCode() + "";
                                    String toLocationCode = flightlToAreaEnt.getZoneCode() + "";

                                    String LangCode = AppConstants.LangCode;
                                    String CurCode = AppConstants.CurCode;

                                    if (prefHelper.isLogin()) {

                                        LangCode = prefHelper.getUser().getUser().getLanguageCode();
                                        CurCode = prefHelper.getUser().getUser().getCurrencyCode();

                                    }

                                    if (!isRondTrip) {
                                        toDate = fromDate;
                                    }

                                    getFlightsList(new FlightSearchModel(fromLocationCode, toLocationCode, fromDate, toDate, nofAdult, nofChild, nofInfant, CurCode, LangCode, Direction, CabinType, stop, "", "",
                                            "", "", "", "", flightlFromAreaEnt.getIATA(), flightlToAreaEnt.getIATA(), ""));


                                } else {
                                    UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.departure_arrival_cites_same));
                                }
                            } else {
                                UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_return_date));
                            }
                        } else {
                            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_dep_date));
                        }
                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_travel_to));
                    }
                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_travel_from));
                }

                break;

            case R.id.llSelectDates:
                SelectFlightDates selectFlightDates = SelectFlightDates.newInstance();
                selectFlightDates.setiDateSelectListner(this);
                Bundle orderBundle = new Bundle();
                orderBundle.putBoolean(AppConstants.IsReturnTrip, isRondTrip);
                selectFlightDates.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(selectFlightDates, SelectFlightDates.class.getSimpleName());
                break;

            case R.id.llTravellers:

                final DialogHelper travellerDialog = new DialogHelper(getDockActivity());
                travellerDialog.initTravellerDialog(R.layout.dialog_no_of_travellers, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        nofAdult = Integer.parseInt(travellerDialog.getTravellerInfo(R.id.tvNoOfAdult));
                        nofChild = Integer.parseInt(travellerDialog.getTravellerInfo(R.id.tvNoOfChild));
                        nofInfant = Integer.parseInt(travellerDialog.getTravellerInfo(R.id.tvNoOfInfant));

                        tvNoOfAdult.setText(nofAdult + " Adult, ");
                        tvNoOfChild.setText(nofChild + " Child, ");
                        tvNoOfInfant.setText(nofInfant + " Infant");

                        travellerDialog.hideDialog();
                    }
                });
                travellerDialog.setCancelable(false);
                travellerDialog.showDialog();

                break;

            case R.id.llEconomy:

                CabinType = AppConstants.CabinType.Economy.toString();

                ivEconomy.setImageResource(R.drawable.economy1);
                //llEconomy.setBackgroundResource(R.drawable.selecter_rounded);
                llEconomy.setBackgroundResource(R.color.transparent);
                tvEconomy.setTextColor(getResources().getColor(R.color.darkBrown));

                ivEconomyPrem.setImageResource(R.drawable.premium_economy);
                llEconomyPrem.setBackgroundResource(R.color.transparent);
                tvEconomyPrem.setTextColor(getResources().getColor(R.color.dark_grey));

                ivBusiness.setImageResource(R.drawable.business);
                llBusiness.setBackgroundResource(R.color.transparent);
                tvBusiness.setTextColor(getResources().getColor(R.color.dark_grey));

                ivBusinessPrem.setImageResource(R.drawable.premium_business);
                llBusinessPrem.setBackgroundResource(R.color.transparent);
                tvBusinessPrem.setTextColor(getResources().getColor(R.color.dark_grey));

                ivFirst.setImageResource(R.drawable.first);
                llFirst.setBackgroundResource(R.color.transparent);
                tvFirst.setTextColor(getResources().getColor(R.color.dark_grey));

                ivFirstPrem.setImageResource(R.drawable.premium_first);
                llFirstPerm.setBackgroundResource(R.color.transparent);
                tvFirstPrem.setTextColor(getResources().getColor(R.color.dark_grey));

                break;

            case R.id.llEconomyPrem:

                CabinType = AppConstants.CabinType.PremiumEconomy.toString();

                ivEconomy.setImageResource(R.drawable.economy);
                llEconomy.setBackgroundResource(R.color.transparent);
                tvEconomy.setTextColor(getResources().getColor(R.color.dark_grey));

                ivEconomyPrem.setImageResource(R.drawable.premium_economy1);
                //llEconomyPrem.setBackgroundResource(R.drawable.selecter_rounded);
                llEconomyPrem.setBackgroundResource(R.color.transparent);
                tvEconomyPrem.setTextColor(getResources().getColor(R.color.darkBrown));

                ivBusiness.setImageResource(R.drawable.business);
                llBusiness.setBackgroundResource(R.color.transparent);
                tvBusiness.setTextColor(getResources().getColor(R.color.dark_grey));

                ivBusinessPrem.setImageResource(R.drawable.premium_business);
                llBusinessPrem.setBackgroundResource(R.color.transparent);
                tvBusinessPrem.setTextColor(getResources().getColor(R.color.dark_grey));

                ivFirst.setImageResource(R.drawable.first);
                llFirst.setBackgroundResource(R.color.transparent);
                tvFirst.setTextColor(getResources().getColor(R.color.dark_grey));

                ivFirstPrem.setImageResource(R.drawable.premium_first);
                llFirstPerm.setBackgroundResource(R.color.transparent);
                tvFirstPrem.setTextColor(getResources().getColor(R.color.dark_grey));

                break;

            case R.id.llBusiness:

                CabinType = AppConstants.CabinType.Business.toString();

                ivEconomy.setImageResource(R.drawable.economy);
                llEconomy.setBackgroundResource(R.color.transparent);
                tvEconomy.setTextColor(getResources().getColor(R.color.dark_grey));

                ivEconomyPrem.setImageResource(R.drawable.premium_economy);
                llEconomyPrem.setBackgroundResource(R.color.transparent);
                tvEconomyPrem.setTextColor(getResources().getColor(R.color.dark_grey));

                ivBusiness.setImageResource(R.drawable.business1);
                //llBusiness.setBackgroundResource(R.drawable.selecter_rounded);
                llBusiness.setBackgroundResource(R.color.transparent);
                tvBusiness.setTextColor(getResources().getColor(R.color.darkBrown));

                ivBusinessPrem.setImageResource(R.drawable.premium_business);
                llBusinessPrem.setBackgroundResource(R.color.transparent);
                tvBusinessPrem.setTextColor(getResources().getColor(R.color.dark_grey));

                ivFirst.setImageResource(R.drawable.first);
                llFirst.setBackgroundResource(R.color.transparent);
                tvFirst.setTextColor(getResources().getColor(R.color.dark_grey));

                ivFirstPrem.setImageResource(R.drawable.first);
                llFirstPerm.setBackgroundResource(R.color.transparent);
                tvFirstPrem.setTextColor(getResources().getColor(R.color.dark_grey));

                break;

            case R.id.llBusinessPrem:

                CabinType = AppConstants.CabinType.PremiumBusiness.toString();

                ivEconomy.setImageResource(R.drawable.economy);
                llEconomy.setBackgroundResource(R.color.transparent);
                tvEconomy.setTextColor(getResources().getColor(R.color.dark_grey));

                ivEconomyPrem.setImageResource(R.drawable.premium_economy);
                llEconomyPrem.setBackgroundResource(R.color.transparent);
                tvEconomyPrem.setTextColor(getResources().getColor(R.color.dark_grey));

                ivBusiness.setImageResource(R.drawable.business);
                llBusiness.setBackgroundResource(R.color.transparent);
                tvBusiness.setTextColor(getResources().getColor(R.color.dark_grey));

                ivBusinessPrem.setImageResource(R.drawable.premium_business1);
                //llBusinessPrem.setBackgroundResource(R.drawable.selecter_rounded);
                llBusinessPrem.setBackgroundResource(R.color.transparent);
                tvBusinessPrem.setTextColor(getResources().getColor(R.color.darkBrown));

                ivFirst.setImageResource(R.drawable.first);
                llFirst.setBackgroundResource(R.color.transparent);
                tvFirst.setTextColor(getResources().getColor(R.color.dark_grey));

                ivFirstPrem.setImageResource(R.drawable.premium_first);
                llFirstPerm.setBackgroundResource(R.color.transparent);
                tvFirstPrem.setTextColor(getResources().getColor(R.color.dark_grey));

                break;

            case R.id.llFirst:

                CabinType = AppConstants.CabinType.First.toString();

                ivEconomy.setImageResource(R.drawable.economy);
                llEconomy.setBackgroundResource(R.color.transparent);
                tvEconomy.setTextColor(getResources().getColor(R.color.dark_grey));

                ivEconomyPrem.setImageResource(R.drawable.premium_economy);
                llEconomyPrem.setBackgroundResource(R.color.transparent);
                tvEconomyPrem.setTextColor(getResources().getColor(R.color.dark_grey));

                ivBusiness.setImageResource(R.drawable.business);
                llBusiness.setBackgroundResource(R.color.transparent);
                tvBusiness.setTextColor(getResources().getColor(R.color.dark_grey));

                ivBusinessPrem.setImageResource(R.drawable.premium_business);
                llBusinessPrem.setBackgroundResource(R.color.transparent);
                tvBusinessPrem.setTextColor(getResources().getColor(R.color.dark_grey));

                ivFirst.setImageResource(R.drawable.first1);
                //llFirst.setBackgroundResource(R.drawable.selecter_rounded);
                llFirst.setBackgroundResource(R.color.transparent);
                tvFirst.setTextColor(getResources().getColor(R.color.darkBrown));

                ivFirstPrem.setImageResource(R.drawable.premium_first);
                llFirstPerm.setBackgroundResource(R.color.transparent);
                tvFirstPrem.setTextColor(getResources().getColor(R.color.dark_grey));


                break;

            case R.id.llFirstPerm:

                CabinType = AppConstants.CabinType.PremiumFirst.toString();

                ivEconomy.setImageResource(R.drawable.economy);
                llEconomy.setBackgroundResource(R.color.transparent);
                tvEconomy.setTextColor(getResources().getColor(R.color.dark_grey));

                ivEconomyPrem.setImageResource(R.drawable.premium_economy);
                llEconomyPrem.setBackgroundResource(R.color.transparent);
                tvEconomyPrem.setTextColor(getResources().getColor(R.color.dark_grey));

                ivBusiness.setImageResource(R.drawable.business);
                llBusiness.setBackgroundResource(R.color.transparent);
                tvBusiness.setTextColor(getResources().getColor(R.color.dark_grey));

                ivBusinessPrem.setImageResource(R.drawable.premium_business);
                llBusinessPrem.setBackgroundResource(R.color.transparent);
                tvBusinessPrem.setTextColor(getResources().getColor(R.color.dark_grey));

                ivFirst.setImageResource(R.drawable.first);
                llFirst.setBackgroundResource(R.color.transparent);
                tvFirst.setTextColor(getResources().getColor(R.color.dark_grey));

                ivFirstPrem.setImageResource(R.drawable.premium_first1);
                //llFirstPerm.setBackgroundResource(R.drawable.selecter_rounded);
                llFirstPerm.setBackgroundResource(R.color.transparent);
                tvFirstPrem.setTextColor(getResources().getColor(R.color.darkBrown));


                break;
        }
    }

    @Override
    public void itemClicked(Object object, int position) {

        for (int i = 0; i < flightTypeEntList.size(); i++) {
            flightTypeEntList.get(i).setSelected(false);
        }

        flightTypeEntList.get(position).setSelected(true);

        //rvAdapter.notifyItemChanged(position);
        rvAdapter.notifyDataSetChanged();

    }

    @Override
    public void itemClicked(Object object, boolean isfrom, int i) {

        if (isfrom) {
            flightlFromAreaEnt = (HotelAreaEnt) object;
            tvFromCityOrPort.setText(flightlFromAreaEnt.getName() + " - " + flightlFromAreaEnt.getIATA());
        } else {
            flightlToAreaEnt = (HotelAreaEnt) object;
            tvToCityOrPort.setText(flightlToAreaEnt.getName() + " - " + flightlToAreaEnt.getIATA());
        }

    }

    @Override
    public void onDatesSelected(String fromDate, String toDate) {

        if (fromDate != null)
            tvFromDate.setText(fromDate);

        if (toDate != null)
            tvToDate.setText("- " + toDate);

    }

    @Override
    public void onDateSelected(String fromDate, Date froDate) {

        if (fromDate != null) {
            tvFromDate.setText(fromDate);
            leftDate = froDate;
        }
    }

    @Override
    public void onDatesSelected(String fromDate, String toDate, Date todate, Date fromdate) {


        if (fromDate != null) {
            tvFromDate.setText(fromDate);
            leftDate = todate;
        }

        if (toDate != null) {
            tvToDate.setText("- " + toDate);
            rightDate = fromdate;
        }

    }

}
