package com.app.amyal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.amyal.R;
import com.app.amyal.entities.HotelAreaEnt;
import com.app.amyal.entities.PackageList;
import com.app.amyal.entities.PackageSerachModel;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.DateHelper;
import com.app.amyal.helpers.DialogHelper;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.interfaces.IDateSelectListner;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.AnyTextView1;
import com.google.gson.Gson;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by waq on 11/24/2017.
 */

public class HomeSpecialPackagesFragment extends BaseFragment implements ItemClickListener, IDateSelectListner {

    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.ivClose)
    ImageView ivClose;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvSelectDates)
    AnyTextView tvSelectDates;
    @BindView(R.id.tvFromDate)
    AnyTextView tvFromDate;
    @BindView(R.id.tvToDate)
    AnyTextView tvToDate;
    @BindView(R.id.llSelectDates)
    LinearLayout llSelectDates;
    @BindView(R.id.tvTravellers)
    AnyTextView tvTravellers;
    @BindView(R.id.tvNoOfAdult)
    AnyTextView tvNoOfAdult;
    @BindView(R.id.tvNoOfChild)
    AnyTextView tvNoOfChild;
    @BindView(R.id.tvNoOfInfant)
    AnyTextView tvNoOfInfant;
    @BindView(R.id.llTravellers)
    LinearLayout llTravellers;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    Unbinder unbinder;

    int nofAdult = 1;
    int nofChild = 0;
    int nofInfant = 0;

    View view = null;

    HotelAreaEnt hotelAreaEntfrom;
    HotelAreaEnt hotelAreaEnt;

    Date leftDate;
    Date rightDate;

    @BindView(R.id.llSource)
    LinearLayout llSource;
    @BindView(R.id.tvSource)
    AnyTextView1 tvSource;
    @BindView(R.id.tvSearch)
    AnyTextView1 tvSearch;

    public HomeSpecialPackagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if (view == null)
            view = inflater.inflate(R.layout.fragmnet_special_packages, container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @OnClick({R.id.tvSource, R.id.tvSearch, R.id.llSelectDates, R.id.llTravellers, R.id.btnSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tvSource:
                HotelAreaFragment hotelAreaFragment = HotelAreaFragment.newInstance();
                hotelAreaFragment.setItemClickListener(this, true, true, true);
                getDockActivity().replaceDockableFragment(hotelAreaFragment, HotelAreaFragment.class.getSimpleName());
                break;

            case R.id.tvSearch:
                hotelAreaFragment = HotelAreaFragment.newInstance();
                hotelAreaFragment.setItemClickListener(this, false, false, false);
                getDockActivity().replaceDockableFragment(hotelAreaFragment, HotelAreaFragment.class.getSimpleName());
                break;

            case R.id.llSelectDates:
                SelectFlightDates selectFlightDates = SelectFlightDates.newInstance();
                selectFlightDates.setiDateSelectListner(this);
                Bundle orderBundle = new Bundle();
                orderBundle.putBoolean(AppConstants.IsReturnTrip, true);
                selectFlightDates.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(selectFlightDates, SelectFlightDates.class.getSimpleName());
                break;

            case R.id.llTravellers:
                final DialogHelper travellerDialog = new DialogHelper(getDockActivity());
                travellerDialog.initTravellerDialog(R.layout.dialog_no_of_travellers, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        nofAdult = Integer.parseInt(travellerDialog.getTravellerInfo(R.id.tvNoOfAdult));
                        nofChild = Integer.parseInt(travellerDialog.getTravellerInfo(R.id.tvNoOfChild));
                        nofInfant = Integer.parseInt(travellerDialog.getTravellerInfo(R.id.tvNoOfInfant));

                        tvNoOfAdult.setText(nofAdult + " Adult, ");
                        tvNoOfChild.setText(nofChild + " Child, ");
                        tvNoOfInfant.setText(nofInfant + " Infant");

                        travellerDialog.hideDialog();
                    }
                });
                travellerDialog.setCancelable(false);
                travellerDialog.showDialog();
                break;

            case R.id.btnSearch:

                if (tvSource.getText().toString() != null && tvSource.getText().toString().length() > 0) {
                    if (tvSearch.getText().toString() != null && tvSearch.getText().toString().length() > 0) {
                        if (tvFromDate.getText().toString() != null && tvFromDate.getText().toString().length() > 0) {
                            if (tvToDate.getText().toString() != null && tvToDate.getText().toString().length() > 0) {

                                //getDockActivity().replaceDockableFragment(SelectPackageFragment.newInstance(), SelectPackageFragment.class.getSimpleName());

                                String fromDate = "";
                                String toDate = "";
                                if (leftDate != null) {
                                    fromDate = DateHelper.dateFormat(leftDate.toString(), DateHelper.DATE_FORMAT3, DateHelper.DATE_TIME_FORMAT2);
                                }
                                if (rightDate != null) {
                                    toDate = DateHelper.dateFormat(rightDate.toString(), DateHelper.DATE_FORMAT3, DateHelper.DATE_TIME_FORMAT2);
                                }

                                String sourceLocationCode = hotelAreaEntfrom.getZoneCode() + "";
                                String fromLocationCode = hotelAreaEnt.getZoneCode() + "";

                                String LangCode = AppConstants.LangCode;
                                String CurCode = AppConstants.CurCode;

                                String countryCode = AppConstants.CountryCode;

                                if (prefHelper.isLogin()) {
                                    LangCode = "es"; //prefHelper.getUser().getUser().getLanguageCode();
                                    CurCode = prefHelper.getUser().getUser().getCurrencyCode();
                                    countryCode = prefHelper.getUser().getUser().getCountryCode();
                                }

                                getPackageList(new PackageSerachModel(sourceLocationCode, fromLocationCode, fromDate, toDate, nofAdult, nofChild, nofInfant, CurCode, LangCode, countryCode
                                        , "", "", "", "", ""));


                            } else {
                                UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_return_date));
                            }
                        } else {
                            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_dep_date));
                        }
                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_destination));
                    }
                }else{
                    UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_souce_location));
                }

                break;
        }
    }


    public void getPackageList(PackageSerachModel packageSerachModel) {

        prefHelper.putPackageSerachModel(packageSerachModel);

        serviceHelper.enqueueCall(webService.GetPackagesList(
                packageSerachModel.getSourceLocationCode(),
                packageSerachModel.getDestinationLocationCode(),
                packageSerachModel.getCheckInTime(),
                packageSerachModel.getCheckOutTime(),
                packageSerachModel.getNoOfAdults(),
                packageSerachModel.getNoOfChildrens(),
                packageSerachModel.getNoOfInfant(),
                packageSerachModel.getCurrencyCode(),
                packageSerachModel.getLanguageCode(),
                packageSerachModel.getCountryOfResidence(),
                packageSerachModel.getPackageCode(),
                packageSerachModel.getPriceRange(),
                packageSerachModel.getDuration()
        ), WebServiceConstants.getPackageList);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.getPackageList:

                PackageList packageList = (PackageList) result;
                PackageSerachModel packageSerachModel = prefHelper.getPackageSerachModel();
                packageSerachModel.setSequenceNumber(packageList.getSequenceNumber());
                prefHelper.putPackageSerachModel(packageSerachModel);

                if (packageList != null) {
                    SelectPackageFragment selectPackageFragment = new SelectPackageFragment();
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.PackageList, new Gson().toJson(packageList));
                    selectPackageFragment.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(selectPackageFragment, SelectFlightFragment.class.getSimpleName());
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
            hotelAreaEntfrom = (HotelAreaEnt) object;
            if (hotelAreaEntfrom != null && hotelAreaEntfrom.getName().length() > 0)
                tvSource.setText(hotelAreaEntfrom.getName());
            else
                tvSource.setText("");
        } else {
            hotelAreaEnt = (HotelAreaEnt) object;
            if (hotelAreaEnt != null && hotelAreaEnt.getName().length() > 0)
                tvSearch.setText(hotelAreaEnt.getName());
            else
                tvSearch.setText("");
        }
    }

    @Override
    public void onDatesSelected(String fromDate, String toDate) {

        if (fromDate != null)
            tvFromDate.setText(fromDate);

        if (toDate != null)
            tvToDate.setText("- " + toDate);

    }

    @Override
    public void onDateSelected(String fromDate, Date date) {

        if (fromDate != null) {
            tvFromDate.setText(fromDate);
            leftDate = date;
        }
    }

    @Override
    public void onDatesSelected(String fromDate, String toDate, Date todate, Date fromdate) {

        if (fromDate != null) {
            tvFromDate.setText(fromDate);
            leftDate = todate;
        }

        if (toDate != null) {
            tvToDate.setText("- " + toDate);
            rightDate = fromdate;
        }

    }
}