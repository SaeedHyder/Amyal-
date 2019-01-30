package com.app.amyal.fragments;

import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.amyal.R;
import com.app.amyal.entities.FlightTravellerEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.Utils;
import com.app.amyal.helpers.DatePickerHelper;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.retrofit.GsonFactory;
import com.app.amyal.ui.views.AnyEditTextView;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.TitleBar;
import com.hbb20.CountryCodePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TravelerInformationFragment extends BaseFragment {

    @BindView(R.id.edtFirstName)
    AnyEditTextView edtFirstName;
    @BindView(R.id.edtMiddleName)
    AnyEditTextView edtMiddleName;
    @BindView(R.id.edtLastName)
    AnyEditTextView edtLastName;
    @BindView(R.id.btnMale)
    AnyTextView btnMale;
    @BindView(R.id.btnFemale)
    AnyTextView btnFemale;
    @BindView(R.id.llGender)
    LinearLayout llGender;
    @BindView(R.id.llDOB)
    LinearLayout llDOB;
    @BindView(R.id.edtPhone)
    AnyEditTextView edtPhone;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.svmain)
    LinearLayout svmain;
    Unbinder unbinder;
    @BindView(R.id.tvDob)
    AnyTextView tvDob;
    @BindView(R.id.btnEdit)
    Button btnEdit;
    @BindView(R.id.btnDelete)
    Button btnDelete;
    @BindView(R.id.llEdit)
    LinearLayout llEdit;

    boolean IsEdit;
    boolean isFemale = false;
    String DateOfBirth = "";

    int position;
    FlightTravellerEnt flightTravellerEnts;
    @BindView(R.id.edtEmail)
    AnyEditTextView edtEmail;

    ItemClickListener itemClickListener;
    @BindView(R.id.edtPassportNo)
    AnyEditTextView edtPassportNo;
    @BindView(R.id.ccp)
    CountryCodePicker ccp;
    @BindView(R.id.edtCity)
    AnyEditTextView edtCity;
    @BindView(R.id.edtAddress)
    AnyEditTextView edtAddress;
    @BindView(R.id.tvExpiaryDate)
    AnyTextView tvExpiaryDate;
    @BindView(R.id.llExpiaryDate)
    LinearLayout llExpiaryDate;
    private String age;

    public static TravelerInformationFragment newInstance() {

        TravelerInformationFragment travelerInformationFragment = new TravelerInformationFragment();

        return travelerInformationFragment;
    }

    public void setListner(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //IsEdit = getArguments().getBoolean(AppConstants.IsEdit);
            IsEdit = false;
            position = getArguments().getInt(AppConstants.position);
            flightTravellerEnts = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.FlightTravellerEnt), FlightTravellerEnt.class);
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_traveler_details, container, false);

        getMainActivity().setBackground(R.drawable.bg_flight);

        unbinder = ButterKnife.bind(this, view);

        if (IsEdit) {
            llEdit.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.GONE);
            enableDisableFields(false);
            enableEditText(false);
        } else {
            btnSubmit.setVisibility(View.VISIBLE);
            llEdit.setVisibility(View.GONE);
            enableDisableFields(true);
            enableEditText(true);
        }


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (flightTravellerEnts != null && flightTravellerEnts.getEmail() != null) {
            setData();
        }

    }

    public void setData() {

        edtFirstName.setText(flightTravellerEnts.getName());
        edtLastName.setText(flightTravellerEnts.getSurName());
        //edtMiddleName.setText(flightTravellerEnts.getMiddleName());
        edtPhone.setText(flightTravellerEnts.getPhone());
        edtEmail.setText(flightTravellerEnts.getEmail());
        edtPassportNo.setText(flightTravellerEnts.getPassportNumber());

        if (!flightTravellerEnts.getIsFemale()) {
            isFemale = false;
            btnMale.setTextColor(getResources().getColor(R.color.darkBrown));
            btnFemale.setTextColor(getResources().getColor(R.color.white));
        } else {
            isFemale = true;
            btnMale.setTextColor(getResources().getColor(R.color.white));
            btnFemale.setTextColor(getResources().getColor(R.color.darkBrown));
        }

        edtCity.setText(flightTravellerEnts.getCity());
        edtAddress.setText(flightTravellerEnts.getAddress());

        tvDob.setText(flightTravellerEnts.getBirthDate());
        DateOfBirth = flightTravellerEnts.getBirthDate();

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.add_travlerdetail));
        titleBar.showBackButton();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initDOBDialog(final AnyTextView textView) {
        UIHelper.hideSoftKeyboard(getMainActivity(), getView());
        Calendar calendar = Calendar.getInstance();
        final DatePickerHelper datePickerHelper = new DatePickerHelper();
        datePickerHelper.initDateDialog(
                getDockActivity(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
                , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Date date = new Date();
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // and get that as a Date
                        Date dateSpecified = c.getTime();

                        if (dateSpecified.before(date)) {
                            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.valid_date));
                        } else {
                            //DateOfBirth = dateSpecified;
                            String predate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
                            DateOfBirth = predate;
                            textView.setText(predate);
                            textView.setPaintFlags(Typeface.BOLD);
                        }
                    }
                }, "Select DOB");
        datePickerHelper.showDate();
    }

    public boolean validatePassoirtNumber() {

        Pattern pattern = Pattern.compile("^(?!^0+$)[a-zA-Z0-9]{3,20}$");
        Matcher matcher = pattern.matcher(edtPassportNo.getText().toString());
        if (matcher.find()) {
            return true;
        } else {
            edtPassportNo.setError(getString(R.string.invalid_passport));
            return false;
        }

    }

    public boolean validate() {

        return edtFirstName.testValidity() && edtPhone.testValidity() && edtEmail.testValidity() && edtPassportNo.testValidity() && validatePassoirtNumber();

    }

    public void enableDisableFields(boolean isEdit) {

        llDOB.setEnabled(isEdit);
        btnMale.setEnabled(isEdit);
        btnFemale.setEnabled(isEdit);
        llDOB.setFocusable(isEdit);
        btnMale.setFocusable(isEdit);
        btnFemale.setFocusable(isEdit);
    }

    public void enableEditText(boolean isEdit) {

        edtFirstName.setFocusable(isEdit);
        edtFirstName.setFocusableInTouchMode(isEdit);

        edtLastName.setFocusable(isEdit);
        edtLastName.setFocusableInTouchMode(isEdit);

        //edtMiddleName.setFocusable(isEdit);
        //edtMiddleName.setFocusableInTouchMode(isEdit);

        edtEmail.setFocusable(isEdit);
        edtEmail.setFocusableInTouchMode(isEdit);

        edtPhone.setFocusable(isEdit);
        edtPhone.setFocusableInTouchMode(isEdit);

        edtPassportNo.setFocusable(isEdit);
        edtPassportNo.setFocusableInTouchMode(isEdit);

    }

    @OnClick({R.id.btnMale, R.id.btnFemale, R.id.llDOB, R.id.btnSubmit, R.id.btnEdit, R.id.btnDelete, R.id.llExpiaryDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btnMale:
                isFemale = false;
                btnMale.setTextColor(getResources().getColor(R.color.darkBrown));
                btnFemale.setTextColor(getResources().getColor(R.color.white));
                break;

            case R.id.btnFemale:
                isFemale = true;
                btnMale.setTextColor(getResources().getColor(R.color.white));
                btnFemale.setTextColor(getResources().getColor(R.color.darkBrown));
                break;

            case R.id.llDOB:
                UIHelper.hideSoftKeyboard(getMainActivity(), getView());
                guestType(tvDob);
                break;

            case R.id.btnSubmit:

                if (validate()) {
                    if (DateOfBirth.length() > 0) {
                        if (edtPhone.getText().toString().length() >= 7 && edtPhone.getText().toString().length() <= 20) {

                            if (edtAddress.getText().length() <= 2) {
                                UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.valid_address));
                                return;
                            }
                            if (edtCity.getText().length() <= 2) {
                                UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.valid_city));
                                return;
                            }
                            IsEdit = false;

                            flightTravellerEnts.setName(edtFirstName.getText().toString());
                            //flightTravellerEnts.setMiddleName(edtMiddleName.getText().toString());
                            flightTravellerEnts.setSurName(edtLastName.getText().toString());
                            flightTravellerEnts.setEmail(edtEmail.getText().toString());
                            flightTravellerEnts.setPhone(edtPhone.getText().toString());
                            flightTravellerEnts.setBirthDate(tvDob.getText().toString());
                            flightTravellerEnts.setIsFemale(isFemale);
                            flightTravellerEnts.setPassportNumber(edtPassportNo.getText().toString());
                            flightTravellerEnts.setCountryCode(ccp.getSelectedCountryNameCode());
                            flightTravellerEnts.setCountry(ccp.getSelectedCountryName());
                            flightTravellerEnts.setAddress(edtAddress.getText().toString());
                            flightTravellerEnts.setCity(edtCity.getText().toString());
                            flightTravellerEnts.setPassportExpiary(tvExpiaryDate.getText().toString());
                            flightTravellerEnts.setAge(age);
                            if (itemClickListener != null) {
                                itemClickListener.itemClicked(flightTravellerEnts, position);
                                getDockActivity().popFragment();
                            }


                        } else {
                            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_enter_valid_phone));
                        }
                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_birth_date));
                    }
                }

                break;

            case R.id.btnEdit:

                if (IsEdit) {
                    IsEdit = false;
                    btnSubmit.setVisibility(View.VISIBLE);
                    llEdit.setVisibility(View.GONE);
                    enableDisableFields(true);
                    enableEditText(true);
                }

                break;

            case R.id.btnDelete:
                getDockActivity().popFragment();
                break;

            case R.id.llExpiaryDate:
                initDOBDialog(tvExpiaryDate);
                break;
        }
    }

    private void guestType(TextView textView) {
        Calendar maxAge = Calendar.getInstance();
        Calendar minAge = Calendar.getInstance();
        String info = flightTravellerEnts.getTravellerNo();
        if (info.contains("Infant")) {
            flightTravellerEnts.setGuestType(3);
            minAge.add(Calendar.YEAR, -1); //infant
        } else if ((info.contains("Children"))) {
            flightTravellerEnts.setGuestType(2);
            minAge.add(Calendar.YEAR, -12); //children
            maxAge.add(Calendar.YEAR, -2);
        } else if (info.contains("Adult") || info.contains("Main")) {
            flightTravellerEnts.setGuestType(1);
            maxAge.add(Calendar.YEAR, -18); //adult
            minAge.add(Calendar.YEAR, -100);
        }

        maxAge.add(Calendar.DAY_OF_MONTH, -1);
        openCalender(minAge, maxAge, textView);
    }

    private void openCalender(Calendar min, Calendar max, final TextView textView) {
        final Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR) - age;
//        calendar.set();
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int year, int month, int day_of_month) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, (month + 1));
                calendar.set(Calendar.DAY_OF_MONTH, day_of_month);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                age = Utils.getAge(year + "", month + "", day_of_month + "");
                calendar.add(Calendar.MONTH, -1);
                textView.setText(sdf.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());// TODO: used to hide previous date,month and year
//        calendarLimit.add(Calendar.YEAR, -2);
        dialog.getDatePicker().setMaxDate(max.getTimeInMillis());// TODO: used to hide future date,month and year
        dialog.getDatePicker().setMinDate(min.getTimeInMillis());// TODO: used to hide future date,month and year
        dialog.show();
    }

}
