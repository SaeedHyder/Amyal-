package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.amyal.R;
import com.app.amyal.entities.CardDetailsEnt;
import com.app.amyal.entities.GuestWraper;
import com.app.amyal.entities.HotelListModel;
import com.app.amyal.entities.HotelSearchModel;
import com.app.amyal.entities.ReservationEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.DateHelper;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.retrofit.GsonFactory;
import com.app.amyal.ui.adapters.GuestinfoAdapter;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.AnyTextView1;
import com.app.amyal.ui.views.TitleBar;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.app.amyal.activities.MainActivity.hotelGuestDetailEnts;


public class HotelAddInfoFragment extends BaseFragment implements ItemClickListener {

    @BindView(R.id.tvHotelName)
    AnyTextView1 tvHotelName;
    @BindView(R.id.btnEdit)
    AnyTextView btnEdit;
    @BindView(R.id.tvAddInfo)
    AnyTextView tvAddInfo;
    //    @BindView(R.id.btnAddGuest)
//    AnyTextView btnAddGuest;
    @BindView(R.id.tvPaymentInfo)
    AnyTextView tvPaymentInfo;
    @BindView(R.id.btnAddPaymentInfo)
    AnyTextView btnAddPaymentInfo;
    @BindView(R.id.tvTotalAmount)
    AnyTextView tvTotalAmount;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;

    Unbinder unbinder;

    HotelListModel hotelListModel;


    CardDetailsEnt detailsEnt;


    View view;
    @BindView(R.id.tvCardName)
    AnyTextView tvCardName;
    Unbinder unbinder1;
    @BindView(R.id.tvCheckIn)
    AnyTextView tvCheckIn;
    @BindView(R.id.tvCheckOut)
    AnyTextView tvCheckOut;
    @BindView(R.id.tvRoomType)
    AnyTextView1 tvRoomType;
    @BindView(R.id.tvRoomNo)
    AnyTextView tvRoomNo;
    @BindView(R.id.tvAdultNo)
    AnyTextView tvAdultNo;
    @BindView(R.id.tvChild)
    AnyTextView tvChild;
    //    @BindView(R.id.llTravelr1)
//    RelativeLayout llTravelr1;
//    @BindView(R.id.llTravelr)
//    RelativeLayout llTravelr;
//    @BindView(R.id.tvGuestDetails1)
//    AnyTextView1 tvGuestDetails1;
//    @BindView(R.id.btnAddGuest1)
//    AnyTextView btnAddGuest1;
//    @BindView(R.id.tvGuestDetails2)
//    AnyTextView1 tvGuestDetails2;
//    @BindView(R.id.btnAddGuest2)
//    AnyTextView btnAddGuest2;
//    @BindView(R.id.llTravelr2)
//    RelativeLayout llTravelr2;
    @BindView(R.id.svmain)
    ScrollView svmain;
    GuestWraper[] guestWraper;
    @BindView(R.id.rvAddInfo)
    RecyclerView rvAddInfo;
    GuestinfoAdapter guestinfoAdapter;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    private int tot;

