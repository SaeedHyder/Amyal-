package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.amyal.R;
import com.app.amyal.entities.PackageList;
import com.app.amyal.entities.PackageSerachModel;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.RangeSeekBar;
import com.app.amyal.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by khan_muhammad on 12/7/2017.
 */

public class FragmentPackageFilter extends BaseFragment {

    @BindView(R.id.tvStartPrice)
    AnyTextView tvStartPrice;
    @BindView(R.id.tvEndPrice)
    AnyTextView tvEndPrice;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    Unbinder unbinder;
    @BindView(R.id.rvPrice)
    RangeSeekBar rvPrice;
    @BindView(R.id.llPriceRange)
    LinearLayout llPriceRange;

    String minPrice = ""; //0
    String maxPrice = ""; //10000

    ItemClickListener itemClickListener;

    public void setListner(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public static FragmentPackageFilter newInstance() {
        return new FragmentPackageFilter();
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
                Reset();
            }
        });
        titleBar.setSubHeading(getString(R.string.filter));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_package_filter, container, false);

        getMainActivity().setBackground(R.drawable.sp_dark);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvStartPrice.setText(prefHelper.getPackageSerachModel().getCurrencyCode() + " " + String.valueOf(0) + ".00");
        tvEndPrice.setText(prefHelper.getPackageSerachModel().getCurrencyCode() + " " + String.valueOf(10000) + ".00");

        rvPrice.setNotifyWhileDragging(true);
        rvPrice.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Number minValue, Number maxValue) {
                tvStartPrice.setText(prefHelper.getPackageSerachModel().getCurrencyCode() + " " + String.valueOf(minValue) + ".00");
                tvEndPrice.setText(prefHelper.getPackageSerachModel().getCurrencyCode() + " " + String.valueOf(maxValue) + ".00");
                minPrice = String.valueOf(minValue);
                maxPrice = String.valueOf(maxValue);
            }
        });

    }

    public void Reset() {

        rvPrice.setSelectedMinValue(0);
        rvPrice.setSelectedMaxValue(10000);
        tvStartPrice.setText(prefHelper.getPackageSerachModel().getCurrencyCode() + " " + String.valueOf(0) + ".00");
        tvEndPrice.setText(prefHelper.getPackageSerachModel().getCurrencyCode() + " " + String.valueOf(10000) + ".00");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {

        getPackageList(new PackageSerachModel(prefHelper.getPackageSerachModel().getSourceLocationCode(), prefHelper.getPackageSerachModel().getDestinationLocationCode(),
                prefHelper.getPackageSerachModel().getCheckInTime(), prefHelper.getPackageSerachModel().getCheckOutTime(), prefHelper.getPackageSerachModel().getNoOfAdults(),
                prefHelper.getPackageSerachModel().getNoOfChildrens(), prefHelper.getPackageSerachModel().getNoOfInfant(), prefHelper.getPackageSerachModel().getCurrencyCode(),
                prefHelper.getPackageSerachModel().getLanguageCode(), prefHelper.getPackageSerachModel().getCountryOfResidence()
                , "", "", "", minPrice+"-"+maxPrice, ""));

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

                if (packageList != null && itemClickListener != null) {
                    itemClickListener.itemClicked(packageList, 0);
                    getDockActivity().popFragment();
                }

                break;
        }
    }

}
