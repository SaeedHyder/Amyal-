package com.app.amyal.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.app.amyal.R;
import com.app.amyal.entities.CarListEnt;
import com.app.amyal.entities.CarSearchModel;
import com.app.amyal.entities.FlightTypeEnt;
import com.app.amyal.entities.HotelAreaEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.Utils;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.DateHelper;
import com.app.amyal.helpers.TimePickerHelper;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.interfaces.IDateSelectListner;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.ui.adapters.RecyclerViewAdapter;
import com.app.amyal.ui.views.AnyEditTextView;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.AnyTextView1;
import com.app.amyal.ui.views.AutoCompleteLocation;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeCarFragment extends BaseFragment implements ItemClickListener, IDateSelectListner {


    Unbinder unbinder;
    @BindView(R.id.tvPick_up)
    AnyTextView tvPickUp;

    @BindView(R.id.edtPickup)
    AutoCompleteLocation edtPickup;
    @BindView(R.id.llPickUp)
    LinearLayout llPickUp;
    @BindView(R.id.tvDrop_off)
    AnyTextView tvDropOff;
    @BindView(R.id.spCarType)
    Spinner spCarType;
    @BindView(R.id.tvPickAddress)
    AnyTextView1 tvPickAddress;
    @BindView(R.id.tvDropAddress)
    AnyTextView1 tvDropAddress;
    @BindView(R.id.edtDropoff)
    AutoCompleteLocation edtDropoff;
    @BindView(R.id.llDropOff)
    LinearLayout llDropOff;
    @BindView(R.id.ivSwap)
    ImageView ivSwap;
    @BindView(R.id.tvSelectDates)
    AnyTextView tvSelectDates;
    @BindView(R.id.tvPickUpDate)
    AnyTextView tvPickUpDate;
    @BindView(R.id.tvDropOffDate)
    AnyTextView tvDropOffDate;
    @BindView(R.id.llSelectDates)
    LinearLayout llSelectDates;
    @BindView(R.id.tvPick_upTime)
    AnyTextView tvPickUpTime;
    @BindView(R.id.llPickUpTime)
    LinearLayout llPickUpTime;
    @BindView(R.id.tvDrop_offTime)
    AnyTextView tvDropOffTime;
    @BindView(R.id.llDropOffTime)
    LinearLayout llDropOffTime;
    @BindView(R.id.edtAge)
    AnyEditTextView edtAge;
    @BindView(R.id.rvCarsType)
    RecyclerView rvCarsType;
    @BindView(R.id.ivEconomy)
    ImageView ivEconomy;
    @BindView(R.id.tvEconomy)
    AnyTextView tvEconomy;
    @BindView(R.id.llEconomy)
    LinearLayout llEconomy;
    @BindView(R.id.ivBusiness)
    ImageView ivBusiness;
    @BindView(R.id.tvBusiness)
    AnyTextView tvBusiness;
    @BindView(R.id.llBusiness)
    LinearLayout llBusiness;
    @BindView(R.id.ivFirst)
    ImageView ivFirst;
    @BindView(R.id.tvFirst)
    AnyTextView tvFirst;
    @BindView(R.id.llFirst)
    LinearLayout llFirst;
    @BindView(R.id.btnSearch)
    Button btnSearch;


    private RecyclerViewAdapter<FlightTypeEnt> rvAdapter;
    private List<FlightTypeEnt> flightTypeEntList;

    View view = null;

    //String pickUpAddress, dropOffAddress = "";

    Date leftDate;
    Date rightDate;

    ArrayList<String> carTypes_list = new ArrayList<>();
    ArrayAdapter<String> carTypesAdapter;

    String carTypeCode = "";

    String pickUpZoneCode = "";
    String dropOffZoneCode = "";

    public HomeCarFragment() {
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
            view = inflater.inflate(R.layout.fragment_home_car, container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCarTypesSpinnerData();

        spCarType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                carTypeCode = Utils.getCarTypeCode(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void setCarTypesSpinnerData() {

        carTypes_list = new ArrayList<>();
        carTypes_list.add("Select Car Type");
        carTypes_list.add("Mini");
        carTypes_list.add("Mini Elite");
        carTypes_list.add("Economy");
        carTypes_list.add("Economy Elite");
        carTypes_list.add("Compact");
        carTypes_list.add("Compact Elite");
        carTypes_list.add("Intermediate");
        carTypes_list.add("Intermediate Elite");
        carTypes_list.add("Standard");
        carTypes_list.add("Standard Elite");
        carTypes_list.add("Full-Size");
        carTypes_list.add("Full-Size Elite");
        carTypes_list.add("Premium");
        carTypes_list.add("Premium Elite");
        carTypes_list.add("Luxury");
        carTypes_list.add("Luxury Elite");
        carTypes_list.add("Oversize");
        carTypes_list.add("Special");


        carTypesAdapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner_item_2, carTypes_list);
        carTypesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_2);
        spCarType.setAdapter(carTypesAdapter);
        spCarType.setSelection(0);

        carTypesAdapter.notifyDataSetChanged();
    }

    /*public void bindData() {

        flightTypeEntList = new ArrayList<>();
        flightTypeEntList.add(new FlightTypeEnt(1, "drawable://" + R.drawable.car_economy1, "drawable://" + R.drawable.car_economy, "Economy", true));
        flightTypeEntList.add(new FlightTypeEnt(1, "drawable://" + R.drawable.car_business, "drawable://" + R.drawable.car_business1, "Business", false));
        flightTypeEntList.add(new FlightTypeEnt(1, "drawable://" + R.drawable.firstclass, "drawable://" + R.drawable.firstclass1, "First-Class", false));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getDockActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvAdapter = new RecyclerViewAdapter<>(flightTypeEntList, new CarTypeBinder(this), getDockActivity(), R.layout.home_flight_type_item);
        rvCarsType.setLayoutManager(linearLayoutManager);
        rvCarsType.setAdapter(rvAdapter);

        rvAdapter.notifyDataSetChanged();

    }*/

    public void getCarList(CarSearchModel carSearchModel) {

        String LangCode = AppConstants.LangCode;
        String CurCode = AppConstants.CurCode;
        String countryCode = AppConstants.CountryCode;

        if (prefHelper.isLogin()) {
            LangCode = prefHelper.getUser().getUser().getLanguageCode();
            CurCode = prefHelper.getUser().getUser().getCurrencyCode();
            countryCode = prefHelper.getUser().getUser().getCountryCode();
        }

        carSearchModel.setCurrencyCode(CurCode);
        carSearchModel.setLanguageCode(LangCode);
        carSearchModel.setCountryOfResidence(countryCode);

        prefHelper.putCarSearchModel(carSearchModel);

        serviceHelper.enqueueCall(webService.GetCarList(
                carSearchModel.getPickUpLocationCode(),
                carSearchModel.getDropOffLocationCode(),
                carSearchModel.getPickUpDate(),
                carSearchModel.getDropOffDate(),
                carSearchModel.getPickUpTime(),
                carSearchModel.getDropOffTime(),
                carSearchModel.getCarCategory(),
                carSearchModel.getCurrencyCode(),
                carSearchModel.getLanguageCode(),
                carSearchModel.getCountryOfResidence(),
                carSearchModel.getDriverAge(),
                "",
                ""
        ), WebServiceConstants.getCarList);

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.getCarList:

                CarListEnt carListEnt = (CarListEnt) result;

                if (carListEnt != null) {

                    SelectCarFragment selectCarFragment = new SelectCarFragment();
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.CarListEnt, new Gson().toJson(carListEnt));
                    selectCarFragment.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(selectCarFragment, SelectCarFragment.class.getSimpleName());
                }

                break;
        }
    }

    @OnClick({R.id.ivSwap, R.id.llSelectDates, R.id.btnSearch, R.id.llPickUpTime, R.id.llDropOffTime, R.id.llPickUp, R.id.llDropOff})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivSwap:

                if (tvPickAddress.getText().toString() != null && tvDropAddress.getText().toString() != null) {
                    if (tvPickAddress.getText().toString().length() > 0 && tvDropAddress.getText().toString().length() > 0) {
                        String pick = tvPickAddress.getText().toString();
                        String drop = tvPickAddress.getText().toString();
                        tvPickAddress.setText(drop);
                        tvDropAddress.setText(pick);

                        pick = pickUpZoneCode;
                        drop = dropOffZoneCode;

                        pickUpZoneCode = drop;
                        dropOffZoneCode = pick;
                    }
                }

                break;
            case R.id.llSelectDates:
                SelectCarDatesFragment selectCarDatesFragment = SelectCarDatesFragment.newInstance();
                selectCarDatesFragment.setiDateSelectListner(this);
                getDockActivity().replaceDockableFragment(selectCarDatesFragment, SelectFlightDates.class.getSimpleName());
                break;
            case R.id.btnSearch:

                if (tvPickAddress.getText().toString() != null && tvPickAddress.getText().toString().length() > 0) {
                    if (tvDropAddress.getText().toString() != null && tvDropAddress.getText().toString().length() > 0) {
                        if (tvPickUpDate.getText().toString() != null && tvPickUpDate.getText().toString().length() > 0) {
                            if (tvDropOffDate.getText().toString() != null && tvDropOffDate.getText().toString().length() > 0) {
                                if (tvPickUpTime.getText().toString() != null && tvPickUpTime.getText().toString().length() > 0) {
                                    if (tvDropOffTime.getText().toString() != null && tvDropOffTime.getText().toString().length() > 0) {
                                        if (edtAge.getText().toString() != null && edtAge.getText().toString().length() > 0) {

                                            String pickUpDate = DateHelper.dateFormat(leftDate.toString(), DateHelper.DATE_FORMAT, DateHelper.DATE_TIME_FORMAT2);
                                            String DropOfDate = DateHelper.dateFormat(rightDate.toString(), DateHelper.DATE_FORMAT, DateHelper.DATE_TIME_FORMAT2);

                                            String pickUpTime = DateHelper.dateFormat(tvDropOffTime.getText().toString(), DateHelper.TIME_FORMAT, DateHelper.TIME_FORMAT3);
                                            String dropOffTime = DateHelper.dateFormat(tvDropOffTime.getText().toString(), DateHelper.TIME_FORMAT, DateHelper.TIME_FORMAT3);

                                            if (leftDate.equals(rightDate)) {

                                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

                                                int hours = 0;

                                                try {
                                                    Date date1 = simpleDateFormat.parse(tvPickUpTime.getText().toString());
                                                    Date date2 = simpleDateFormat.parse(tvDropOffTime.getText().toString());

                                                    long difference = date2.getTime() - date1.getTime();
                                                    int days = (int) (difference / (1000 * 60 * 60 * 24));
                                                    hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                if (hours < 1) {
                                                    UIHelper.showLongToastInCenter(getDockActivity(), "Pick up and drop off time cannot be less then one hour");
                                                } else {

                                                    getCarList(new CarSearchModel(pickUpZoneCode, dropOffZoneCode, pickUpDate, DropOfDate, pickUpTime,
                                                            dropOffTime, carTypeCode, "", "", "", edtAge.getText().toString(), "", ""));


                                                    //getDockActivity().replaceDockableFragment(SelectCarFragment.newInstance(), SelectCarFragment.class.getSimpleName());
                                                }

                                            } else {

                                                getCarList(new CarSearchModel(pickUpZoneCode, dropOffZoneCode, pickUpDate, DropOfDate, pickUpTime,
                                                        dropOffTime, carTypeCode, "", "", "", edtAge.getText().toString(), "", ""));


                                                //getDockActivity().replaceDockableFragment(SelectCarFragment.newInstance(), SelectCarFragment.class.getSimpleName());
                                            }

                                        } else {
                                            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_enter_driver_age));
                                        }
                                    } else {
                                        UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_drop_time));
                                    }
                                } else {
                                    UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_pick_time));
                                }
                            } else {
                                UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_pick_date));
                            }
                        } else {
                            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_dep_date));
                        }
                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_dropoff_location));
                    }
                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_pickup_location));
                }

                //UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.will_be_implemented_in_beta));

                break;

            case R.id.llPickUpTime:
                initTimePicker(tvPickUpTime);
                break;

            case R.id.llDropOffTime:
                initTimePicker(tvDropOffTime);
                break;

            case R.id.llPickUp:

                HotelAreaFragment hotelAreaFragment = HotelAreaFragment.newInstance();
                hotelAreaFragment.setItemClickListener(this, true, false,false);
                getDockActivity().replaceDockableFragment(hotelAreaFragment, HotelAreaFragment.class.getSimpleName());

                break;

            case R.id.llDropOff:

                hotelAreaFragment = HotelAreaFragment.newInstance();
                hotelAreaFragment.setItemClickListener(this, false, false,false);
                getDockActivity().replaceDockableFragment(hotelAreaFragment, HotelAreaFragment.class.getSimpleName());

                break;

        }
    }

    private void initTimePicker(final AnyTextView textView) {
        Calendar calendar = Calendar.getInstance();
        final TimePickerHelper timePicker = new TimePickerHelper();
        timePicker.initTimeDialog(getDockActivity(), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                textView.setText(timePicker.getTime(hourOfDay, minute));
                //Time = timePicker.get24HourTime(hourOfDay, minute);
            }
        }, DateFormat.is24HourFormat(getMainActivity()));

        timePicker.showTime();
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
    public void itemClicked(Object object, boolean isfrom,int i) {

        HotelAreaEnt location = (HotelAreaEnt) object;

        if (location != null && location.getName().length() > 0) {

            if (isfrom) {
                tvPickAddress.setText(location.getName());
                pickUpZoneCode = location.getZoneCode() + "";
            } else {
                tvDropAddress.setText(location.getName());
                dropOffZoneCode = location.getZoneCode() + "";
            }
        } else {
            if (isfrom) {
                tvPickAddress.setText("");
                pickUpZoneCode = "";
                pickUpZoneCode = "";
            } else {
                tvDropAddress.setText("");
                dropOffZoneCode = "";
            }
        }

    }

    @Override
    public void onDatesSelected(String fromDate, String toDate) {
       /* if (fromDate != null)
            tvPickUpDate.setText(fromDate + " - ");

        if (toDate != null)
            tvDropOffDate.setText(toDate);*/
    }

    @Override
    public void onDateSelected(String fromDate, Date leftDate) {

    }

    @Override
    public void onDatesSelected(String fromDate, String toDate, Date todate, Date fromdate) {

        if (fromDate != null)
            tvPickUpDate.setText(fromDate + " - ");

        if (toDate != null)
            tvDropOffDate.setText(toDate);

        leftDate = todate;
        rightDate = fromdate;

    }

}
