package com.app.amyal.fragments;

import android.os.Bundle;
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
import com.app.amyal.entities.PackageDetailsEnt;
import com.app.amyal.entities.PackageListEnt;
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
import com.app.amyal.ui.views.AnyTextView1;
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

public class PackageAddInfoFragment extends BaseFragment implements ItemClickListener {

    @BindView(R.id.tvAddInfo)
    AnyTextView tvAddInfo;
    @BindView(R.id.tvPaymentInfo)
    AnyTextView tvPaymentInfo;
    @BindView(R.id.btnAddPaymentInfo)
    AnyTextView btnAddPaymentInfo;
    @BindView(R.id.tvTotalAmount)
    AnyTextView tvTotalAmount;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;
    @BindView(R.id.svmain)
    ScrollView svmain;
    Unbinder unbinder;

    PackageDetailsEnt packageDetailsEnt;
    PackageListEnt packageListEnt;
    @BindView(R.id.tvPackageName)
    AnyTextView tvPackageName;
    @BindView(R.id.tvStartDate)
    AnyTextView tvStartDate;
    @BindView(R.id.tvEndDate)
    AnyTextView tvEndDate;
    @BindView(R.id.tvDestination)
    AnyTextView tvDestination;
    @BindView(R.id.tvTraveller)
    AnyTextView1 tvTraveller;

    CardDetailsEnt detailsEnt;
    @BindView(R.id.lvTravellers)
    ListView lvTravellers;
    @BindView(R.id.tvCardName)
    AnyTextView tvCardName;

    private ArrayListAdapter<FlightTravellerEnt> adapter;
    List<FlightTravellerEnt> flightTravellerEnts;

    PackageAddInfoFragment context;
    View view;

