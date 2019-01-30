package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import com.app.amyal.R;
import com.app.amyal.entities.CardDetailsEnt;
import com.app.amyal.entities.FlightStop;
import com.app.amyal.entities.FlightTravellerEnt;
import com.app.amyal.entities.FlightsList;
import com.app.amyal.entities.ReservationEnt;
import com.app.amyal.entities.ReturnFlightStop;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.Utils;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.retrofit.GsonFactory;
import com.app.amyal.ui.adapters.ArrayListAdapter;
import com.app.amyal.ui.binders.FlightTravellerItemBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.TitleBar;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by khan_muhammad on 11/29/2017.
 */

public class AddTravelerInfoFragment extends BaseFragment implements ItemClickListener {

    AnyTextView tvFlightNo;
    @BindView(R.id.tvAddInfo)
    AnyTextView tvAddInfo;
    @BindView(R.id.btnAddTravel)
    AnyTextView btnAddTravel;
    /*@BindView(R.id.tvTraveller1)
    AnyTextView tvTraveller1;
    @BindView(R.id.tvTraveller2)
    AnyTextView tvTraveller2;*/
    @BindView(R.id.btnAddPaymentInfo)
    AnyTextView btnAddPaymentInfo;
    @BindView(R.id.tvTotalAmount)
    AnyTextView tvTotalAmount;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;
    @BindView(R.id.svmain)
    ScrollView svmain;
    Unbinder unbinder;

    boolean isRondTrip = false;

    FlightsList flightsList;
    @BindView(R.id.lvTravellers)
    ListView lvTravellers;

    @BindView(R.id.tvCardName)
    AnyTextView tvCardName;
    @BindView(R.id.tvReturn)
    AnyTextView tvReturn;
    @BindView(R.id.tvFrom)
    AnyTextView tvFrom;
    @BindView(R.id.tvTo)
    AnyTextView tvTo;

    @BindView(R.id.tvTotalBill)
    AnyTextView tvTotalBill;

    @BindView(R.id.tvPaymentInfo)
    AnyTextView tvPaymentInfo;
    @BindView(R.id.tvDeparture)
    AnyTextView tvDeparture;
    List<FlightTravellerEnt> flightTravellerEnts;
    AddTravelerInfoFragment context;
    CardDetailsEnt detailsEnt;
    View view;
    Unbinder unbinder1;
    private ArrayListAdapter<FlightTravellerEnt> adapter;

