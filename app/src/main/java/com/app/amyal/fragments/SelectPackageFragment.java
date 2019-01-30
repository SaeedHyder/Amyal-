package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.app.amyal.R;
import com.app.amyal.entities.HotelRoomEnt;
import com.app.amyal.entities.PackageDetailsEnt;
import com.app.amyal.entities.PackageList;
import com.app.amyal.entities.PackageListEnt;
import com.app.amyal.entities.PackageSerachModel;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.Utils;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.interfaces.IHotelRoomBtnListner;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.retrofit.GsonFactory;
import com.app.amyal.ui.adapters.FilterableListAdapter;
import com.app.amyal.ui.binders.BinderSelectPackage;
import com.app.amyal.ui.views.AnyEditTextView;
import com.app.amyal.ui.views.TitleBar;
import com.google.common.base.Function;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by waq on 12/7/2017.
 */

public class SelectPackageFragment extends BaseFragment implements IHotelRoomBtnListner, ItemClickListener {

    @BindView(R.id.spSort)
    Spinner spSort;
    @BindView(R.id.llSpinner)
    LinearLayout llSpinner;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.edt_search)
    AnyEditTextView edtSearch;
    @BindView(R.id.ivClose)
    ImageView ivClose;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.lvPackages)
    ListView lvPackages;
    Unbinder unbinder;

    private List<HotelRoomEnt> hotelRoomEntList;
    private FilterableListAdapter<PackageListEnt> adapter;

    ArrayList<String> sort_list = new ArrayList<>();
    ArrayAdapter<String> sortAdapter;

    PackageList packageList;

    PackageListEnt packageListEnt;

    SelectPackageFragment context;

    public static SelectPackageFragment newInstance() {

        Bundle args = new Bundle();

        SelectPackageFragment fragment = new SelectPackageFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new FilterableListAdapter<>(getDockActivity(), new BinderSelectPackage(getDockActivity(), prefHelper, this));

        if (getArguments() != null) {
            packageList = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.PackageList), PackageList.class);
        }

        context = this;

    }

    private void filterList(String string) {
        adapter.getFilter().filter(string);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_package, container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.specialpackages));

        titleBar.showRight1Button(R.drawable.search, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSpinner.setVisibility(View.GONE);
                llSearch.setVisibility(View.VISIBLE);
            }
        });

        titleBar.showRightButton(R.drawable.filter, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentPackageFilter fragmentPackageFilter = FragmentPackageFilter.newInstance();
                fragmentPackageFilter.setListner(context);
                getDockActivity().replaceDockableFragment(fragmentPackageFilter, FragmentPackageFilter.class.getSimpleName());

            }
        });

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (packageList != null)
            bindData(packageList.getResults());

        setSortSpinnerData();

        edtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterList(s.toString());
            }

        });

        lvPackages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if (packageList != null) {
                    packageListEnt = packageList.getResults().get(position);
                    if (packageListEnt != null)
                        getPackageDeails();
                }

            }
        });

    }

    public void getPackageDeails() {

        serviceHelper.enqueueCall(webService.GetPackageDetails(
                packageListEnt.getRatePlanCode(),
                packageListEnt.getPackageCode(),
                prefHelper.getPackageSerachModel().getLanguageCode()
        ), WebServiceConstants.getPackageDeails);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.getPackageDeails:

                PackageDetailsEnt packageDetailsEnt = (PackageDetailsEnt) result;
                PackageSerachModel packageSerachModel = prefHelper.getPackageSerachModel();
                packageSerachModel.setRatePlanCode(packageListEnt.getRatePlanCode());
                prefHelper.putPackageSerachModel(packageSerachModel);

                if (packageDetailsEnt != null) {
                    FragmentPackageDetail fragmentPackageDetail = new FragmentPackageDetail();
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.packageDetailsEnt, new Gson().toJson(packageDetailsEnt));
                    orderBundle.putString(AppConstants.PackageListEnt, new Gson().toJson(packageListEnt));
                    fragmentPackageDetail.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(fragmentPackageDetail, FragmentPackageDetail.class.getSimpleName());
                }

                break;
        }
    }


    public void bindData(List<PackageListEnt> packageListEnts) {

        /*hotelRoomEntList = new ArrayList<>();
        hotelRoomEntList.add(new HotelRoomEnt("http://www.flyertravelex.com/data/frontImages/tours/tours_image/1440406519_tanjung_beach_langkawi_malaysia_peaceful_hd-wallpaper-1539723.jpg", "Hundread Island Package", "AED 1500.00", 3.4f, "Very Good"));
        hotelRoomEntList.add(new HotelRoomEnt("http://www.asiawebdirect.com/media/images/hotels/451/642933.jpg", "Family Package", "AED 1500.00", 3.5f, "Very Good"));*/

        //hotelRoomEntList.add(new HotelRoomEnt("drawable://" + R.drawable.american, "Westhill Cotage", 4f, "Las Vegas", "8.4 Very Good", "AED 1500.00"));

        /*if (flightEntList.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvMyreviews.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvMyreviews.setVisibility(View.VISIBLE);

        }*/
        //notificationCollection.addAll(result);

        adapter.clearList();
        adapter = new FilterableListAdapter<PackageListEnt>(getDockActivity(), new ArrayList<PackageListEnt>(), new BinderSelectPackage(getDockActivity(), prefHelper, this),
                new Function<PackageListEnt, String>() {
                    @Override
                    @Nullable
                    public String apply(
                            @Nullable PackageListEnt arg0) {
                        return arg0.getPackageName();
                    }
                });
        lvPackages.setAdapter(adapter);
        adapter.addAll(packageListEnts);
        adapter.notifyDataSetChanged();

    }

    private void setSortSpinnerData() {

        sort_list = new ArrayList<>();
        sort_list.add("Sort by");
        sort_list.add("A to Z");
        sort_list.add("Z to A");

        sortAdapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner_item_2, sort_list);
        sortAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_2);
        spSort.setAdapter(sortAdapter);
        spSort.setSelection(0);

        sortAdapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.ivClose)
    public void onViewClicked() {
        if (edtSearch.getText().toString().length() == 0) {
            llSearch.setVisibility(View.GONE);
            llSpinner.setVisibility(View.VISIBLE);
            Utils.HideKeyBoard(getDockActivity());
        } else {
            edtSearch.setText("");
        }
    }

    @Override
    public void onClick(int position) {

        if (packageList != null) {
            packageListEnt = packageList.getResults().get(position);
            if (packageListEnt != null)
                getPackageDeails();
        }
    }

    @Override
    public void itemClicked(Object object, int position) {

        if (object != null) {
            packageList = (PackageList) object;
            bindData(packageList.getResults());
        }

    }

    @Override
    public void itemClicked(Object object, boolean isfrom,int i) {

    }
}
