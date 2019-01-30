package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.app.amyal.R;
import com.app.amyal.entities.FlightDetailsEnt;
import com.app.amyal.entities.FlightStop;
import com.app.amyal.entities.FlightStopEnt;
import com.app.amyal.entities.FlightsList;
import com.app.amyal.entities.ReturnFlightStop;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.Utils;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.DateHelper;
import com.app.amyal.helpers.DialogHelper;
import com.app.amyal.retrofit.GsonFactory;
import com.app.amyal.ui.adapters.ArrayListAdapter;
import com.app.amyal.ui.binders.BinderFlightStop;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.CustomRatingBar;
import com.app.amyal.ui.views.TitleBar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by khan_muhammad on 11/28/2017.
 */

public class RoundTripFlightDetailsFragment extends BaseFragment {

    @BindView(R.id.tvOutbound)
    AnyTextView tvOutbound;
    @BindView(R.id.tvOutboundDate)
    AnyTextView tvOutboundDate;
    @BindView(R.id.lvFlightStopsOutbound)
    ListView lvFlightStopsOutbound;
    @BindView(R.id.tvInbound)
    AnyTextView tvInbound;
    @BindView(R.id.tvInboundDate)
    AnyTextView tvInboundDate;
    @BindView(R.id.lvFlightStopsInbound)
    ListView lvFlightStopsInbound;
    @BindView(R.id.btnCheckout)
    Button btnCheckout;
    Unbinder unbinder;
    @BindView(R.id.llDeparture)
    LinearLayout llDeparture;
    @BindView(R.id.tvOutboundMoreOrLess)
    AnyTextView tvOutboundMoreOrLess;
    @BindView(R.id.llReturn)
    LinearLayout llReturn;
    @BindView(R.id.tvInboundMoreOrLess)
    AnyTextView tvInboundMoreOrLess;
    @BindView(R.id.tvTripDates)
    AnyTextView tvTripDates;
    @BindView(R.id.tvOutboundTime)
    AnyTextView tvOutboundTime;
    @BindView(R.id.tvStop1OutBound)
    AnyTextView tvStop1OutBound;
    @BindView(R.id.tvStop2OutBound)
    AnyTextView tvStop2OutBound;
    @BindView(R.id.tvStop3OutBound)
    AnyTextView tvStop3OutBound;
    @BindView(R.id.tvFlightDuraionOutBound)
    AnyTextView tvFlightDuraionOutBound;
    @BindView(R.id.tvFlightTypeOutBound)
    AnyTextView tvFlightTypeOutBound;
    @BindView(R.id.tvInboundTime)
    AnyTextView tvInboundTime;
    @BindView(R.id.tvStop1InBound)
    AnyTextView tvStop1InBound;
    @BindView(R.id.tvStop2InBound)
    AnyTextView tvStop2InBound;
    @BindView(R.id.tvStop3InBound)
    AnyTextView tvStop3InBound;
    @BindView(R.id.tvFlightDuraionInBound)
    AnyTextView tvFlightDuraionInBound;
    @BindView(R.id.tvFlightTypeInBound)
    AnyTextView tvFlightTypeInBound;
    @BindView(R.id.ivAirline)
    ImageView ivAirline;
    @BindView(R.id.tvAirlineName)
    AnyTextView tvAirlineName;
    @BindView(R.id.tvTotalDuaration)
    AnyTextView tvTotalDuaration;
    @BindView(R.id.tvTotalDuarationValue)
    AnyTextView tvTotalDuarationValue;
    @BindView(R.id.CrRating)
    CustomRatingBar CrRating;
    @BindView(R.id.tvPrice)
    AnyTextView tvPrice;
    @BindView(R.id.tvPerson)
    AnyTextView tvPerson;
    @BindView(R.id.tvFlightDate)
    AnyTextView tvFlightDate;
    boolean isShowOutboundDetail = false;
    boolean isShowInboundDetail = false;
    FlightsList flightsList;
    String OutboundDuration = "";
    String InboundDuration = "";
    private List<FlightStopEnt> flightStopOutboundEntList;
    private ArrayListAdapter<FlightStopEnt> flightStopOutboundAdapter;
    private List<FlightStopEnt> flightStopInboundEntList;
    private ArrayListAdapter<FlightStopEnt> flightStopInboundAdapter;