    public static HotelAddInfoFragment newInstance() {
        return new HotelAddInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            hotelListModel = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.HotelListModel), HotelListModel.class);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_hotel_add_information, container, false);
            unbinder = ButterKnife.bind(this, view);
        }
        guestWraper = HotelSearchModel.getInstance().getGuestWrapers();
        unbinder1 = ButterKnife.bind(this, view);
        tvTitle.setText(HotelSearchModel.getInstance().getBookingExpiry());
        setGuestinfoAdapter();
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

        getMainActivity().setBackground(R.drawable.bg_hotel);
        tvTotalAmount.setText(HotelSearchModel.getInstance().getCurrencyCode() + " " + HotelSearchModel.getInstance().getAmountWithTax());

        if (hotelListModel != null) {

            tvHotelName.setText(hotelListModel.getHotelName());
            //tvCheckIn.setText(prefHelper.getHotelSearchModel().getCheckInDate());
            //tvCheckOut.setText(prefHelper.getHotelSearchModel().getCheckOutDate());
            tvCheckIn.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMD, AppConstants.DateFormat_DMY3, HotelSearchModel.getInstance().getCheckInDate()));
            tvCheckOut.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMD, AppConstants.DateFormat_DMY3, HotelSearchModel.getInstance().getCheckOutDate()));
            tvRoomType.setSelected(true);
            tvRoomType.setText(hotelListModel.getRoomCombinations().get(hotelListModel.getRoomGalleryEnt().getPosition()).getRoomType());
            tvRoomNo.setText(HotelSearchModel.getInstance().getTotInfant() + "");
            tvAdultNo.setText(HotelSearchModel.getInstance().getTotAdult() + "");
            tvChild.setText(HotelSearchModel.getInstance().getTotChild() + "");

        }


    }

    @OnClick({R.id.btnEdit/*, R.id.btnAddGuest, R.id.btnAddGuest1, R.id.btnAddGuest2,*/, R.id.btnAddPaymentInfo, R.id.btnConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnEdit:
                getDockActivity().replaceDockableFragment(EditBookingFragment.newInstance(), EditBookingFragment.class.getSimpleName());
                break;
            case R.id.btnAddPaymentInfo:
                AddPaymentFragment addPaymentFragment = new AddPaymentFragment();
                addPaymentFragment.setTabPosition(1);
                addPaymentFragment.setItemClickListener(this);
                getDockActivity().replaceDockableFragment(addPaymentFragment, AddPaymentFragment.class.getSimpleName());
                break;
            case R.id.btnConfirm:

//                if (tvGuestDetails.getText().toString() != null && tvGuestDetails.getText().toString().length() > 0) {

                if (detailsEnt != null && detailsEnt.getCardNumber().length() > 0) {
                    String roomImage = "";
                    if (hotelListModel.getHotelGalleryEnt().getImages() != null && hotelListModel.getHotelGalleryEnt().getImages().size() > 0) {
                        roomImage = hotelListModel.getHotelGalleryEnt().getImages().get(0);
                    }

//                        int totoalGuest = prefHelper.getHotelSearchModel().getNofAdult() + prefHelper.getHotelSearchModel().getNofChild();


                    JSONObject jsonObject;
                    try {

                        jsonObject = new JSONObject();
                        jsonObject.put("CheckInTime", DateHelper.getFormatedDate("yyyy-MM-dd", "dd MMM, yyyy", HotelSearchModel.getInstance().getCheckInDate()));
                        jsonObject.put("CheckOutTime", DateHelper.getFormatedDate("yyyy-MM-dd", "dd MMM, yyyy", HotelSearchModel.getInstance().getCheckOutDate()));
                        jsonObject.put("SequenceNumber", HotelSearchModel.getInstance().getSequenceNumber());
                        jsonObject.put("HotelCode", HotelSearchModel.getInstance().getHotelCode());
//                            jsonObject.put("RatePlanCode", prefHelper.getHotelSearchModel().getRatePlanCode());
                        jsonObject.put("AmountAfterTax", HotelSearchModel.getInstance().getAmountWithTax());
                        jsonObject.put("CurrencyCode", HotelSearchModel.getInstance().getCurrencyCode());
                        jsonObject.put("ThumbImage", roomImage);
                        jsonObject.put("Image", roomImage);
                        jsonObject.put("TravellingFor", HotelSearchModel.getInstance().getTravellingFor());
                        jsonObject.put("CategoryCode", hotelListModel.getCategoryCode().equals("") ? "0" : hotelListModel.getCategoryCode().equals(""));
                        jsonObject.put("ZoneCode", HotelSearchModel.getInstance().getZoneCode());
                        jsonObject.put("LanguageCode", prefHelper.getUser().getUser().getLanguageCode());
                        jsonObject.put("CurrencyCode", HotelSearchModel.getInstance().getCurrencyCode());


//                        for (int i=0;i<)


                        JSONArray guestDistributionArray = new JSONArray();

                        for (int i = 0; i < HotelSearchModel.getInstance().getGuestWrapers().length; i++) {
                            JSONObject guestDistributionObj = new JSONObject();
                            JSONArray hotelGuestArray = new JSONArray();
                            if (HotelSearchModel.getInstance().getGuestWrapers()[i] != null) {
                                int tot = HotelSearchModel.getInstance().getGuestWrapers()[i].getNofAdult() + HotelSearchModel.getInstance().getGuestWrapers()[i].getNofChild() + HotelSearchModel.getInstance().getGuestWrapers()[i].getNofInfant();
                                for (int j = 0; j < tot; j++) {

                                    if (hotelGuestDetailEnts.size() > 0 && hotelGuestDetailEnts.get(0) != null) {
                                        JSONObject hotelGuestEnt = new JSONObject();

                                        hotelGuestEnt.put("Name", hotelGuestDetailEnts.get(0).getName());
                                        hotelGuestEnt.put("SurName", "Mr " + hotelGuestDetailEnts.get(0).getName());
                                        hotelGuestEnt.put("Age", hotelGuestDetailEnts.get(0).getAge());
                                        hotelGuestEnt.put("Email", hotelGuestDetailEnts.get(0).getEmail());
                                        if (j == 0)
                                            hotelGuestEnt.put("MainGuest", hotelGuestDetailEnts.get(0).isMainGuest());
                                        else
                                            hotelGuestEnt.put("MainGuest", false);
                                        hotelGuestEnt.put("Phone", prefHelper.getUser().getUser().getPhone());
                                        hotelGuestEnt.put("Country", "");
                                        hotelGuestEnt.put("CountryCode", "");
                                        hotelGuestEnt.put("City", "");
                                        hotelGuestEnt.put("Address", "");
                                        hotelGuestEnt.put("PostalCode", "");
                                        hotelGuestEnt.put("State", "");
                                        hotelGuestEnt.put("PassportNumber", "");
                                        hotelGuestEnt.put("Gender", hotelGuestDetailEnts.get(0).getGender());
                                        hotelGuestEnt.put("BirthDate", DateHelper.getFormatedDate("dd/MM/yyyy", "dd MMM, yyyy", hotelGuestDetailEnts.get(0).getDob()));
                                        hotelGuestEnt.put("Nationality", "ES");
                                        hotelGuestEnt.put("GuestType", guestType(hotelGuestDetailEnts.get(0).getAge(), hotelGuestEnt));
                                        hotelGuestArray.put(hotelGuestEnt);
                                        hotelGuestDetailEnts.remove(0);
                                    } else {
                                        UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.please_add_guest_info));
                                        return;
                                    }
                                }
                                guestDistributionObj.put("NoOfAdults", HotelSearchModel.getInstance().getGuestWrapers()[i].getNofAdult() + "");
                                guestDistributionObj.put("NoOfChildrens", HotelSearchModel.getInstance().getGuestWrapers()[i].getNofChild() + "");
                                guestDistributionObj.put("NoOfInfant", HotelSearchModel.getInstance().getGuestWrapers()[i].getNofInfant() + "");
                                guestDistributionObj.put("Guests", hotelGuestArray);
                                guestDistributionArray.put(guestDistributionObj);
                            }
                        }


                        jsonObject.put("GuestDistribution", guestDistributionArray);

                        jsonObject.put("BookingCode", HotelSearchModel.getInstance().getBookingCode());

                        JSONObject cardDetails = new JSONObject();
                        cardDetails.put("CardCode", detailsEnt.getCardCode());
                        cardDetails.put("CardNumber", detailsEnt.getCardNumber());
                        cardDetails.put("SeriesCode", detailsEnt.getSeriesCode());
                        cardDetails.put("ExpireDate", DateHelper.getFormatedDate("yyyy-MM-dd", "dd MMM, yyyy", detailsEnt.getExpireDate()));
                        cardDetails.put("CardHolderName", detailsEnt.getCardHolderName());
                        cardDetails.put("CardHolderSurname", detailsEnt.getCardHolderName());
                        cardDetails.put("CardHolderEmail", "  ");

                        jsonObject.put("CardDetails", cardDetails);

//                            jsonObject.put("HotelListModel", "");

                        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

                        serviceHelper.enqueueCall(webService.PostHotelReservation(body, "Bearer " + prefHelper.getUser().getAuthToken()
                        ), WebServiceConstants.getHotelReservation);

                    } catch (Exception e) {
                        Log.e("tag", e.getMessage());
                    }

                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.please_add_payment_info));
                }

                break;
        }
    }


    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.getHotelReservation:

                ReservationEnt reservationEnt = (ReservationEnt) result;
                if (reservationEnt != null) {
                    HotelBookDetailFragment hotelBookDetailFragment = new HotelBookDetailFragment();
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.ReservationEnt, new Gson().toJson(reservationEnt));
                    hotelBookDetailFragment.setArguments(orderBundle);
                    getDockActivity().popBackStackTillEntry(1);
                    getDockActivity().replaceDockableFragment(hotelBookDetailFragment, HotelBookDetailFragment.class.getSimpleName());
                    hotelGuestDetailEnts = new ArrayList<>();
                    HotelSearchModel.getInstance().cleardata();
                }

                break;
        }
    }

    @Override
    public void itemClicked(Object object, int position) {

    }

    @Override
    public void itemClicked(Object object, boolean isfrom, int guestNo) {

        if (isfrom) {

        } else {
            detailsEnt = (CardDetailsEnt) object;
            tvCardName.setText(detailsEnt.getCardHolderType());
            btnAddPaymentInfo.setText(getString(R.string.edit));
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void setGuestinfoAdapter() {
        tot = HotelSearchModel.getInstance().getTotChild() + HotelSearchModel.getInstance().getTotAdult() + HotelSearchModel.getInstance().getTotInfant();
        ArrayList<Integer> totGuests = new ArrayList<>();


        for (int i = 0; i < HotelSearchModel.getInstance().getGuestWrapers().length; i++) {
            if (HotelSearchModel.getInstance().getGuestWrapers()[i] != null) {
                for (int j = 0; j < HotelSearchModel.getInstance().getGuestWrapers()[i].getNofAdult(); j++) {
                    totGuests.add(18);
                }
                for (int j = 0; j < HotelSearchModel.getInstance().getGuestWrapers()[i].getNofChild(); j++) {
                    totGuests.add(12);
                }
                for (int j = 0; j < HotelSearchModel.getInstance().getGuestWrapers()[i].getNofInfant(); j++) {
                    totGuests.add(2);
                }
            }
        }

        guestinfoAdapter = new GuestinfoAdapter(getMainActivity(), this, totGuests);
        rvAddInfo.setLayoutManager(new LinearLayoutManager(getMainActivity()));
        rvAddInfo.setAdapter(guestinfoAdapter);
    }


    private int guestType(String age, JSONObject hotelGuestEnt) {
        int ages = Integer.parseInt(age);
        try {
            if (ages < 2) {
                //infant
                hotelGuestEnt.put("IsInfant", true);
                return 3;
            } else if (ages >= 2 && ages <= 17) {
                //children
                hotelGuestEnt.put("IsInfant", false);
                return 2;
            } else {
                //adult
                hotelGuestEnt.put("IsInfant", false);
                return 1;
            }
        } catch (Exception ex) {
            return 208;
        }
    }
}