    public static PackageAddInfoFragment newInstance() {
        Bundle args = new Bundle();

        PackageAddInfoFragment fragment = new PackageAddInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        if (getArguments() != null) {
            packageDetailsEnt = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.packageDetailsEnt), PackageDetailsEnt.class);
            packageListEnt = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.PackageListEnt), PackageListEnt.class);
        }

        adapter = new ArrayListAdapter<>(getDockActivity(), new FlightTravellerItemBinder(getDockActivity(), prefHelper));

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.add_info));
        titleBar.showBackButton();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_package_add_info, container, false);
            unbinder = ButterKnife.bind(this, view);
        }

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getMainActivity().setBackground(R.drawable.sp_dark);

        setData();
        setTravellerData();

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

            int totalTravellersCount = prefHelper.getPackageSerachModel().getNoOfAdults() + prefHelper.getPackageSerachModel().getNoOfChildrens() + prefHelper.getPackageSerachModel().getNoOfInfant();

            /*for (int i = 0; i < totalTravellersCount; i++) {

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

                flightTravellerEnts.add(new FlightTravellerEnt(getString(R.string.click_to_add_info), MainGuest, Traveller));

            }*/

            for (int i = 0; i < prefHelper.getPackageSerachModel().getNoOfAdults(); i++) {

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

                flightTravellerEnts.add(new FlightTravellerEnt(getString(R.string.click_to_add_info), MainGuest, Traveller,false));

            }

            for (int j = 0; j < prefHelper.getPackageSerachModel().getNoOfChildrens(); j++) {

                String Traveller = "";

                int No = j + 1;
                Traveller = getString(R.string.childern) + " " + No;

                flightTravellerEnts.add(new FlightTravellerEnt(getString(R.string.click_to_add_info), false, Traveller,false));

            }

            for (int k = 0; k < prefHelper.getPackageSerachModel().getNoOfInfant(); k++) {

                String Traveller = "";

                int No = k + 1;
                Traveller = getString(R.string.infant) + " " + No;

                flightTravellerEnts.add(new FlightTravellerEnt(getString(R.string.click_to_add_info), false, Traveller,true));
            }

            adapter.clearList();
            lvTravellers.setAdapter(adapter);
            adapter.addAll(flightTravellerEnts);
            adapter.notifyDataSetChanged();
        }

        Utils.justifyListViewHeightBasedOnChildren(lvTravellers);
    }

    public void setData() {

        tvPackageName.setText(packageListEnt.getPackageName());
        tvStartDate.setText(prefHelper.getPackageSerachModel().getCheckInTime());
        tvEndDate.setText(prefHelper.getPackageSerachModel().getCheckOutTime());
        tvDestination.setText(packageListEnt.getDestination());
        tvTotalAmount.setText(packageListEnt.getCurrencyCode() + " " + packageListEnt.getTotalAmount());

        String traveller = "";
        if (prefHelper.getPackageSerachModel().getNoOfAdults() > 0) {
            traveller = prefHelper.getPackageSerachModel().getNoOfAdults() + " Adult(s)";
        }

        if (prefHelper.getPackageSerachModel().getNoOfChildrens() > 0) {
            traveller = traveller + " " + prefHelper.getPackageSerachModel().getNoOfChildrens() + " Child(s)";
        }

        if (prefHelper.getPackageSerachModel().getNoOfInfant() > 0) {
            traveller = traveller + " " + prefHelper.getPackageSerachModel().getNoOfInfant() + " Infant(s)";
        }

        tvTraveller.setSelected(true);
        tvTraveller.setText(traveller);

    }

    @OnClick({R.id.btnAddPaymentInfo, R.id.btnConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btnAddPaymentInfo:
                AddPaymentFragment addPaymentFragment = new AddPaymentFragment();
                addPaymentFragment.setTabPosition(3);
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

                if (!isInfoMissing) {

                    if (detailsEnt != null && detailsEnt.getCardNumber().length() > 0)
                        PackageReservation();
                    else
                        UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.please_add_payment_info));
                }

                break;
        }
    }

    public void PackageReservation() {

        String airlineNames = "";
        String flightNumber = "";
        String returnFlightNumber = "";

        for (FlightsList flightsList : packageListEnt.getFlightsList()) {

            if (airlineNames.length() > 0)
                airlineNames = airlineNames + "," + flightsList.getAirlineName();
            else
                airlineNames = flightsList.getAirlineName();

            for (FlightStop flightStop : flightsList.getFlightStop()) {
                if (flightNumber.length() > 0)
                    flightNumber = flightNumber + "," + flightStop.getFlightNumber();
                else
                    flightNumber = flightStop.getFlightNumber();
            }

            for (ReturnFlightStop returnFlightStop : flightsList.getReturnFlightStop()) {
                if (returnFlightNumber.length() > 0)
                    returnFlightNumber = returnFlightNumber + "," + returnFlightStop.getFlightNumber();
                else
                    returnFlightNumber = returnFlightStop.getFlightNumber();
            }

        }


        JSONObject jsonObject;
        try {

            jsonObject = new JSONObject();
            jsonObject.put("SequenceNumber", prefHelper.getPackageSerachModel().getSequenceNumber());
            jsonObject.put("BookingCode", packageDetailsEnt.getBookingCode());
            jsonObject.put("BookingExpiryDate", packageDetailsEnt.getExpiryDate());
            jsonObject.put("PackageCode", packageListEnt.getPackageCode());
            jsonObject.put("StartDate", packageListEnt.getStartDate());
            jsonObject.put("EndDate", packageListEnt.getEndDate());
            jsonObject.put("TotalAmount", packageListEnt.getTotalAmount());
            jsonObject.put("AirlineNames", airlineNames);
            jsonObject.put("FlightNumber", flightNumber);
            jsonObject.put("ReturnFlightNumber", returnFlightNumber);
            jsonObject.put("SourceLocationCode", packageListEnt.getSourceCode());
            jsonObject.put("DestinationLocationCode", packageListEnt.getDestinationCode());
            jsonObject.put("CurrencyCode", prefHelper.getPackageSerachModel().getCurrencyCode());
            jsonObject.put("ThumbImage", packageListEnt.getImages().get(0));
            jsonObject.put("Image", packageListEnt.getImages().get(0));
            jsonObject.put("LanguageCode", prefHelper.getUser().getUser().getLanguageCode());
            jsonObject.put("CurrencyCode", prefHelper.getUser().getUser().getCurrencyCode());


            JSONArray hotelGuestArray = new JSONArray();

            for (FlightTravellerEnt flightTravellerEnt : flightTravellerEnts) {

                JSONObject hotelGuestEnt = new JSONObject();

                hotelGuestEnt.put("Name", flightTravellerEnt.getName());// + flightTravellerEnt.getMiddleName()
                hotelGuestEnt.put("SurName", flightTravellerEnt.getSurName());
                hotelGuestEnt.put("Age", "");
                hotelGuestEnt.put("Phone", flightTravellerEnt.getPhone());
                hotelGuestEnt.put("Email", flightTravellerEnt.getEmail());
                hotelGuestEnt.put("Country", "");
                hotelGuestEnt.put("CountryCode", prefHelper.getUser().getUser().getCountryCode());
                hotelGuestEnt.put("State", "");
                hotelGuestEnt.put("BirthDate", flightTravellerEnt.getBirthDate());
                hotelGuestEnt.put("PassportNumber", flightTravellerEnt.getPassportNumber());
                hotelGuestEnt.put("Nationality",prefHelper.getUser().getUser().getCountryCode());
                hotelGuestEnt.put("IsInfant", flightTravellerEnt.isInfant());


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
            cardDetails.put("CardHolderEmail", "  ");

            jsonObject.put("CardDetails", cardDetails);

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

            serviceHelper.enqueueCall(webService.PostPackageBooking(body, "Bearer " + prefHelper.getUser().getAuthToken()), WebServiceConstants.PostPackageReservation);

        } catch (Exception e) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Exception " + e.getMessage());
        }

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.PostPackageReservation:

                ReservationEnt reservationEnt = (ReservationEnt) result;
                if (reservationEnt != null) {

                    PackageBookDetailFragment packageBookDetailFragment = new PackageBookDetailFragment();
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.ReservationEnt, new Gson().toJson(reservationEnt));
                    packageBookDetailFragment.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(packageBookDetailFragment, PackageBookDetailFragment.class.getSimpleName());

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
    public void itemClicked(Object object, boolean isfrom,int i) {

        if (!isfrom && object != null) {
            detailsEnt = (CardDetailsEnt) object;
            tvCardName.setText(detailsEnt.getCardHolderType());
        }

    }
}