package com.app.amyal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.app.amyal.R;
import com.app.amyal.entities.CarList;
import com.app.amyal.entities.CardDetailsEnt;
import com.app.amyal.entities.FlightTravellerEnt;
import com.app.amyal.entities.ReservationEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.retrofit.GsonFactory;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.AnyTextView1;
import com.app.amyal.ui.views.TitleBar;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by waq on 12/6/2017.
 */

public class CarAddInformation extends BaseFragment implements ItemClickListener {

    @BindView(R.id.tvAddInfo)
    AnyTextView tvAddInfo;
    @BindView(R.id.btnAddDriver)
    AnyTextView btnAddDriver;
    @BindView(R.id.llTravelr1)
    RelativeLayout llTravelr1;
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

    CarList carListModel;

    View view;

    CardDetailsEnt detailsEnt;

    FlightTravellerEnt flightTravellerEnts;
    @BindView(R.id.tvPick_up)
    AnyTextView1 tvPickUp;
    @BindView(R.id.tvDrop_off)
    AnyTextView1 tvDropOff;
    @BindView(R.id.tvCarName)
    AnyTextView tvCarName;
    @BindView(R.id.tvCardName)
    AnyTextView tvCardName;
    @BindView(R.id.tvPick_upAddress)
    AnyTextView1 tvPickUpAddress;
    @BindView(R.id.tvDrop_offAddress)
    AnyTextView1 tvDropOffAddress;
    @BindView(R.id.tvDriverName)
    AnyTextView tvDriverName;

