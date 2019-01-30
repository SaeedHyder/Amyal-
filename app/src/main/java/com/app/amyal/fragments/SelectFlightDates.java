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

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class SelectFlightDates extends BaseFragment {

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

    private boolean isReturnTrip = false;
    private String fromDate = "";
    private String toDate = "";
    public IDateSelectListner iDateSelectListner;

    Date leftDate;
    Date rightDate;

    public static SelectFlightDates newInstance() {
        return new SelectFlightDates();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_dates, container, false);

        unbinder = ButterKnife.bind(this, view);

        getMainActivity().setBackground(R.drawable.bg_flight);

        if (getArguments() != null) {
            isReturnTrip = getArguments().getBoolean(AppConstants.IsReturnTrip);
        }

        if (isReturnTrip) {
            tvReturn.setText(R.string.returntext);
            tvRightDate.setVisibility(View.VISIBLE);
            vRightIndecator.setVisibility(View.VISIBLE);

        } else {
            tvReturn.setText(R.string.no_return);
            tvRightDate.setVisibility(View.INVISIBLE);
            vRightIndecator.setVisibility(View.INVISIBLE);
        }

        cvRightTab.setVisibility(View.GONE);
        cvLeftTab.setVisibility(View.VISIBLE);

        return view;
    }

    public void setiDateSelectListner(IDateSelectListner iDateSelectListner) {
        this.iDateSelectListner = iDateSelectListner;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //cvLeftTab.setSelectedDate(CalendarDay.today());
        //cvLeftTab.state().edit().setMinimumDate(CalendarDay.today()).commit();
        //cvRightTab.state().edit().setMinimumDate(CalendarDay.today()).commit();

       /* todayDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(todayDate);*/

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
                            toDate = day + " " + MonthOfWeek + "," + date.getYear();
                        }
                    }

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(date.getYear(), date.getMonth(), date.getDay());
                    String DayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH).format(calendar.getTime());
                    String MonthOfWeek = new SimpleDateFormat("MMM", Locale.ENGLISH).format(calendar.getTime());
                    tvLeftDate.setText(DayOfWeek + ", " + MonthOfWeek + " " + date.getDay());
                    fromDate = date.getDay() + " " + MonthOfWeek + "," + date.getYear();

                    cvLeftTab.invalidateDecorators();
                    cvLeftTab.addDecorators();

                    DayViewDecorator dayViewDecorator = new DayViewDecorator() {
                        @Override
                        public boolean shouldDecorate(CalendarDay day) {

                            return true;
                        }

                        @Override
                        public void decorate(DayViewFacade view) {

                        }
                    };

                }

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
                            fromDate = day + " " + MonthOfWeek + "," + date.getYear();
                        }

                    }

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(date.getYear(), date.getMonth(), date.getDay());
                    String DayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH).format(calendar.getTime());
                    String MonthOfWeek = new SimpleDateFormat("MMM", Locale.ENGLISH).format(calendar.getTime());
                    tvRightDate.setText(DayOfWeek + ", " + MonthOfWeek + " " + date.getDay());
                    toDate = date.getDay() + " " + MonthOfWeek + "," + date.getYear();

                }

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

                if (isReturnTrip) {
                    tvReturn.setText(R.string.returntext);
                    tvRightDate.setVisibility(View.VISIBLE);
                    vRightIndecator.setVisibility(View.VISIBLE);
                    llLeftTab.setAlpha(1);
                    llRightTab.setAlpha(0.5f);
                    cvRightTab.setVisibility(View.GONE);
                    cvLeftTab.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.llRightTab:

                if (isReturnTrip) {
                    tvReturn.setText(R.string.returntext);
                    tvRightDate.setVisibility(View.VISIBLE);
                    vRightIndecator.setVisibility(View.VISIBLE);
                    llRightTab.setAlpha(1);
                    llLeftTab.setAlpha(0.5f);
                    cvRightTab.setVisibility(View.VISIBLE);
                    cvLeftTab.setVisibility(View.GONE);
                }

                break;
            case R.id.btnDone:

                if (fromDate.length() > 0) {
                    if (isReturnTrip) {
                        if (toDate.length() > 0) {
                            iDateSelectListner.onDatesSelected(fromDate, toDate,leftDate,rightDate);
                            getDockActivity().popFragment();
                        } else {
                            if (llRightTab.getAlpha() == 1)
                                UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.please_select_return_date));
                            else {
                                tvReturn.setText(R.string.returntext);
                                tvRightDate.setVisibility(View.VISIBLE);
                                vRightIndecator.setVisibility(View.VISIBLE);
                                llRightTab.setAlpha(1);
                                llLeftTab.setAlpha(0.5f);
                                cvRightTab.setVisibility(View.VISIBLE);
                                cvLeftTab.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        iDateSelectListner.onDateSelected(fromDate,leftDate);
                        getDockActivity().popFragment();
                    }
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.please_select_dep_date));
                }

                break;
        }
    }

   /* @Override
    public boolean shouldDecorate(CalendarDay day) {

        *//*if(llRightTab.getAlpha() == 1){

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(cvRightTab.getSelectedDate().getDate());
            cal2.setTime(day.getDate());
            boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

            if(sameDay){
                return true;
            }else{
                return false;
            }

        }else{

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(cvLeftTab.getSelectedDate().getDate());
            cal2.setTime(day.getDate());
            boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

            if(sameDay){
                return true;
            }else{
                return false;
            }

        }*//*

        return false;
    }*/

    /*@Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(getResources().getColor(R.color.darkBrown)));
    }*/
}
