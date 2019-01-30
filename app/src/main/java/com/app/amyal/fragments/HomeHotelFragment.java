package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
import com.app.amyal.entities.GuestWraper;
import com.app.amyal.entities.HotelAreaEnt;
import com.app.amyal.entities.HotelListEnt;
import com.app.amyal.entities.HotelSearchModel;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.DateHelper;
import com.app.amyal.helpers.DialogHelper;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.interfaces.IDateSelectListner;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.ui.views.AnyTextView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class HomeHotelFragment extends BaseFragment implements IDateSelectListner, ItemClickListener {

    View view = null;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.tvSearch)
    AnyTextView tvSearch;
    @BindView(R.id.ivClose)
    ImageView ivClose;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvCheckIn)
    AnyTextView tvCheckIn;
    @BindView(R.id.tvCheckInDate)
    AnyTextView tvCheckInDate;
    @BindView(R.id.llCheckIn)
    LinearLayout llCheckIn;
    @BindView(R.id.tvCheckOut)
    AnyTextView tvCheckOut;
    @BindView(R.id.tvCheckOutDate)
    AnyTextView tvCheckOutDate;
    @BindView(R.id.llCheckOut)
    LinearLayout llCheckOut;
    @BindView(R.id.ivSwap)
    ImageView ivSwap;
    @BindView(R.id.tvTravellers)
    AnyTextView tvTravellers;
    @BindView(R.id.tvNoOfAdult)
    AnyTextView tvNoOfAdult;
    //    @BindView(R.id.llTravellers)
//    LinearLayout llTravellers;
    @BindView(R.id.BtnWork)
    ImageView BtnWork;
    @BindView(R.id.BtnLeisure)
    ImageView BtnLeisure;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    Unbinder unbinder;

    boolean isWork = true;
    @BindView(R.id.llWork)
    LinearLayout llWork;
    @BindView(R.id.llLeisure)
    LinearLayout llLeisure;

    Date leftDate;
    Date rightDate;

    HotelAreaEnt hotelAreaEnt;
    @BindView(R.id.tvWork)
    AnyTextView tvWork;
    @BindView(R.id.tvLeisure)
    AnyTextView tvLeisure;
    @BindView(R.id.spRoom)
    Spinner spRoom;
    @BindView(R.id.tvNoOfAdult1)
    AnyTextView tvNoOfAdult1;
    @BindView(R.id.tvNoOfAdult2)
    AnyTextView tvNoOfAdult2;
    private RequestBody body;
    private ArrayAdapter<String> RoomAdapter;