    public static CarAddInformation newInstance() {
        Bundle args = new Bundle();

        CarAddInformation fragment = new CarAddInformation();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //View view = inflater.inflate(R.layout.fragment_car_add_info, container, false);

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_car_add_info, container, false);
            unbinder = ButterKnife.bind(this, view);
        }

        unbinder = ButterKnife.bind(this, view);

        if (getArguments() != null) {
            carListModel = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.CarListModel), CarList.class);
        }

        return view;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.add_info));
        titleBar.showBackButton();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getMainActivity().setBackground(R.drawable.bg_car);

        if (carListModel != null) {
            tvTotalAmount.setText(carListModel.getCurrencyCode() + " " + carListModel.getTotalAmount());
            tvPickUp.setText(prefHelper.getCarSearchModel().getPickUpDate());
            tvDropOff.setText(prefHelper.getCarSearchModel().getDropOffDate());
            tvPickUpAddress.setSelected(true);
            tvPickUpAddress.setText(carListModel.getCarDetailsEnt().getPickUpLocationDetails().getAddress());
            tvDropOffAddress.setSelected(true);
            tvDropOffAddress.setText(carListModel.getCarDetailsEnt().getDropOffLocationDetails().getAddress());
            tvCarName.setText(carListModel.getCarName());
        }

    }

    @OnClick({R.id.btnAddDriver, R.id.btnAddPaymentInfo, R.id.btnConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAddDriver:
                AddDriverFragment addDriverFragment = AddDriverFragment.newInstance();
                addDriverFragment.setItemClickListener(this);
                Bundle orderBundle = new Bundle();
                if (flightTravellerEnts != null) {
                    orderBundle.putString(AppConstants.FlightTravellerEnt, new Gson().toJson(flightTravellerEnts));
                    addDriverFragment.setArguments(orderBundle);
                }
                getDockActivity().replaceDockableFragment(addDriverFragment, AddDriverFragment.class.getSimpleName());
                break;
            case R.id.btnAddPaymentInfo:
                AddPaymentFragment addPaymentFragment = new AddPaymentFragment();
                addPaymentFragment.setItemClickListener(this);
                addPaymentFragment.setTabPosition(2);
                getDockActivity().replaceDockableFragment(addPaymentFragment, AddPaymentFragment.class.getSimpleName());
                break;
            case R.id.btnConfirm:

                if (flightTravellerEnts != null && flightTravellerEnts.getName() != null && flightTravellerEnts.getName().length() > 0) {

                    if (detailsEnt != null && detailsEnt.getCardNumber().length() > 0) {

                        JSONObject jsonObject;

                        try {

                            jsonObject = new JSONObject();

                            jsonObject.put("SequenceNumber", prefHelper.getCarSearchModel().getSequenceNumber());
                            jsonObject.put("BookingCode", carListModel.getCarDetailsEnt().getBookingCode());
                            jsonObject.put("BookingExpiryDate", carListModel.getCarDetailsEnt().getExpiryDate());
                            jsonObject.put("CarCode", carListModel.getCarCode());
                            jsonObject.put("TotalFareAmount", carListModel.getTotalAmount());
                            jsonObject.put("PickUpDate", prefHelper.getCarSearchModel().getPickUpDate());
                            jsonObject.put("DropOffDate", prefHelper.getCarSearchModel().getDropOffDate());
                            jsonObject.put("PickUpLocationCode", prefHelper.getCarSearchModel().getPickUpLocationCode());
                            jsonObject.put("DropOffLocationCode", prefHelper.getCarSearchModel().getDropOffLocationCode());
                            jsonObject.put("Image", carListModel.getImage());
                            jsonObject.put("LanguageCode", prefHelper.getCarSearchModel().getLanguageCode());
                            jsonObject.put("CurrencyCode", prefHelper.getCarSearchModel().getCurrencyCode());

                            JSONArray carDriverArray = new JSONArray();

                            JSONObject carDriverEnt = new JSONObject();

                            carDriverEnt.put("Name", flightTravellerEnts.getName());
                            carDriverEnt.put("SurName", flightTravellerEnts.getSurName());
                            carDriverEnt.put("Age", "");
                            carDriverEnt.put("Phone", flightTravellerEnts.getPhone());
                            carDriverEnt.put("Email", "");
                            carDriverEnt.put("Country", "");
                            carDriverEnt.put("CountryCode", "");
                            carDriverEnt.put("City", "");
                            carDriverEnt.put("Address", "");
                            carDriverEnt.put("PostalCode", "");
                            carDriverEnt.put("State", "");
                            carDriverEnt.put("BirthDate", flightTravellerEnts.getBirthDate());
                            carDriverEnt.put("PassportNumber", flightTravellerEnts.getPassportNumber());
                            carDriverEnt.put("Nationality", prefHelper.getCarSearchModel().getCountryOfResidence());

                            if (flightTravellerEnts.getIsFemale())
                                carDriverEnt.put("Gender", "F");
                            else
                                carDriverEnt.put("Gender", "M");

                            carDriverEnt.put("MainGuest", true);

                            carDriverArray.put(carDriverEnt);

                            jsonObject.put("Guests", carDriverArray);

                            JSONObject cardDetails = new JSONObject();
                            cardDetails.put("CardCode", detailsEnt.getCardCode());
                            cardDetails.put("CardNumber", detailsEnt.getCardNumber());
                            cardDetails.put("SeriesCode", detailsEnt.getSeriesCode());
                            cardDetails.put("ExpireDate", detailsEnt.getExpireDate());
                            cardDetails.put("CardHolderName", detailsEnt.getCardHolderName());
                            cardDetails.put("CardHolderSurname", detailsEnt.getCardHolderName());
                            cardDetails.put("CardHolderEmail", "");


                            jsonObject.put("CardDetails", cardDetails);

                            //jsonObject.put("HotelListModel", "");

                            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

                            serviceHelper.enqueueCall(webService.PostCarReservation(body, "Bearer " + prefHelper.getUser().getAuthToken()
                            ), WebServiceConstants.PostCarReservation);

                        } catch (Exception e) {

                        }
                    }else {
                        UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.please_add_payment_info));
                    }
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.please_add_driver_info));
                }

                //getDockActivity().replaceDockableFragment(CarBookDetailFragment.newInstance(), CarBookDetailFragment.class.getSimpleName());

                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.PostCarReservation:

                ReservationEnt reservationEnt = (ReservationEnt) result;
                if (reservationEnt != null) {
                    CarBookDetailFragment carBookDetailFragment = new CarBookDetailFragment();
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.ReservationEnt, new Gson().toJson(reservationEnt));
                    carBookDetailFragment.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(carBookDetailFragment, CarBookDetailFragment.class.getSimpleName());

                }

                break;
        }
    }

    @Override
    public void itemClicked(Object object, int position) {

    }

    @Override
    public void itemClicked(Object object, boolean isfrom,int i) {

        if (isfrom) {

            flightTravellerEnts = (FlightTravellerEnt) object;

            if (flightTravellerEnts != null) {
                tvDriverName.setText(flightTravellerEnts.getName());
            }

        } else {
            detailsEnt = (CardDetailsEnt) object;
            tvCardName.setText(detailsEnt.getCardHolderType());
        }

    }
}
