package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.amyal.R;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.interfaces.IDateSelectListner;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.TitleBar;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by khan_muhammad on 11/30/2017.
 */

public class SelectHotelDatesFragment extends BaseFragment {

    @BindView(R.id.llLeftTab)
    LinearLayout llLeftTab;
    @BindView(R.id.llRightTab)
    LinearLayout llRightTab;
    @BindView(R.id.cvLeftTab)
    MaterialCalendarView cvLeftTab;
    @BindView(R.id.cvRightTab)
    MaterialCalendarView cvRightTab;
    @BindView(R.id.btnDone)
    Button btnDone;
    Unbinder unbinder;

    @BindView(R.id.tvLeftDate)
    AnyTextView tvLeftDate;
    @BindView(R.id.vLeftIndecator)
    View vLeftIndecator;
    @BindView(R.id.tvReturn)
    AnyTextView tvReturn;
    @BindView(R.id.tvRightDate)
    AnyTextView tvRightDate;
    @BindView(R.id.vRightIndecator)
    View vRightIndecator;
    @BindView(R.id.tvLeftTab)
    AnyTextView tvLeftTab;

    private String checkInDate = "";
    private String checkOutDate = "";
    public IDateSelectListner iDateSelectListner;

    boolean isCheckIn = false;

    Date leftDate;
    Date rightDate;

    public static SelectHotelDatesFragment newInstance() {
        return new SelectHotelDatesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.selectDates));
    }

    public void setLeftDate(Date leftDate){
        this.leftDate = leftDate;
    }

    public void setRightDate(Date rightDate){
        this.rightDate = rightDate;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_dates, container, false);

        unbinder = ButterKnife.bind(this, view);

        getMainActivity().setBackground(R.drawable.bg_hotel);

        if (getArguments() != null) {
            isCheckIn = getArguments().getBoolean(AppConstants.IsCheckInDate);
        }

        return view;
    }

    public void setiDateSelectListner(IDateSelectListner iDateSelectListner) {
        this.iDateSelectListner = iDateSelectListner;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvLeftTab.setText(R.string.check_in);
        tvReturn.setText(R.string.check_out);

        if (isCheckIn) {
            tvRightDate.setVisibility(View.INVISIBLE);
            vRightIndecator.setVisibility(View.INVISIBLE);
            cvRightTab.setVisibility(View.GONE);
            cvLeftTab.setVisibility(View.VISIBLE);
            llLeftTab.setAlpha(1);
            llRightTab.setAlpha(0.5f);
        } else {
            tvLeftDate.setVisibility(View.INVISIBLE);
            vLeftIndecator.setVisibility(View.INVISIBLE);
            tvRightDate.setVisibility(View.VISIBLE);
            vRightIndecator.setVisibility(View.VISIBLE);
            cvLeftTab.setVisibility(View.GONE);
            cvRightTab.setVisibility(View.VISIBLE);
            llRightTab.setAlpha(1);
            llLeftTab.setAlpha(0.5f);
        }

        cvLeftTab.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                if (date.getDate().before(CalendarDay.today().getDate())) {

                    cvLeftTab.clearSelection();
                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.please_enter_valid_date));

                } else {
                    leftDate = date.getDate();

                    if (rightDate != null) {
                        if (rightDate.before(leftDate)) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(leftDate);
                            calendar.add(Calendar.DATE, 1);
                            cvRightTab.setSelectedDate(calendar);

                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            rightDate = cvRightTab.getSelectedDate().getDate();

                            String DayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH).format(calendar.getTime());
                            String MonthOfWeek = new SimpleDateFormat("MMM", Locale.ENGLISH).format(calendar.getTime());
                            tvRightDate.setText(DayOfWeek + ", " + MonthOfWeek + " " + day);
                            checkOutDate = day + " " + MonthOfWeek + "," + date.getYear();
                        }
                    }

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(date.getYear(), date.getMonth(), date.getDay());
                    String DayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH).format(calendar.getTime());
                    String MonthOfWeek = new SimpleDateFormat("MMM", Locale.ENGLISH).format(calendar.getTime());
                    tvLeftDate.setText(DayOfWeek + ", " + MonthOfWeek + " " + date.getDay());
                    checkInDate = date.getDay() + " " + MonthOfWeek + "," + date.getYear();

                }


                /*Calendar calendar = Calendar.getInstance();
                calendar.set(date.getYear(), date.getMonth(), date.getDay());
                String DayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH).format(calendar.getTime());
                String MonthOfWeek = new SimpleDateFormat("MMM", Locale.ENGLISH).format(calendar.getTime());
                tvLeftDate.setText(DayOfWeek + ", " + MonthOfWeek + " " + date.getDay());
                checkInDate = DayOfWeek + ", " + date.getDay() + " " + MonthOfWeek;*/
            }
        });

        cvRightTab.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {


                if (date.getDate().before(CalendarDay.today().getDate())) {

                    cvRightTab.clearSelection();
                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.please_enter_valid_date));

                } else {

                    rightDate = date.getDate();

                    if (leftDate != null) {
                        if (rightDate.before(leftDate)) {

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(rightDate);

                            if(!rightDate.equals(CalendarDay.today().getDate())){
                                calendar.add(Calendar.DATE, -1);
                            }

                            cvLeftTab.setSelectedDate(calendar);
                            leftDate = cvRightTab.getSelectedDate().getDate();

                            int day = calendar.get(Calendar.DAY_OF_MONTH);

                            String DayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH).format(calendar.getTime());
                            String MonthOfWeek = new SimpleDateFormat("MMM", Locale.ENGLISH).format(calendar.getTime());
                            tvLeftDate.setText(DayOfWeek + ", " + MonthOfWeek + " " + day);
                            checkInDate = day + " " + MonthOfWeek + "," + date.getYear();
                        }

                    }

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(date.getYear(), date.getMonth(), date.getDay());
                    String DayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH).format(calendar.getTime());
                    String MonthOfWeek = new SimpleDateFormat("MMM", Locale.ENGLISH).format(calendar.getTime());
                    tvRightDate.setText(DayOfWeek + ", " + MonthOfWeek + " " + date.getDay());
                    checkOutDate = date.getDay() + " " + MonthOfWeek + "," + date.getYear();

                }

              /*  Calendar calendar = Calendar.getInstance();
                calendar.set(date.getYear(), date.getMonth(), date.getDay());
                String DayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH).format(calendar.getTime());
                String MonthOfWeek = new SimpleDateFormat("MMM", Locale.ENGLISH).format(calendar.getTime());
                tvRightDate.setText(DayOfWeek + ", " + MonthOfWeek + " " + date.getDay());
                checkOutDate = DayOfWeek + ", " + date.getDay() + " " + MonthOfWeek;*/

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @OnClick({R.id.llLeftTab, R.id.llRightTab, R.id.btnDone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llLeftTab:
                break;
            case R.id.llRightTab:
                break;
            case R.id.btnDone:

                if (iDateSelectListner != null) {
                    if (isCheckIn) {
                        if (checkInDate.length() > 0) {
                            iDateSelectListner.onDatesSelected(checkInDate, checkOutDate,leftDate,rightDate);
                            getDockActivity().popFragment();
                        } else {
                            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_checkin_date));
                        }
                    } else {
                        if (checkOutDate.length() > 0) {
                            iDateSelectListner.onDatesSelected(checkInDate, checkOutDate,leftDate,rightDate);
                            getDockActivity().popFragment();
                        } else {
                            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_checkout_date));
                        }
                    }
                }
                break;
        }
    }

}
