package com.app.amyal.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.amyal.R;
import com.app.amyal.entities.HotelGuestDetailEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.Utils;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.ui.views.AnyEditTextView;
import com.app.amyal.ui.views.TitleBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddGuestFragment extends BaseFragment {

    @BindView(R.id.edtName)
    AnyEditTextView edtName;
    @BindView(R.id.edtEmail)
    AnyEditTextView edtEmail;
    //    @BindView(R.id.edtPhone)
//    AnyEditTextView edtPhone;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.svmain)
    LinearLayout svmain;
    Unbinder unbinder;

    boolean isHotel = true;

    ItemClickListener itemClickListener;
    @BindView(R.id.spGender)
    Spinner spGender;
    @BindView(R.id.tvDOB)
    TextView tvDOB;
    private ArrayAdapter<String> genderAdapter;
    private String gender = "M";
    private int guestNo;
    private String age;
    private int guestAge;

    public static AddGuestFragment newInstance(boolean isHotel) {
        Bundle args = new Bundle();

        AddGuestFragment fragment = new AddGuestFragment();
        args.putBoolean("isHotel", isHotel);
        fragment.setArguments(args);
        return fragment;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            isHotel = getArguments().getBoolean("isHotel");
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.guest_info));
        titleBar.showBackButton();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_guest, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setGenderSpinnerData();
        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 0) {

                    gender = "F";

                } else {

                    gender = "M";

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gender = "";
            }
        });
        if (isHotel) {
            getMainActivity().setBackground(R.drawable.bg_hotel);
        } else {
            getMainActivity().setBackground(R.drawable.sp_dark);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public boolean isNumberValid(String number) {
        String regExpn = "\"^[+][0-9]{9,15}$";

        CharSequence inputStr = number;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }

    boolean isValidated() {
        return edtName.testValidity() && edtEmail.testValidity();
    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {

        if (isValidated()) {
//            if (edtPhone.getText().toString().length() >= 7 && edtPhone.getText().toString().length() <= 20) {
            if (!tvDOB.getText().toString().equals("")) {
                if (itemClickListener != null) {
                    HotelGuestDetailEnt hotelGuestDetailEnt = new HotelGuestDetailEnt(edtName.getText().toString(), age, edtEmail.getText().toString(), tvDOB.getText().toString(), gender, true);
                    itemClickListener.itemClicked(hotelGuestDetailEnt, true, guestNo);
                }

                getDockActivity().popFragment();
            } else
                UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.dob_required));
        } else {
//            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.username_required));
        }
    }

//    }

    private void setGenderSpinnerData() {

        ArrayList<String> genderList = new ArrayList<String>();
        genderList.add("Female");
        genderList.add("Male");

        genderAdapter = new ArrayAdapter<>(getDockActivity(), R.layout.spinner_item_2, genderList);
        genderAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spGender.setAdapter(genderAdapter);
        spGender.setSelection(1);
        genderAdapter.notifyDataSetChanged();

    }

    @OnClick(R.id.tvDOB)
    public void onClick() {
        UIHelper.hideSoftKeyboard(getMainActivity(), getView());
        Calendar maxAge = Calendar.getInstance();
        Calendar minAge = Calendar.getInstance();

        switch (guestAge) {
            case 18:
                maxAge.add(Calendar.YEAR, -guestAge); //adult
                minAge.add(Calendar.YEAR, -100);
                break;

            case 12:
                minAge.add(Calendar.YEAR, -guestAge); //children
                maxAge.add(Calendar.YEAR, -2);
                break;

            default:
                minAge.add(Calendar.YEAR, -guestAge); //infant
                break;
        }
        maxAge.add(Calendar.DAY_OF_MONTH, -1);
        openCalender(minAge, maxAge);
    }

    public void setGuestNo(int guestNo, int guestAge) {
        this.guestNo = guestNo;
        this.guestAge = guestAge;
    }


    private void openCalender(Calendar min, Calendar max) {
        final Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR) - age;
//        calendar.set();
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int year, int month, int day_of_month) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, (month + 1));
                calendar.set(Calendar.DAY_OF_MONTH, day_of_month);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                age = Utils.getAge(year + "", month + "", day_of_month + "");
                calendar.add(Calendar.MONTH, -1);
                tvDOB.setText(sdf.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());// TODO: used to hide previous date,month and year
//        calendarLimit.add(Calendar.YEAR, -2);
        dialog.getDatePicker().setMaxDate(max.getTimeInMillis());// TODO: used to hide future date,month and year
        dialog.getDatePicker().setMinDate(min.getTimeInMillis());// TODO: used to hide future date,month and year
        dialog.show();
    }
}