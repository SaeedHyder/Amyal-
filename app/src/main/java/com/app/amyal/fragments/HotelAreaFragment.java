package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.app.amyal.R;
import com.app.amyal.entities.HotelAreaEnt;
import com.app.amyal.entities.ResponseWrapper;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.InternetHelper;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.ui.adapters.ArrayListAdapter;
import com.app.amyal.ui.binders.BinderHotelArea;
import com.app.amyal.ui.views.AnyEditTextView;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by waq on 12/29/2017.
 */

public class HotelAreaFragment extends BaseFragment {

    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.edt_search)
    AnyEditTextView edtSearch;
    @BindView(R.id.ivClose)
    ImageView ivClose;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.lvSearch)
    ListView lvSearch;
    Unbinder unbinder;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    Call<ResponseWrapper<ArrayList<HotelAreaEnt>>> call;
    private ArrayList<HotelAreaEnt> hotelAreaEnts;
    private ArrayListAdapter<HotelAreaEnt> adapter;
    private ItemClickListener itemClickListener;
    private boolean isfrom = false;
    private boolean isFlight = false;
    private boolean isSearchAllZones = false;

    public static HotelAreaFragment newInstance() {
        return new HotelAreaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<>(getDockActivity(), new BinderHotelArea(getDockActivity(), prefHelper));
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.search));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_city_airport, container, false);
        if (isFlight) {
            getMainActivity().setBackground(R.drawable.bg_flight);
        }else
        getMainActivity().setBackground(R.drawable.bg_hotel);

        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtSearch.setFocusable(true);

        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                itemClickListener.itemClicked(hotelAreaEnts.get(position), isfrom, -1);
                getDockActivity().popFragment();

            }
        });


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString() != null && s.length() > 2) {
                    if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                        if (isSearchAllZones) {
                            searchAllZones(s.toString());
                        } else {
                            searchZones(s.toString());
                        }
                    }
                }

            }
        });

        init();

    }

    private void searchZones(String search) {

        if (call != null && !call.isCanceled())
            call.cancel();

        String isAirport = "0";
        if (isFlight) {
            isAirport = "1";
        }

        loadingStarted();

        try {
            call = webService.searchZones(search, isAirport);
            call.enqueue(new Callback<ResponseWrapper<ArrayList<HotelAreaEnt>>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<ArrayList<HotelAreaEnt>>> call, Response<ResponseWrapper<ArrayList<HotelAreaEnt>>> response) {
                    loadingFinished();
                    if (response.body().getResponse().equals(WebServiceConstants.CODE_SUCCESS)) {

                        hotelAreaEnts = (ArrayList<HotelAreaEnt>) response.body().getResult();
                        bindData(hotelAreaEnts);

                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseWrapper<ArrayList<HotelAreaEnt>>> call, Throwable t) {
                    loadingFinished();
                    if (t.getMessage().equalsIgnoreCase("Canceled") && t.getMessage().equalsIgnoreCase("Socket Closed"))
                        UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void searchAllZones(String search) {

        if (call != null && !call.isCanceled())
            call.cancel();

        loadingStarted();

        try {
            call = webService.SearchAllZones(search);
            call.enqueue(new Callback<ResponseWrapper<ArrayList<HotelAreaEnt>>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<ArrayList<HotelAreaEnt>>> call, Response<ResponseWrapper<ArrayList<HotelAreaEnt>>> response) {
                    loadingFinished();
                    if (response.body().getResponse().equals(WebServiceConstants.CODE_SUCCESS)) {

                        hotelAreaEnts = (ArrayList<HotelAreaEnt>) response.body().getResult();
                        bindData(hotelAreaEnts);

                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseWrapper<ArrayList<HotelAreaEnt>>> call, Throwable t) {
                    loadingFinished();
                    if (t.getMessage().equalsIgnoreCase("Canceled") && t.getMessage().equalsIgnoreCase("Socket Closed"))
                        UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void bindData(ArrayList<HotelAreaEnt> result) {

        if (result != null && result.size() > 0) {
            txtNoData.setVisibility(View.GONE);
            lvSearch.setVisibility(View.VISIBLE);
        } else {
            txtNoData.setVisibility(View.VISIBLE);
            lvSearch.setVisibility(View.GONE);
        }

        if (lvSearch != null && adapter != null) {
            adapter.clearList();
            lvSearch.setAdapter(adapter);
            if (result != null && result.size() > 0) {
                adapter.addAll(result);
                adapter.notifyDataSetChanged();
            }
        }

    }

    @OnClick(R.id.ivClose)
    public void onViewClicked() {
        edtSearch.setText("");
        lvSearch.setVisibility(View.GONE);
    }

    public void setItemClickListener(ItemClickListener itemClickListener, boolean isfrom, boolean isFlight, boolean isSearchAllZones) {
        this.itemClickListener = itemClickListener;
        this.isfrom = isfrom;
        this.isFlight = isFlight;
        this.isSearchAllZones = isSearchAllZones;
    }

    private void init() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtSearch.getText().length() > 0) {
                    ivClose.setVisibility(View.VISIBLE);
                } else
                    ivClose.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