    public static AddTravelerInfoFragment newInstance(boolean isRondTrip) {

        AddTravelerInfoFragment addTravelerInfoFragment = new AddTravelerInfoFragment();
        Bundle orderBundle = new Bundle();
        orderBundle.putBoolean(AppConstants.IsReturnTrip, isRondTrip);
        addTravelerInfoFragment.setArguments(orderBundle);

        return addTravelerInfoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        if (getArguments() != null) {
            isRondTrip = getArguments().getBoolean(AppConstants.IsReturnTrip);
            flightsList = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.FlightsList), FlightsList.class);
        }

        adapter = new ArrayListAdapter<>(getDockActivity(), new FlightTravellerItemBinder(getDockActivity(), prefHelper));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //view = inflater.inflate(R.layout.fragment_add_traveler_info, container, false);

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_add_traveler_info, container, false);
            unbinder = ButterKnife.bind(this, view);
        }

        unbinder1 = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getMainActivity().setBackground(R.drawable.bg_flight);

        setTravellerData();

        tvDeparture.setText(prefHelper.getFlightSearchModel().getDepartureDate());

        if (isRondTrip)
            tvReturn.setText(prefHelper.getFlightSearchModel().getReturnDate());
        else {
            tvReturn.setText(R.string.no_return);
        }

        if (flightsList.getFlightStop() != null && flightsList.getFlightStop().size() > 0) {
            tvFrom.setText(flightsList.getFlightStop().get(0).getDepartureLocation());
            tvTo.setText(flightsList.getFlightStop().get(flightsList.getFlightStop().size() - 1).getArrivalLocation());
        }

        if (prefHelper.getUser().getUser().getCurrencyCode().equals(flightsList.getCurrencyCode())) {
            tvTotalBill.setText(flightsList.getCurrencyCode() + " " + flightsList.getAmount());
            tvTotalAmount.setText(flightsList.getCurrencyCode() + " " + flightsList.getAmount());
        } else {
            tvTotalBill.setText(prefHelper.getUser().getUser().getCurrencyCode() + " " + prefHelper.getCurrency().getSAR() * flightsList.getAmount());
            tvTotalAmount.setText(prefHelper.getUser().getUser().getCurrencyCode() + " " + prefHelper.getCurrency().getSAR() * flightsList.getAmount());
        }


        lvTravellers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TravelerInformationFragment travelerInformationFragment = new TravelerInformationFragment();
                travelerInformationFragment.setListner(context);
                Bundle orderBundle = new Bundle();
                orderBundle.putString(AppConstants.FlightTravellerEnt, new Gson().toJson(flightTravellerEnts.get(position)));
                orderBundle.putInt(AppConstants.position, position);
                travelerInformationFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(travelerInformationFragment, TravelerInformationFragment.class.getSimpleName());

            }
        });

    }


    public void setTravellerData() {

        if (flightTravellerEnts != null && flightTravellerEnts.size() > 0) {
            adapter.clearList();
            lvTravellers.setAdapter(adapter);
            adapter.addAll(flightTravellerEnts);
            adapter.notifyDataSetChanged();
        } else {

            flightTravellerEnts = new ArrayList<>();

            int totalTravellersCount = prefHelper.getFlightSearchModel().getNofAdult() + prefHelper.getFlightSearchModel().getNofChild();

            /*for (int i = 0; i < totalTravellersCount; i++) {

                String Traveller = "";
                boolean MainGuest = false;
                if (i == 0) {
                    Traveller = getString(R.string.main_traveler);
                    MainGuest = true;
                } else {
                    int No = i + 1;
                    Traveller = getString(R.string.traveller) + " " + No;
                    MainGuest = false;
                }

                flightTravellerEnts.add(new FlightTravellerEnt(getString(R.string.click_to_add_info), MainGuest, Traveller,false));
            }

            for (int i = 0; i < prefHelper.getFlightSearchModel().getNofInfant(); i++) {

                String Traveller = "";
                int No = i + 1;
                Traveller = getString(R.string.infant) + " " + No;
                flightTravellerEnts.add(new FlightTravellerEnt(getString(R.string.click_to_add_info), false, Traveller,true));
            }*/

            for (int i = 0; i < prefHelper.getFlightSearchModel().getNofAdult(); i++) {

                String Traveller = "";
                boolean MainGuest = false;
                if (i == 0) {
                    Traveller = getString(R.string.main_guset);
                    MainGuest = true;
                } else {
                    int No = i + 1;
                    Traveller = getString(R.string.guest) + " " + No;
                    MainGuest = false;
                }

                flightTravellerEnts.add(new FlightTravellerEnt(getString(R.string.click_to_add_info), MainGuest, Traveller, false));

            }

            for (int j = 0; j < prefHelper.getFlightSearchModel().getNofChild(); j++) {

                String Traveller = "";

                int No = j + 1;
                Traveller = getString(R.string.childern) + " " + No;

                flightTravellerEnts.add(new FlightTravellerEnt(getString(R.string.click_to_add_info), false, Traveller, false));

            }

            for (int k = 0; k < prefHelper.getFlightSearchModel().getNofInfant(); k++) {

                String Traveller = "";

                int No = k + 1;
                Traveller = getString(R.string.infant) + " " + No;

                flightTravellerEnts.add(new FlightTravellerEnt(getString(R.string.click_to_add_info), false, Traveller, true));
            }


            adapter.clearList();
            lvTravellers.setAdapter(adapter);
            adapter.addAll(flightTravellerEnts);
            adapter.notifyDataSetChanged();
        }

        Utils.justifyListViewHeightBasedOnChildren(lvTravellers);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.add_info));
        titleBar.showBackButton();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.btnAddTravel, R.id.btnAddPaymentInfo, R.id.btnConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAddTravel:
                //getDockActivity().replaceDockableFragment(TravelerInformationFragment.newInstance(false), TravelerInformationFragment.class.getSimpleName());
                break;
            case R.id.btnAddPaymentInfo:
                AddPaymentFragment addPaymentFragment = new AddPaymentFragment();
                addPaymentFragment.setTabPosition(0);
                addPaymentFragment.setItemClickListener(this);
                getDockActivity().replaceDockableFragment(addPaymentFragment, AddPaymentFragment.class.getSimpleName());
                break;
            case R.id.btnConfirm:

                boolean isInfoMissing = false;
                for (FlightTravellerEnt flightTravellerEnt : flightTravellerEnts) {
                    if (flightTravellerEnt.getEmail() != null && flightTravellerEnt.getEmail().length() > 0) {
                        isInfoMissing = false;
                    } else {
                        isInfoMissing = true;
                        UIHelper.showShortToastInCenter(getDockActivity(), flightTravellerEnt.getTravellerNo() + " Information is Missing");
                        break;
                    }
                }

                if (!isInfoMissing)
                    FlightReservation();
                break;

        }
    }

    public void FlightReservation() {

        if (detailsEnt != null && detailsEnt.getCardNumber().length() > 0) {

            String FlightNumber = "";
            String ReturnFlightNumber = "";

            String AirlineCode = "";
            String ReturnAirlineCode = "";

            String DeparDate = "";
            String ArrivalDate = "";

            String ReturnDeparDate = "";
            String ReturnArivalDate = "";

            String AirlineName = flightsList.getAirlineName();


            if (flightsList.getFlightStop() != null && flightsList.getFlightStop().size() > 0) {
                for (FlightStop flightStop : flightsList.getFlightStop()) {
                    if (FlightNumber.length() > 0) {
                        FlightNumber = FlightNumber + "," + flightStop.getFlightNumber();
                    } else {
                        FlightNumber = flightStop.getFlightNumber();
                    }

                    if (AirlineCode.length() > 0) {
                        AirlineCode = AirlineCode + "," + flightStop.getAirlineCode();
                    } else {
                        AirlineCode = flightStop.getAirlineCode();
                    }
                }

                DeparDate = flightsList.getFlightStop().get(0).getDepartureDateTime();

                if (flightsList.getFlightStop() != null && flightsList.getFlightStop().size() != 0)
                    ArrivalDate = flightsList.getFlightStop().get(flightsList.getFlightStop().size() - 1).getArrivalDateTime();

            }

            if (flightsList.getReturnFlightStop() != null && flightsList.getReturnFlightStop().size() > 0) {
                for (ReturnFlightStop flightStop : flightsList.getReturnFlightStop()) {
                    if (ReturnFlightNumber.length() > 0)
                        ReturnFlightNumber = ReturnFlightNumber + "," + flightStop.getFlightNumber();
                    else
                        ReturnFlightNumber = flightStop.getFlightNumber();

                    if (ReturnAirlineCode.length() > 0) {
                        ReturnAirlineCode = ReturnAirlineCode + "," + flightStop.getAirlineCode();
                    } else {
                        ReturnAirlineCode = flightStop.getAirlineCode();
                    }
                }

                ReturnDeparDate = flightsList.getReturnFlightStop().get(0).getDepartureDateTime();

                if (flightsList.getReturnFlightStop() != null && flightsList.getReturnFlightStop().size() != 0)
                    ReturnArivalDate = flightsList.getReturnFlightStop().get(flightsList.getReturnFlightStop().size() - 1).getArrivalDateTime();
            }

            JSONObject jsonObject;

            try {

                jsonObject = new JSONObject();
                jsonObject.put("SequenceNumber", prefHelper.getFlightSearchModel().getSequenceNumber());
                jsonObject.put("BookingCode", flightsList.getBookingCode());
                jsonObject.put("BookingExpiryDate", flightsList.getExpiryDate());
                jsonObject.put("Direction", prefHelper.getFlightSearchModel().getDirection());

                jsonObject.put("FlightNumber", FlightNumber);
                jsonObject.put("ReturnFlightNumber", ReturnFlightNumber);

                jsonObject.put("AirlineCode", AirlineCode);
                jsonObject.put("ReturnAirlineCode", ReturnAirlineCode);

                jsonObject.put("DepartureDate", DeparDate);
                jsonObject.put("ArrivalDate", ArrivalDate);

                jsonObject.put("ReturnDepartureDate", ReturnDeparDate);
                jsonObject.put("ReturnArrivalDate", ReturnArivalDate);

                jsonObject.put("AirlineName", AirlineName);
                jsonObject.put("ReturnAirlineName", AirlineName);

                jsonObject.put("SeatNumber", "");
                jsonObject.put("SeatCode", "");

                jsonObject.put("TotalFareAmount", flightsList.getAmount());

                jsonObject.put("SourceLocationCode", prefHelper.getFlightSearchModel().getSourceLocationCode());
                jsonObject.put("DestinationLocationCode", prefHelper.getFlightSearchModel().getDestinationLocationCode());

                jsonObject.put("TotalDuration", flightsList.getTotalDuration());
                jsonObject.put("OutboundDuration", flightsList.getOutboundDuration());
                jsonObject.put("InboundDuration", flightsList.getInboundDuration());

                jsonObject.put("Image", flightsList.getImage());
                jsonObject.put("ThumbImage", flightsList.getThumb());
                jsonObject.put("LanguageCode", "en"); //prefHelper.getFlightSearchModel().getLanguageCode()
                jsonObject.put("CurrencyCode", prefHelper.getFlightSearchModel().getCurrencyCode());

//                jsonObject.put("OutboundDuration", "");
//                jsonObject.put("InboundDuration", "");

                JSONArray hotelGuestArray = new JSONArray();

                for (FlightTravellerEnt flightTravellerEnt : flightTravellerEnts) {

                    JSONObject hotelGuestEnt = new JSONObject();

                    hotelGuestEnt.put("Name", flightTravellerEnt.getName()); //+ flightTravellerEnt.getMiddleName()
                    hotelGuestEnt.put("SurName", flightTravellerEnt.getSurName());
                    hotelGuestEnt.put("Age", flightTravellerEnt.getAge());
                    hotelGuestEnt.put("Phone", flightTravellerEnt.getPhone());
                    hotelGuestEnt.put("Email", flightTravellerEnt.getEmail());
//                    hotelGuestEnt.put("Country", flightTravellerEnt.getCountry());
                    hotelGuestEnt.put("Country", "UAE");
                    hotelGuestEnt.put("CountryCode", flightTravellerEnt.getCountryCode());
                    hotelGuestEnt.put("City", flightTravellerEnt.getCity());
                    hotelGuestEnt.put("Address", flightTravellerEnt.getAddress());
                    hotelGuestEnt.put("PostalCode", prefHelper.getUser().getUser().getPostalCode());
                    hotelGuestEnt.put("State", "");
                    hotelGuestEnt.put("BirthDate", flightTravellerEnt.getBirthDate());
                    hotelGuestEnt.put("PassportNumber", flightTravellerEnt.getPassportNumber());
                    hotelGuestEnt.put("PassportExpireDate", flightTravellerEnt.getPassportExpiary());
                    hotelGuestEnt.put("Nationality", prefHelper.getUser().getUser().getCountryCode()); //prefHelper.getUser().getUser().getCountryCode()
                    hotelGuestEnt.put("IsInfant", flightTravellerEnt.isInfant());

//                    hotelGuestEnt.put("GuestType", flightTravellerEnt.getGuestType());


                    if (flightTravellerEnt.getIsFemale())
                        hotelGuestEnt.put("Gender", "F");
                    else
                        hotelGuestEnt.put("Gender", "M");

                    hotelGuestEnt.put("MainGuest", flightTravellerEnt.isMainGuest());

                    hotelGuestArray.put(hotelGuestEnt);

                }

                jsonObject.put("Guests", hotelGuestArray);

                JSONObject cardDetails = new JSONObject();
                cardDetails.put("CardCode", detailsEnt.getCardCode());
                cardDetails.put("CardNumber", detailsEnt.getCardNumber());
                cardDetails.put("SeriesCode", detailsEnt.getSeriesCode());
                cardDetails.put("ExpireDate", detailsEnt.getExpireDate());
                cardDetails.put("CardHolderName", detailsEnt.getCardHolderName());
                cardDetails.put("CardHolderSurname", detailsEnt.getCardHolderName());
                cardDetails.put("CardHolderEmail", "");
                cardDetails.put("CardType", detailsEnt.getCardHolderType());
                jsonObject.put("CardDetails", cardDetails);

                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

                serviceHelper.enqueueCall(webService.PostFlightReservation(body, "Bearer " + prefHelper.getUser().getAuthToken()
                ), WebServiceConstants.PostFlightReservation);

            } catch (Exception e) {

            }
        } else {
            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.please_add_payment_info));
        }

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.PostFlightReservation:

                ReservationEnt reservationEnt = (ReservationEnt) result;
                if (reservationEnt != null) {

                    FlightBookDetailFragment flightBookDetailFragment = new FlightBookDetailFragment();
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.ReservationEnt, new Gson().toJson(reservationEnt));
                    orderBundle.putBoolean(AppConstants.IsReturnTrip, isRondTrip);
                    flightBookDetailFragment.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(flightBookDetailFragment, FlightBookDetailFragment.class.getSimpleName());

                }

                break;
        }
    }

    @Override
    public void itemClicked(Object object, int position) {

        FlightTravellerEnt flightTravellerEnt = (FlightTravellerEnt) object;

        if (flightTravellerEnt != null && position < flightTravellerEnts.size()) {
            flightTravellerEnts.remove(position);
            flightTravellerEnts.add(position, flightTravellerEnt);
        }


    }

    @Override
    public void itemClicked(Object object, boolean isfrom, int i) {
        if (!isfrom) {
            detailsEnt = (CardDetailsEnt) object;
            tvCardName.setText(detailsEnt.getCardHolderType());
            btnAddPaymentInfo.setText(getString(R.string.edit));
        }
    }
}
