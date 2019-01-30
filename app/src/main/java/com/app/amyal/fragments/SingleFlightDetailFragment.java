package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.app.amyal.R;
import com.app.amyal.entities.FlightDetailsEnt;
import com.app.amyal.entities.FlightStop;
import com.app.amyal.entities.FlightStopEnt;
import com.app.amyal.entities.FlightsList;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by khan_muhammad on 11/28/2017.
 */

public class SingleFlightDetailFragment extends BaseFragment {

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
    @BindView(R.id.lvFlightStops)
    ListView lvFlightStops;
    @BindView(R.id.btnSelect)
    Button btnSelect;
    Unbinder unbinder;
    @BindView(R.id.tvFlightDate)
    AnyTextView tvFlightDate;
    @BindView(R.id.ivAirline)
    ImageView ivAirline;
    boolean isReturnTrip = false;
    boolean isInBound = false;
    boolean isLoginScreenOpen = false;
    FlightsList flightsList;
    private List<FlightStopEnt> flightStopEntList;
    private ArrayListAdapter<FlightStopEnt> adapter;

    public static SingleFlightDetailFragment newInstance(boolean isRondTrip, boolean isInBound) {

        SingleFlightDetailFragment singleFlightDetailFragment = new SingleFlightDetailFragment();
        Bundle orderBundle = new Bundle();
        orderBundle.putBoolean(AppConstants.IsReturnTrip, isRondTrip);
        orderBundle.putBoolean(AppConstants.IsInBound, isInBound);
        singleFlightDetailFragment.setArguments(orderBundle);

        return singleFlightDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new BinderFlightStop(getDockActivity(), prefHelper));

        if (getArguments() != null) {
            isReturnTrip = getArguments().getBoolean(AppConstants.IsReturnTrip);
            isInBound = getArguments().getBoolean(AppConstants.IsInBound);
            flightsList = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.FlightsList), FlightsList.class);
        }

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        //titleBar.setSubHeading(getString(R.string.notification));

        if (isInBound) {
            titleBar.setSubHeading("Inbound to " + prefHelper.getFlightSearchModel().getFromLocationCode());
        } else {
            titleBar.setSubHeading("Outbound to " + prefHelper.getFlightSearchModel().getToLocationCode());
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_flight_detail, container, false);

        unbinder = ButterKnife.bind(this, view);

        getMainActivity().setBackground(R.drawable.bg_flight);

        /*if (getArguments() != null) {
            isReturnTrip = getArguments().getBoolean(AppConstants.IsReturnTrip);
            isInBound = getArguments().getBoolean(AppConstants.IsInBound);
        }*/

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (flightsList != null && flightsList.getFlightStop() != null && flightsList.getFlightStop().size() > 0) {
            bindData(flightsList.getFlightStop());
            Utils.justifyListViewHeightBasedOnChildren(lvFlightStops);

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void bindData(List<FlightStop> result) {

        flightStopEntList = new ArrayList<>();

        //flightStopEntList.add(new FlightStopEnt("Las Vegas","01:00 pm - 08:00 pm","Airbus (A321)","Economy (L)","","","",""));

        Date BaseDate = DateHelper.dateFormatNew(result.get(0).getDepartureDateTime(), DateHelper.DATE_FORMAT, DateHelper.DATE_TIME_FORMAT);

        for (FlightStop stop : result) {

            String startTime = "";
            String endTime = "";

            Date EndDate = DateHelper.dateFormatNew(stop.getArrivalDateTime(), DateHelper.DATE_FORMAT, DateHelper.DATE_TIME_FORMAT);
            int Diff = Utils.getDaysDifference(BaseDate, EndDate);

            startTime = DateHelper.dateFormat(stop.getDepartureDateTime(), DateHelper.TIME_FORMAT2, DateHelper.DATE_TIME_FORMAT);
            endTime = DateHelper.dateFormat(stop.getArrivalDateTime(), DateHelper.TIME_FORMAT2, DateHelper.DATE_TIME_FORMAT);

            String time = "";
            if (Diff > 0) {
                time = "Arrival Time " + startTime + "\n" + "Departure Time " + endTime + " + " + Diff + " d";
            } else {
                time = "Arrival Time " + startTime + "\n" + "Departure Time " + endTime;
            }

            String PlaceNames = stop.getDepartureLocation() + " (" + stop.getDepartureAirportCode() + ")" + " - " + stop.getArrivalLocation() + " (" + stop.getArrivalAirportCode() + ")";

            flightStopEntList.add(new FlightStopEnt(PlaceNames, time, stop.getDepartureAirportCode(), stop.getAirplanType(), stop.getFlightNumber(), stop.getCabinType(), stop.getGroundDuration(), stop.getDepartureLocation()));
        }

        adapter.clearList();
        lvFlightStops.setAdapter(adapter);
        adapter.addAll(flightStopEntList);
        adapter.notifyDataSetChanged();

    }

    @OnClick(R.id.btnSelect)
    public void onViewClicked() {

        if (isReturnTrip) {
            if (!isInBound)
                getDockActivity().replaceDockableFragment(SelectFlightFragment.newInstance(isReturnTrip, true), SelectFlightFragment.class.getSimpleName() + "InBound");
            else {
                getDockActivity().replaceDockableFragment(RoundTripFlightDetailsFragment.newInstance(), RoundTripFlightDetailsFragment.class.getSimpleName());
            }
        } else {
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

                    AddTravelerInfoFragment addTravelerInfoFragment = new AddTravelerInfoFragment();
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.FlightsList, new Gson().toJson(flightsList));
                    orderBundle.putBoolean(AppConstants.IsReturnTrip, isReturnTrip);
                    addTravelerInfoFragment.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(addTravelerInfoFragment, AddTravelerInfoFragment.class.getSimpleName());

                }

                break;
        }
    }
}
