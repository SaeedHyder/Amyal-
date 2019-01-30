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

import com.app.amyal.Comparators.SortBasedOnCarName;
import com.app.amyal.R;
import com.app.amyal.entities.CarList;
import com.app.amyal.entities.CarListEnt;
import com.app.amyal.entities.CarSearchModel;
import com.app.amyal.entities.FlightDetailsEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.Utils;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.retrofit.GsonFactory;
import com.app.amyal.ui.adapters.FilterableListAdapter;
import com.app.amyal.ui.binders.BinderSelectCar;
import com.app.amyal.ui.views.AnyEditTextView;
import com.app.amyal.ui.views.TitleBar;
import com.google.common.base.Function;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by waq on 12/6/2017.
 */

public class SelectCarFragment extends BaseFragment implements ItemClickListener {


    @BindView(R.id.spSort)
    Spinner spSort;
    @BindView(R.id.llMap)
    LinearLayout llMap;
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
    @BindView(R.id.lvCars)
    ListView lvCars;

    private List<CarListEnt> hotelEntList;
    private FilterableListAdapter<CarList> adapter;

    ArrayList<String> sort_list = new ArrayList<>();
    ArrayAdapter<String> sortAdapter;

    CarListEnt carListEnt;

    CarList carListModel;

    SelectCarFragment context;


    public static SelectCarFragment newInstance() {
        return new SelectCarFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new FilterableListAdapter<>(getDockActivity(), new BinderSelectCar(getDockActivity(), prefHelper));

        if (getArguments() != null && carListEnt == null) {
            carListEnt = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.CarListEnt), CarListEnt.class);
        }

        context = this;

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.cars));
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
                FragmentCarFilter fragmentCarFilter = new FragmentCarFilter();
                fragmentCarFilter.setListner(context);
                getDockActivity().replaceDockableFragment(fragmentCarFilter, FragmentCarFilter.class.getSimpleName());
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_car, container, false);

        getMainActivity().setBackground(R.drawable.bg_car);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (carListEnt != null)
            bindData(carListEnt);

        //Utils.justifyListViewHeightBasedOnChildren(lvCars);

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

        lvCars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (carListEnt != null && carListEnt.getCarList() != null) {

                    carListModel = carListEnt.getCarList().get(position);
                    CarSearchModel carSearchModel = prefHelper.getCarSearchModel();
                    carSearchModel.setSequenceNumber(carListEnt.getSequenceNumber());
                    carSearchModel.setCarCode(carListModel.getCarCode());
                    prefHelper.putCarSearchModel(carSearchModel);
                    serviceHelper.enqueueCall(webService.GetCarDetails(carListModel.getRatePlanCode(),carListModel.getPickUpLocationCode(),carListModel.getDropOffLocationCode()), WebServiceConstants.getCarDetails);

                }

                //getDockActivity().replaceDockableFragment(new CarDetailFragment(), CarDetailFragment.class.getSimpleName());

            }
        });

        spSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 1) {

                    if (carListEnt != null) {

                        Collections.sort(carListEnt.getCarList(), new SortBasedOnCarName());
                        bindData(carListEnt);
                    }

                } else if (position == 2) {

                    Collections.sort(carListEnt.getCarList(), new SortBasedOnCarName());
                    Collections.reverse(carListEnt.getCarList());
                    bindData(carListEnt);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.getCarDetails:

                FlightDetailsEnt carDetailsEnt = (FlightDetailsEnt) result;

                if (carDetailsEnt != null) {
                    carListModel.setCarDetailsEnt(carDetailsEnt);
                    CarDetailFragment carDetailFragment = new CarDetailFragment();
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.CarListModel, new Gson().toJson(carListModel));
                    carDetailFragment.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(carDetailFragment, CarDetailFragment.class.getSimpleName());
                }

                break;
        }
    }

    private void filterList(String string) {
        adapter.getFilter().filter(string);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void bindData(CarListEnt carListEnt) {

        /*hotelEntList = new ArrayList<>();
        hotelEntList.add(new HotelEnt("https://static.pexels.com/photos/170811/pexels-photo-170811.jpeg", "Honda HR-V", 4f, "", "8.4 Very Good", "AED 1500.00"));
        hotelEntList.add(new HotelEnt("https://static.pexels.com/photos/2394/lights-clouds-dark-car.jpg", "Nissan Days", 4f, "", "8.4 Very Good", "AED 1500.00"));
        hotelEntList.add(new HotelEnt("https://static.pexels.com/photos/170811/pexels-photo-170811.jpeg", "2017 Ford", 4f, "", "8.4 Very Good", "AED 1500.00"));
        //hotelEntList.add(new HotelEnt("https://static.pexels.com/photos/2394/lights-clouds-dark-car.jpg", "Nissan", 4f, "", "8.4 Very Good", "AED 1500.00"));


        *//*if (flightEntList.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvMyreviews.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvMyreviews.setVisibility(View.VISIBLE);

        }*//*
        //notificationCollection.addAll(result);*/

        adapter.clearList();
        adapter = new FilterableListAdapter<CarList>(getDockActivity(), new ArrayList<CarList>(), new BinderSelectCar(getDockActivity(), prefHelper),
                new Function<CarList, String>() {
                    @Override
                    @Nullable
                    public String apply(
                            @Nullable CarList arg0) {
                        return arg0.getCarName();
                    }
                });
        lvCars.setAdapter(adapter);
        adapter.addAll(carListEnt.getCarList());
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

    @OnClick({R.id.llMap, R.id.ivClose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llMap:
                Utils.HideKeyBoard(getDockActivity());
                getDockActivity().replaceDockableFragment(new SelectCarMapFragment(), SelectCarMapFragment.class.getSimpleName());
                break;
            case R.id.ivClose:
                if (edtSearch.getText().toString().length() == 0) {
                    llSearch.setVisibility(View.GONE);
                    llSpinner.setVisibility(View.VISIBLE);
                    Utils.HideKeyBoard(getDockActivity());
                } else {
                    edtSearch.setText("");
                }
                break;
        }
    }

    @Override
    public void itemClicked(Object object, int position) {
        carListEnt = (CarListEnt)object;
    }

    @Override
    public void itemClicked(Object object, boolean isfrom,int i) {

    }
}