//    private GuestWraper[] guestWrapersArray;
//    private GuestWraper guestWraper;
//    private GuestWraper guestWraper1;
//    private GuestWraper guestWraper2;

    public HomeHotelFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (view == null)
            view = inflater.inflate(R.layout.fragment_home_hotel, container, false);
        HotelSearchModel.getInstance().cleardata();
        unbinder = ButterKnife.bind(this, view);
        tvNoOfAdult.setText(getString(R.string._1_adult_0_children_0_infant));
        tvNoOfAdult1.setText(getString(R.string._1_adult_0_children_0_infant));
        tvNoOfAdult2.setText(getString(R.string._1_adult_0_children_0_infant));
        spRoom.setSelection(0);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRoomSpinnerData();
        spRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        tvNoOfAdult.setVisibility(View.VISIBLE);
                        tvNoOfAdult1.setVisibility(View.GONE);
                        tvNoOfAdult2.setVisibility(View.GONE);
                        break;
                    case 1:
                        tvNoOfAdult.setVisibility(View.VISIBLE);
                        tvNoOfAdult1.setVisibility(View.VISIBLE);
                        tvNoOfAdult2.setVisibility(View.GONE);
                        break;
                    case 2:
                        tvNoOfAdult.setVisibility(View.VISIBLE);
                        tvNoOfAdult1.setVisibility(View.VISIBLE);
                        tvNoOfAdult2.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void getHotelList() {

        HotelSearchModel hotelSearchModel = HotelSearchModel.getInstance();
        String LangCode = AppConstants.LangCode;
        String CurCode = AppConstants.CurCode;

        if (prefHelper.isLogin()) {

            LangCode = prefHelper.getUser().getUser().getLanguageCode();
            CurCode = prefHelper.getUser().getUser().getCurrencyCode();

        }

        JSONObject mainObj = new JSONObject();
        JSONArray guestsArray = new JSONArray();
//        JSONObject guestObj = new JSONObject();
        try {
            int totCount = 0;
            for (int i = 0; i < spRoom.getSelectedItemPosition() + 1; i++) {
                JSONObject guestObj = new JSONObject();
                if (HotelSearchModel.getInstance().getGuestWrapers()[i] == null) {
                    HotelSearchModel.getInstance().getGuestWrapers()[i] = new GuestWraper();
                }
                hotelSearchModel.setTotAdult(hotelSearchModel.getTotAdult() + hotelSearchModel.getGuestWrapers()[i].getNofAdult());
                hotelSearchModel.setTotChild(hotelSearchModel.getTotChild() + hotelSearchModel.getGuestWrapers()[i].getNofChild());
                hotelSearchModel.setTotInfant(hotelSearchModel.getTotInfant() + hotelSearchModel.getGuestWrapers()[i].getNofInfant());

                guestObj.put("NoOfAdults", hotelSearchModel.getGuestWrapers()[i].getNofAdult());
                guestObj.put("NoOfChildrens", hotelSearchModel.getGuestWrapers()[i].getNofChild());
                guestObj.put("NoOfInfant", hotelSearchModel.getGuestWrapers()[i].getNofInfant());
                guestsArray.put(guestObj);
                totCount = i;
            }

            mainObj.put("CheckInTime", DateHelper.getFormatedDate("yyyy-MM-dd", "dd MMM,yyyy", hotelSearchModel.getCheckInDate()));
            mainObj.put("CheckOutTime", DateHelper.getFormatedDate("yyyy-MM-dd", "dd MMM,yyyy", hotelSearchModel.getCheckOutDate()));
            mainObj.put("ZoneCode", hotelSearchModel.getZoneCode());
            mainObj.put("HotelName", hotelSearchModel.getHotelName());
            mainObj.put("PriceRange", hotelSearchModel.getPriceRange());
            mainObj.put("Rating", hotelSearchModel.getRatting());
            mainObj.put("HotelCode", hotelSearchModel.getHotelCode());
            mainObj.put("TravellingFor", hotelSearchModel.getTravellingFor());
            mainObj.put("LanguageCode", LangCode);
            mainObj.put("CurrencyCode", CurCode);
            mainObj.put("CountryOfResidence", "ES");
            mainObj.put("PageIndex", 0);
            mainObj.put("DeviceID", prefHelper.getUser().getAuthToken());

            for (int i = totCount + 1; i < 3; i++) {
                hotelSearchModel.getGuestWrapers()[i] = null;
            }
            mainObj.put("Guests", guestsArray);
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mainObj.toString());
        } catch (Exception ex) {
            Log.e("", ex.getMessage());
        }

        serviceHelper.enqueueCall(webService.GetHotelList(
                body
        ), WebServiceConstants.getHotelList);

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.getHotelList:

                HotelListEnt hotelListEnt = (HotelListEnt) result;

                if (hotelListEnt != null) {

                    SelectHotelsFragment selectHotelsFragment = new SelectHotelsFragment();
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.HotelListEnt, new Gson().toJson(hotelListEnt));
                    selectHotelsFragment.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(selectHotelsFragment, SelectHotelsFragment.class.getSimpleName());
                }

                break;
        }
    }


    @OnClick({R.id.llSearch, R.id.llCheckIn, R.id.llCheckOut, R.id.ivSwap, R.id.tvNoOfAdult1, R.id.tvNoOfAdult, R.id.tvNoOfAdult2, R.id.llWork, R.id.llLeisure, R.id.btnSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llSearch:
                HotelAreaFragment hotelAreaFragment = HotelAreaFragment.newInstance();
                hotelAreaFragment.setItemClickListener(this, true, false, false);
                getDockActivity().replaceDockableFragment(hotelAreaFragment, HotelAreaFragment.class.getSimpleName());
                break;

            case R.id.llCheckIn:
                SelectHotelDatesFragment selectHotelDatesFragment = SelectHotelDatesFragment.newInstance();
                selectHotelDatesFragment.setiDateSelectListner(this);
                if (rightDate != null) {
                    selectHotelDatesFragment.setRightDate(rightDate);
                }
                Bundle orderBundle = new Bundle();
                orderBundle.putBoolean(AppConstants.IsCheckInDate, true);
                selectHotelDatesFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(selectHotelDatesFragment, SelectHotelDatesFragment.class.getSimpleName());
                break;

            case R.id.llCheckOut:
                selectHotelDatesFragment = SelectHotelDatesFragment.newInstance();
                selectHotelDatesFragment.setiDateSelectListner(this);
                if (leftDate != null) {
                    selectHotelDatesFragment.setLeftDate(leftDate);
                }
                orderBundle = new Bundle();
                orderBundle.putBoolean(AppConstants.IsCheckInDate, false);
                selectHotelDatesFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(selectHotelDatesFragment, SelectHotelDatesFragment.class.getSimpleName());
                break;

            case R.id.ivSwap:
                /*if (tvCheckInDate.getText().toString() != null) {
                    String checkin = tvCheckInDate.getText().toString();
                    if (tvCheckOutDate.getText().toString() != null) {
                        String to = tvCheckOutDate.getText().toString();
                        tvCheckInDate.setText(to);
                        tvCheckOutDate.setText(checkin);
                    }
                }*/
                break;

            case R.id.tvNoOfAdult1:
                final DialogHelper travellerDialog1 = new DialogHelper(getDockActivity());
                travellerDialog1.initHotelDialog(R.layout.dialog_no_of_room_guest, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GuestWraper guestwraper = HotelSearchModel.getInstance().getGuestWrapers()[1];
                        if (guestwraper == null)
                            guestwraper = new GuestWraper();
                        guestwraper.add(Integer.parseInt(travellerDialog1.getTravellerInfo(R.id.tvNoOfAdult)),
                                Integer.parseInt(travellerDialog1.getTravellerInfo(R.id.tvNoOfChild)),
                                Integer.parseInt(travellerDialog1.getTravellerInfo(R.id.tvNoOfInfants)));

                        HotelSearchModel.getInstance().getGuestWrapers()[1] = guestwraper;
                        tvNoOfAdult1.setText(guestwraper.getNofAdult() + " Adult, " + guestwraper.getNofChild() + " Child, " + guestwraper.getNofInfant() + " Infant");

                        travellerDialog1.hideDialog();
                    }
                });
                travellerDialog1.setCancelable(false);
                travellerDialog1.showDialog();
                break;

            case R.id.tvNoOfAdult:
                final DialogHelper travellerDialog = new DialogHelper(getDockActivity());
                travellerDialog.initHotelDialog(R.layout.dialog_no_of_room_guest, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GuestWraper guestwraper = HotelSearchModel.getInstance().getGuestWrapers()[0];
                        if (guestwraper == null)
                            guestwraper = new GuestWraper();
                        guestwraper.add(Integer.parseInt(travellerDialog.getTravellerInfo(R.id.tvNoOfAdult)),
                                Integer.parseInt(travellerDialog.getTravellerInfo(R.id.tvNoOfChild)),
                                Integer.parseInt(travellerDialog.getTravellerInfo(R.id.tvNoOfInfants)));


                        HotelSearchModel.getInstance().getGuestWrapers()[0] = guestwraper;
                        tvNoOfAdult.setText(guestwraper.getNofAdult() + " Adult, " + guestwraper.getNofChild() + " Child, " + guestwraper.getNofInfant() + " Infant");


                        travellerDialog.hideDialog();
                    }
                });
                travellerDialog.setCancelable(false);
                travellerDialog.showDialog();
                break;

            case R.id.tvNoOfAdult2:
                final DialogHelper travellerDialog2 = new DialogHelper(getDockActivity());
                travellerDialog2.initHotelDialog(R.layout.dialog_no_of_room_guest, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        GuestWraper guestwraper = HotelSearchModel.getInstance().getGuestWrapers()[2];
                        if (guestwraper == null)
                            guestwraper = new GuestWraper();
                        guestwraper.add(Integer.parseInt(travellerDialog2.getTravellerInfo(R.id.tvNoOfAdult)),
                                Integer.parseInt(travellerDialog2.getTravellerInfo(R.id.tvNoOfChild)),
                                Integer.parseInt(travellerDialog2.getTravellerInfo(R.id.tvNoOfInfants)));

                        HotelSearchModel.getInstance().getGuestWrapers()[2] = guestwraper;
                        tvNoOfAdult2.setText(guestwraper.getNofAdult() + " Adult, " + guestwraper.getNofChild() + " Child, " + guestwraper.getNofInfant() + " Infant");

                        travellerDialog2.hideDialog();
                    }
                });
                travellerDialog2.setCancelable(false);
                travellerDialog2.showDialog();
                break;

            case R.id.llWork:
                isWork = true;
                BtnWork.setImageResource(R.drawable.work1);
                BtnLeisure.setImageResource(R.drawable.leisure);
                tvWork.setTextColor(getResources().getColor(R.color.darkBrown));
                tvLeisure.setTextColor(getResources().getColor(R.color.dark_grey));
                break;

            case R.id.llLeisure:
                isWork = false;
                BtnWork.setImageResource(R.drawable.work);
                BtnLeisure.setImageResource(R.drawable.leisure1);
                tvWork.setTextColor(getResources().getColor(R.color.dark_grey));
                tvLeisure.setTextColor(getResources().getColor(R.color.darkBrown));
                break;

            case R.id.btnSearch:

                if (tvSearch.getText().toString() != null && tvSearch.getText().toString().length() > 0) {
                    if (tvCheckInDate.getText().toString() != null && tvCheckInDate.getText().toString().length() > 0) {
                        if (tvCheckOutDate.getText().toString() != null && tvCheckOutDate.getText().toString().length() > 0) {

                            String checkInDate = DateHelper.dateFormat(leftDate.toString(), DateHelper.DATE_FORMAT, DateHelper.DATE_TIME_FORMAT2);
                            String checkOutDate = DateHelper.dateFormat(rightDate.toString(), DateHelper.DATE_FORMAT, DateHelper.DATE_TIME_FORMAT2);
                            String purpose = "";

                            if (isWork) {
                                purpose = getString(R.string.work);
                            } else {
                                purpose = getString(R.string.leisure);
                            }
                            //add in singleton
                            HotelSearchModel.getInstance().addData(checkInDate, checkOutDate, HotelSearchModel.getInstance().getGuestWrapers(), hotelAreaEnt.getZoneCode() + "", "", "", "", "", purpose, "", "", "", "");
                            getHotelList();

                        } else {
                            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_checkout_date));
                        }
                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_checkin_date));
                    }
                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_location));
                }

                break;
        }
    }

    @Override
    public void onDatesSelected(String fromDate, String toDate) {


    }

    @Override
    public void onDateSelected(String fromDate, Date leftDate) {

    }

    @Override
    public void onDatesSelected(String fromDate, String toDate, Date todate, Date fromdate) {

        leftDate = todate;
        rightDate = fromdate;

        if (fromDate != null && fromDate.length() > 0) {
            tvCheckInDate.setText(fromDate);
        }
        if (toDate != null && toDate.length() > 0) {
            tvCheckOutDate.setText(toDate);
        }

    }

    @Override
    public void itemClicked(Object object, int position) {

    }

    @Override
    public void itemClicked(Object object, boolean isfrom, int i) {

        hotelAreaEnt = (HotelAreaEnt) object;
        if (hotelAreaEnt != null && hotelAreaEnt.getName().length() > 0)
            tvSearch.setText(hotelAreaEnt.getName());
        else
            tvSearch.setText("");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void setRoomSpinnerData() {

        ArrayList<String> genderList = new ArrayList<String>();
        genderList.add("1 Room");
        genderList.add("2 Rooms");
        genderList.add("3 Rooms");

        RoomAdapter = new ArrayAdapter<>(getDockActivity(), R.layout.spinner_item, genderList);
        RoomAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spRoom.setAdapter(RoomAdapter);
        spRoom.setSelection(0);
        RoomAdapter.notifyDataSetChanged();

    }
}