    public static RoundTripFlightDetailsFragment newInstance() {

        return new RoundTripFlightDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        flightStopOutboundAdapter = new ArrayListAdapter<>(getDockActivity(), new BinderFlightStop(getDockActivity(), prefHelper));
        flightStopInboundAdapter = new ArrayListAdapter<>(getDockActivity(), new BinderFlightStop(getDockActivity(), prefHelper));

        if (getArguments() != null) {
            flightsList = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.FlightsList), FlightsList.class);
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);

        titleBar.hideButtons();
        titleBar.showBackButton();

        titleBar.setSubHeading("Trip to " + prefHelper.getFlightSearchModel().getToLocationCode());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_round_trip_details, container, false);

        getMainActivity().setBackground(R.drawable.bg_flight);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (flightsList != null && flightsList.getFlightStop().size() > 0 && flightsList.getReturnFlightStop().size() > 0) {

            bindDataOutbound(flightsList.getFlightStop());

            bindDataInbound(flightsList.getReturnFlightStop());

            Utils.justifyListViewHeightBasedOnChildren(lvFlightStopsOutbound);
            Utils.justifyListViewHeightBasedOnChildren(lvFlightStopsInbound);

            tvOutbound.setText(prefHelper.getFlightSearchModel().getFromLocationCode() + " to " + prefHelper.getFlightSearchModel().getToLocationCode());
            tvOutboundDate.setText(prefHelper.getFlightSearchModel().getDepartureDate());

           /* String startTime = "";
            String endTime = "";

            startTime = DateHelper.dateFormat(stop.getDepartureDateTime(), DateHelper.TIME_FORMAT2, DateHelper.DATE_TIME_FORMAT2);
            endTime = DateHelper.dateFormat(stop.getArrivalDateTime(), DateHelper.TIME_FORMAT2, DateHelper.DATE_TIME_FORMAT2);*/


            tvInbound.setText(prefHelper.getFlightSearchModel().getToLocationCode() + " to " + prefHelper.getFlightSearchModel().getFromLocationCode());
            tvInboundDate.setText(prefHelper.getFlightSearchModel().getReturnDate());

            String StartDate = DateHelper.dateFormat(prefHelper.getFlightSearchModel().getDepartureDate(), DateHelper.DATE_FORMAT4, DateHelper.DATE_FORMAT3);
            String EndDate = DateHelper.dateFormat(prefHelper.getFlightSearchModel().getReturnDate(), DateHelper.DATE_FORMAT4, DateHelper.DATE_FORMAT3);

            tvTripDates.setText(StartDate + " - " + EndDate);

            tvFlightTypeOutBound.setText(flightsList.getFlightStop().get(0).getCabinType());

            String numberOfStops = AppConstants.Nonstop;
            if (Integer.parseInt(flightsList.getNoOfStops()) > 0) {
                numberOfStops = Integer.parseInt(flightsList.getNoOfStops()) > 1 ? flightsList.getNoOfStops() + " Stops" : flightsList.getNoOfStops() + " Stop";
            }

            String Duration = flightsList.getFlightStop().get(0).getDuration().replace("H", "H ");

            String val = String.format(Locale.US, "%s (%s)", Duration, numberOfStops);

            tvFlightDuraionOutBound.setText(val);

            OutboundDuration = Duration;

            tvFlightTypeInBound.setText(flightsList.getReturnFlightStop().get(0).getCabinType());

            numberOfStops = AppConstants.Nonstop;
            if (Integer.parseInt(flightsList.getReturnNoOfStops()) > 0) {
                numberOfStops = Integer.parseInt(flightsList.getReturnNoOfStops()) > 1 ? flightsList.getReturnNoOfStops() + " Stops" : flightsList.getReturnNoOfStops() + " Stop";
            }

            Duration = flightsList.getReturnFlightStop().get(0).getDuration().replace("H", "H ");

            val = String.format(Locale.US, "%s (%s)", Duration, numberOfStops);

            tvFlightDuraionInBound.setText(val);

            InboundDuration = Duration;

            if (flightsList.getFlightStop() != null && flightsList.getFlightStop().size() > 0) {

                Date BaseDay = DateHelper.dateFormatNew(flightsList.getFlightStop().get(0).getDepartureDateTime(), DateHelper.DATE_FORMAT, DateHelper.DATE_TIME_FORMAT);

                Date EndDay = DateHelper.dateFormatNew(flightsList.getFlightStop().get(flightsList.getFlightStop().size() - 1).getArrivalDateTime(), DateHelper.DATE_FORMAT, DateHelper.DATE_TIME_FORMAT);
                int Diff = Utils.getDaysDifference(BaseDay, EndDay);

                String startTime = DateHelper.dateFormat(flightsList.getFlightStop().get(0).getDepartureDateTime(), DateHelper.TIME_FORMAT2, DateHelper.DATE_TIME_FORMAT);
                String endTime = DateHelper.dateFormat(flightsList.getFlightStop().get(flightsList.getFlightStop().size() - 1).getArrivalDateTime(), DateHelper.TIME_FORMAT2, DateHelper.DATE_TIME_FORMAT);

                String time = "";
                if (Diff > 0) {
                    time = startTime + " - " + endTime + " + " + Diff + " d";
                } else {
                    time = startTime + " - " + endTime;
                }

                tvOutboundTime.setText(time);

                if (flightsList.getFlightStop().size() == 1) {

                    tvStop1OutBound.setText(flightsList.getFlightStop().get(0).getDepartureAirportCode());
                    tvStop2OutBound.setText(flightsList.getFlightStop().get(0).getArrivalAirportCode());
                    tvStop3OutBound.setVisibility(View.GONE);

                } else if (flightsList.getFlightStop().size() == 2) {
                    tvStop3OutBound.setVisibility(View.VISIBLE);
                    tvStop1OutBound.setText(flightsList.getFlightStop().get(0).getDepartureAirportCode());
                    tvStop2OutBound.setText(flightsList.getFlightStop().get(0).getArrivalAirportCode());
                    tvStop3OutBound.setText(flightsList.getFlightStop().get(1).getArrivalAirportCode());
                } else if (flightsList.getFlightStop().size() > 2) {
                    tvStop3OutBound.setVisibility(View.VISIBLE);
                    tvStop1OutBound.setText(flightsList.getFlightStop().get(0).getDepartureAirportCode());
                    tvStop2OutBound.setText(flightsList.getFlightStop().get(0).getArrivalAirportCode());
                    tvStop3OutBound.setText(flightsList.getFlightStop().get(1).getArrivalAirportCode() + " ...");
                }


                tvAirlineName.setText(flightsList.getAirlineName());
                if (prefHelper.getUser().getUser().getCurrencyCode().equals(flightsList.getCurrencyCode()))
                    tvPrice.setText(flightsList.getCurrencyCode() + " " + flightsList.getAmount());
                else
                    tvPrice.setText(prefHelper.getUser().getUser().getCurrencyCode() + " " + prefHelper.getCurrency().getSAR() * flightsList.getAmount());

                tvTotalDuarationValue.setText(flightsList.getTotalDuration());

                String Date = DateHelper.dateFormat(prefHelper.getFlightSearchModel().getDepartureDate(), DateHelper.DATE_FORMAT4, DateHelper.DATE_FORMAT3);
                tvFlightDate.setText(Date);

                if (flightsList.getImage() != null && flightsList.getImage().length() > 0) {
                    Picasso.with(getDockActivity())
                            .load(flightsList.getImage())
                            .into(ivAirline);
                }

                if (flightsList.getRating() != null && flightsList.getRating().length() > 0) {
                    CrRating.setScore(Float.parseFloat(flightsList.getRating()));
                } else {
                    CrRating.setVisibility(View.GONE);
                }

            }

            if (flightsList.getReturnFlightStop() != null && flightsList.getReturnFlightStop().size() > 0) {

                Date BaseDay = DateHelper.dateFormatNew(flightsList.getReturnFlightStop().get(0).getDepartureDateTime(), DateHelper.DATE_FORMAT, DateHelper.DATE_TIME_FORMAT);

                Date EndDay = DateHelper.dateFormatNew(flightsList.getReturnFlightStop().get(flightsList.getReturnFlightStop().size() - 1).getArrivalDateTime(), DateHelper.DATE_FORMAT, DateHelper.DATE_TIME_FORMAT);
                int Diff = Utils.getDaysDifference(BaseDay, EndDay);

                String startTime = DateHelper.dateFormat(flightsList.getReturnFlightStop().get(0).getDepartureDateTime(), DateHelper.TIME_FORMAT2, DateHelper.DATE_TIME_FORMAT);
                String endTime = DateHelper.dateFormat(flightsList.getReturnFlightStop().get(flightsList.getReturnFlightStop().size() - 1).getArrivalDateTime(), DateHelper.TIME_FORMAT2, DateHelper.DATE_TIME_FORMAT);

                String time = "";
                if (Diff > 0) {
                    time = startTime + " - " + endTime + " + " + Diff + " d";
                } else {
                    time = startTime + " - " + endTime;
                }

                tvInboundTime.setText(time);

                if (flightsList.getReturnFlightStop().size() == 1) {

                    tvStop1InBound.setText(flightsList.getReturnFlightStop().get(0).getDepartureAirportCode());
                    tvStop2InBound.setText(flightsList.getReturnFlightStop().get(0).getArrivalAirportCode());
                    tvStop3InBound.setVisibility(View.GONE);

                } else if (flightsList.getReturnFlightStop().size() == 2) {
                    tvStop3InBound.setVisibility(View.VISIBLE);
                    tvStop1InBound.setText(flightsList.getReturnFlightStop().get(0).getDepartureAirportCode());
                    tvStop2InBound.setText(flightsList.getReturnFlightStop().get(0).getArrivalAirportCode());
                    tvStop3InBound.setText(flightsList.getReturnFlightStop().get(1).getArrivalAirportCode());
                } else if (flightsList.getReturnFlightStop().size() > 2) {
                    tvStop3InBound.setVisibility(View.VISIBLE);
                    tvStop1InBound.setText(flightsList.getReturnFlightStop().get(0).getDepartureAirportCode());
                    tvStop2InBound.setText(flightsList.getReturnFlightStop().get(0).getArrivalAirportCode());
                    tvStop3InBound.setText(flightsList.getReturnFlightStop().get(1).getArrivalAirportCode() + " ...");
                }

            }

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void bindDataOutbound(List<FlightStop> result) {

        flightStopOutboundEntList = new ArrayList<>();

        if (result != null && result.size() > 0) {
            Date BaseDate = DateHelper.dateFormatNew(result.get(0).getDepartureDateTime(), DateHelper.DATE_FORMAT, DateHelper.DATE_TIME_FORMAT);

            for (FlightStop stop : result) {

                String startTime = "";
                String endTime = "";

                Date EndDate = DateHelper.dateFormatNew(stop.getArrivalDateTime(), DateHelper.DATE_FORMAT, DateHelper.DATE_TIME_FORMAT);
                int Diff = Utils.getDaysDifference(BaseDate, EndDate);

                startTime = DateHelper.dateFormat(stop.getArrivalDateTime(), DateHelper.TIME_FORMAT2, DateHelper.DATE_TIME_FORMAT);
                endTime = DateHelper.dateFormat(stop.getDepartureDateTime(), DateHelper.TIME_FORMAT2, DateHelper.DATE_TIME_FORMAT);

                String time = "";
                if (Diff > 0) {
                    time = " " + getString(R.string.arrivaltime) + " " + startTime + "\n" + getString(R.string.departuretime) + " " + endTime + " + " + Diff + " d";
                } else {
                    time = " " + getString(R.string.arrivaltime) + " " + startTime + "\n" + getString(R.string.departuretime) + " " + endTime;
                }

                String PlaceNames = stop.getDepartureLocation() + " (" + stop.getDepartureAirportCode() + ")" + " - " + stop.getArrivalLocation() + " (" + stop.getArrivalAirportCode() + ")";

                flightStopOutboundEntList.add(new FlightStopEnt(PlaceNames, time, stop.getDepartureAirportCode(), stop.getAirplanType(), stop.getFlightNumber(), stop.getCabinType(), stop.getGroundDuration(), stop.getDepartureLocation()));
            }

       /* flightStopOutboundEntList.add(new FlightStopEnt("Charlotte", "10:20 pm - 11:14 pm", "Airbus (A321)", "Economy (L)"));
        flightStopOutboundEntList.add(new FlightStopEnt("Florence", "11:30 pm - 02:00 am", "Airbus (A321)", "Economy (L)"));*/


            flightStopOutboundAdapter.clearList();
            lvFlightStopsOutbound.setAdapter(flightStopOutboundAdapter);
            flightStopOutboundAdapter.addAll(flightStopOutboundEntList);
            flightStopOutboundAdapter.notifyDataSetChanged();
        }

    }

    public void bindDataInbound(List<ReturnFlightStop> result) {

        flightStopInboundEntList = new ArrayList<>();

        if (result != null && result.size() > 0) {
            Date BaseDate = DateHelper.dateFormatNew(result.get(0).getDepartureDateTime(), DateHelper.DATE_FORMAT, DateHelper.DATE_TIME_FORMAT);

            for (ReturnFlightStop stop : result) {

                Date EndDate = DateHelper.dateFormatNew(stop.getArrivalDateTime(), DateHelper.DATE_FORMAT, DateHelper.DATE_TIME_FORMAT);
                int Diff = Utils.getDaysDifference(BaseDate, EndDate);

                String startTime = "";
                String endTime = "";

                startTime = DateHelper.dateFormat(stop.getDepartureDateTime(), DateHelper.TIME_FORMAT2, DateHelper.DATE_TIME_FORMAT);
                endTime = DateHelper.dateFormat(stop.getArrivalDateTime(), DateHelper.TIME_FORMAT2, DateHelper.DATE_TIME_FORMAT);

                String time = "";
                if (Diff > 0) {
//                    time = startTime + " - " + endTime + " + " + Diff + " d";
                    time = " " + getString(R.string.arrivaltime) + startTime + "\n" + getString(R.string.departuretime) + " " + endTime + " + " + Diff + " d";
                } else {
                    time = " " + getString(R.string.arrivaltime) + startTime + "\n" + getString(R.string.departuretime) + " " + endTime;
                }

                String PlaceNames = stop.getDepartureLocation() + " (" + stop.getDepartureAirportCode() + ")" + " - " + stop.getArrivalLocation() + " (" + stop.getArrivalAirportCode() + ")";

                flightStopInboundEntList.add(new FlightStopEnt(PlaceNames, time, stop.getDepartureAirportCode(), stop.getAirplanType(), stop.getFlightNumber(), stop.getCabinType(), stop.getGroundDuration(), stop.getDepartureLocation()));
            }

        /*flightStopInboundEntList.add(new FlightStopEnt("Charlotte", "10:20 pm - 11:14 pm", "Airbus (A321)", "Economy (L)"));
        flightStopInboundEntList.add(new FlightStopEnt("Las Vegas", "11:30 pm - 02:00 am", "Airbus (A321)", "Economy (L)"));*/

            flightStopInboundAdapter.clearList();
            lvFlightStopsInbound.setAdapter(flightStopInboundAdapter);
            flightStopInboundAdapter.addAll(flightStopInboundEntList);
            flightStopInboundAdapter.notifyDataSetChanged();
        }

    }

    @OnClick({R.id.tvOutboundMoreOrLess, R.id.tvInboundMoreOrLess, R.id.btnCheckout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvOutboundMoreOrLess:

                if (isShowOutboundDetail) {
                    isShowOutboundDetail = false;
                    llDeparture.setVisibility(View.VISIBLE);
                    lvFlightStopsOutbound.setVisibility(View.GONE);
                    tvOutboundMoreOrLess.setText(getString(R.string.show_detail));
                } else {
                    isShowOutboundDetail = true;
                    llDeparture.setVisibility(View.GONE);
                    lvFlightStopsOutbound.setVisibility(View.VISIBLE);
                    tvOutboundMoreOrLess.setText(R.string.hide_details);
                }

                break;

            case R.id.tvInboundMoreOrLess:

                if (isShowInboundDetail) {
                    isShowInboundDetail = false;
                    llReturn.setVisibility(View.VISIBLE);
                    lvFlightStopsInbound.setVisibility(View.GONE);
                    tvInboundMoreOrLess.setText(getString(R.string.show_detail));
                } else {
                    isShowInboundDetail = true;
                    llReturn.setVisibility(View.GONE);
                    lvFlightStopsInbound.setVisibility(View.VISIBLE);
                    tvInboundMoreOrLess.setText(R.string.hide_details);
                }
                break;

            case R.id.btnCheckout:

                if (prefHelper.isLogin()) {
                    serviceHelper.enqueueCall(webService.GetFlightDetails(flightsList.getRatePlanCode()), WebServiceConstants.GetFlightDeails);
                } else {
                    final DialogHelper logoutdialog = new DialogHelper(getDockActivity());
                    logoutdialog.initLoginAlertDialog(R.layout.dialog_login_alert, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            logoutdialog.hideDialog();
                            LoginFragment loginFragment = new LoginFragment();
                            loginFragment.setIsCameFromBuyFlow(true);
                            getDockActivity().replaceDockableFragment(loginFragment, LoginFragment.class.getSimpleName());
                        }
                    });
                    logoutdialog.setCancelable(true);
                    logoutdialog.showDialog();
                }
                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.GetFlightDeails:

                FlightDetailsEnt flightDetailsEnt = (FlightDetailsEnt) result;

                if (flightDetailsEnt != null) {

                    flightsList.setBookingCode(flightDetailsEnt.getBookingCode());
                    flightsList.setExpiryDate(flightDetailsEnt.getExpiryDate());
                    flightsList.setOutboundDuration(OutboundDuration);
                    flightsList.setInboundDuration(InboundDuration);

                    AddTravelerInfoFragment addTravelerInfoFragment = new AddTravelerInfoFragment();
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.FlightsList, new Gson().toJson(flightsList));
                    orderBundle.putBoolean(AppConstants.IsReturnTrip, true);
                    addTravelerInfoFragment.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(addTravelerInfoFragment, AddTravelerInfoFragment.class.getSimpleName());


                }

                break;
        }
    }
}
