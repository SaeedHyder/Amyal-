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

import com.app.amyal.R;
import com.app.amyal.entities.FlightTravellerEnt;
import com.app.amyal.entities.HotelGuestDetailEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.helpers.DatePickerHelper;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.retrofit.GsonFactory;
import com.app.amyal.ui.views.AnyEditTextView;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.TitleBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by khan_muhammad on 12/6/2017.
 */

public class AddDriverFragment extends BaseFragment {


    boolean isFemale = false;
    String DateOfBirth = "";
    @BindView(R.id.edtName)
    AnyEditTextView edtName;
    @BindView(R.id.btnMale)
    AnyTextView btnMale;
    @BindView(R.id.btnFemale)
    AnyTextView btnFemale;
    @BindView(R.id.llGender)
    LinearLayout llGender;
    @BindView(R.id.tvDob)
    AnyTextView tvDob;
    @BindView(R.id.llDOB)
    LinearLayout llDOB;
    @BindView(R.id.edtPhone)
    AnyEditTextView edtPhone;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.svmain)
    LinearLayout svmain;
    Unbinder unbinder;

    ItemClickListener itemClickListener;

    FlightTravellerEnt flightTravellerEnts;
    @BindView(R.id.edtPassportNo)
    AnyEditTextView edtPassportNo;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public static AddDriverFragment newInstance() {

        return new AddDriverFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            flightTravellerEnts = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.FlightTravellerEnt), FlightTravellerEnt.class);
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_driver, container, false);

        getMainActivity().setBackground(R.drawable.bg_car);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (flightTravellerEnts != null && flightTravellerEnts.getName() != null) {
            setData();
        }

    }

    public void setData() {

        edtName.setText(flightTravellerEnts.getName());
        edtPhone.setText(flightTravellerEnts.getPhone());
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

        tvDob.setText(flightTravellerEnts.getBirthDate());
        DateOfBirth = flightTravellerEnts.getBirthDate();

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.add_infor));
        titleBar.showBackButton();
    }

    private void initDOBDialog(final AnyTextView textView) {

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
                        if (dateSpecified.after(date)) {
                            UIHelper.showShortToastInCenter(getDockActivity(), "Please enter valid date.");
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

    public boolean validate() {

        return edtName.testValidity() && edtPhone.testValidity();

    }

    @OnClick({R.id.btnMale, R.id.btnFemale, R.id.llDOB, R.id.btnSubmit})
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
                initDOBDialog(tvDob);
                break;

            case R.id.btnSubmit:

                if (validate()) {
                    if (DateOfBirth.length() > 0) {

                        flightTravellerEnts = new FlightTravellerEnt();

                        flightTravellerEnts.setName(edtName.getText().toString());
                        flightTravellerEnts.setSurName(edtName.getText().toString());
                        flightTravellerEnts.setPhone(edtPhone.getText().toString());
                        flightTravellerEnts.setBirthDate(tvDob.getText().toString());
                        flightTravellerEnts.setIsFemale(isFemale);
                        flightTravellerEnts.setPassportNumber(edtPassportNo.getText().toString());

                        if (itemClickListener != null) {
                            itemClickListener.itemClicked(flightTravellerEnts, true,-1);
                        }

                        getDockActivity().popFragment();

                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), R.string.please_select_birth_date);
                    }
                }

                break;
        }
    }
}
