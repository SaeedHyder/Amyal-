package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.app.amyal.R;
import com.app.amyal.entities.CarListEnt;
import com.app.amyal.entities.CarSearchModel;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.RangeSeekBarPrice;
import com.app.amyal.ui.views.TitleBar;
import com.github.florent37.androidslidr.SlidrTotalPrice;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by waq on 12/7/2017.
 */

public class FragmentCarFilter extends BaseFragment {


    ArrayList<String> areasList = new ArrayList<>();
    ArrayAdapter<String> areasAdapter;
    @BindView(R.id.rvTotalPrice)
    RangeSeekBarPrice rvTotalPrice;
    @BindView(R.id.sbTotalPrice)
    SlidrTotalPrice sbTotalPrice;
    @BindView(R.id.tvStartPrice)
    AnyTextView tvStartPrice;
    @BindView(R.id.tvEndPrice)
    AnyTextView tvEndPrice;
    @BindView(R.id.spDistance)
    Spinner spDistance;
    @BindView(R.id.llDistanceSpinner)
    LinearLayout llDistanceSpinner;
    @BindView(R.id.llSpecifications)
    LinearLayout llSpecifications;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    Unbinder unbinder;
    @BindView(R.id.cbAuto)
    CheckBox cbAuto;
    @BindView(R.id.cbManual)
    CheckBox cbManual;
    @BindView(R.id.cbAirCond)
    CheckBox cbAirCond;
    @BindView(R.id.cbPetrol)
    CheckBox cbPetrol;
    @BindView(R.id.cbDiesel)
    CheckBox cbDiesel;
    @BindView(R.id.cbHybrid)
    CheckBox cbHybrid;
    @BindView(R.id.cbLPG)
    CheckBox cbLPG;
    @BindView(R.id.cbMulti)
    CheckBox cbMulti;

    String specification = "";

    String maxPrice = "10000";

    ItemClickListener itemClickListener;

    public void setListner(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public static FragmentCarFilter newInstance() {
        return new FragmentCarFilter();
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
        titleBar.showRightButton(R.drawable.reload, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maxPrice = "10000";
                specification = "";
                cbAuto.setChecked(true);
                cbManual.setChecked(false);
                cbAirCond.setChecked(true);
                cbPetrol.setChecked(true);
                cbDiesel.setChecked(false);
                cbLPG.setChecked(false);
                cbHybrid.setChecked(false);
                cbMulti.setChecked(false);
            }
        });
        titleBar.setSubHeading(getString(R.string.filter));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_filter, container, false);

        getMainActivity().setBackground(R.drawable.bg_car);

        unbinder = ButterKnife.bind(this, view);

        rvTotalPrice.setCurrencyCode(prefHelper.getCarSearchModel().getCurrencyCode() + " ");

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setAreasSpinnerData();

        tvStartPrice.setText(prefHelper.getCarSearchModel().getCurrencyCode() + " "+String.valueOf(0) + ".00");
        tvEndPrice.setText(prefHelper.getCarSearchModel().getCurrencyCode() + " "+String.valueOf(10000) + ".00");

        rvTotalPrice.setOnRangeSeekBarChangeListener(new RangeSeekBarPrice.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBarPrice bar, Number minValue, Number maxValue) {

                //tvStartPrice.setText(prefHelper.getCarSearchModel().getCurrencyCode() + " "+String.valueOf(0) + ".00");

                //tvEndPrice.setText(prefHelper.getCarSearchModel().getCurrencyCode() + " "+String.valueOf(maxValue) + ".00");

                maxPrice = String.valueOf(maxValue);

            }
        });

    }

    /*private void setAreasSpinnerData() {

        areasList = new ArrayList<>();
        areasList.add("1 Mile");
        areasList.add("2 Miles");
        areasList.add("3 Miles");

        areasAdapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner_item, areasList);
        areasAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spDistance.setAdapter(areasAdapter);
        spDistance.setSelection(0);

        areasAdapter.notifyDataSetChanged();
    }*/


    @OnClick({R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btnSubmit:

                specification = "";

                if(cbAirCond.isChecked()){
                    specification = "Yes,";
                }else{
                    specification = "No,";
                }

                if(cbAuto.isChecked()){
                    specification = specification + "Auto,";
                }

                if(cbManual.isChecked()){
                    specification = specification + "Manual,";
                }

                if(cbPetrol.isChecked()){
                    specification = specification + "Petrol,";
                }

                if(cbDiesel.isChecked()){
                    specification = specification + "Diesel,";
                }

                if(cbHybrid.isChecked()){
                    specification = specification + "Hybrid,";
                }

                if(cbLPG.isChecked()){
                    specification = specification + "LPG/Compressed Gas,";
                }

                if(cbMulti.isChecked()){
                    specification = specification + "Multi Fuel/Power,";
                }

                specification = removeLastChar(specification);

                getCarList();

                break;
        }
    }

    private String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    public void getCarList() {

        CarSearchModel carSearchModel = prefHelper.getCarSearchModel();

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
                maxPrice,
                specification
        ), WebServiceConstants.getCarList);

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.getCarList:

                CarListEnt carListEnt = (CarListEnt) result;

                if (carListEnt != null && itemClickListener!= null) {
                    itemClickListener.itemClicked(carListEnt, 0);
                    getDockActivity().popFragment();
                }

                break;
        }
    }


}
