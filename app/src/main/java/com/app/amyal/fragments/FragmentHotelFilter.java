package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.app.amyal.R;
import com.app.amyal.entities.HotelAreaEnt;
import com.app.amyal.entities.HotelListEnt;
import com.app.amyal.entities.HotelSearchModel;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.DateHelper;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.ui.views.AnyEditTextView;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.CustomRatingBar;
import com.app.amyal.ui.views.RangeSeekBar;
import com.app.amyal.ui.views.TitleBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class FragmentHotelFilter extends BaseFragment {

    @BindView(R.id.tvHotelName)
    AnyEditTextView tvHotelName;
    @BindView(R.id.ivClose)
    ImageView ivClose;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;

    /*@BindView(R.id.rvPrice)
    SimpleRangeView rvPrice;*/

    @BindView(R.id.tvStartPrice)
    AnyTextView tvStartPrice;
    @BindView(R.id.tvEndPrice)
    AnyTextView tvEndPrice;
    @BindView(R.id.llPriceRange)
    LinearLayout llPriceRange;
    @BindView(R.id.CrRating)
    CustomRatingBar CrRating;
    @BindView(R.id.tvStars)
    AnyTextView tvStars;
    @BindView(R.id.spAreas)
    Spinner spAreas;
    @BindView(R.id.llAreasSpinner)
    LinearLayout llAreasSpinner;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    Unbinder unbinder;
    @BindView(R.id.rvPrice)
    RangeSeekBar rvPrice;

    ArrayList<String> areasList = new ArrayList<>();
    ArrayList<String> countryList = new ArrayList<>();
    ArrayAdapter<String> areasAdapter;
    ArrayAdapter<String> countryAdapter;
    @BindView(R.id.spCountry)
    Spinner spCountry;
    @BindView(R.id.llCountrySpinner)
    LinearLayout llCountrySpinner;

    ArrayList<HotelAreaEnt> countryListEnt;
    ArrayList<HotelAreaEnt> areaListEnt;

    ItemClickListener itemClickListener;

    String hotelName = "";
    String minPrice = "";
    String maxPrice = "";
    String zoneCode = "";
    String ratting = "";
    private RequestBody body;

    public static FragmentHotelFilter newInstance() {
        return new FragmentHotelFilter();
    }

    public void setListner(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
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
        View view = inflater.inflate(R.layout.fragment_hotel_filter, container, false);

        getMainActivity().setBackground(R.drawable.bg_hotel);

        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getSpinnerData();

        /*rvPrice.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView rangeView, int start) {
                tvStartPrice.setText(String.valueOf(start) + ".00");
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView rangeView, int end) {
                tvEndPrice.setText(String.valueOf(end) + ".00");
            }
        });*/

        rvPrice.setNotifyWhileDragging(true);
        rvPrice.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Number minValue, Number maxValue) {
                tvStartPrice.setText("USD " + String.valueOf(minValue) + ".00");
                tvEndPrice.setText("USD " + String.valueOf(maxValue) + ".00");
                minPrice = String.valueOf(minValue);
                maxPrice = String.valueOf(maxValue);
            }
        });

        CrRating.setOnScoreChanged(new CustomRatingBar.IRatingBarCallbacks() {
            @Override
            public void scoreChanged(float score) {
                tvStars.setText(score + " Stars");
                ratting = (int) score + "";
            }
        });

        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (countryListEnt != null) {
                    if (position != 0)
                        serviceHelper.enqueueCall(webService.GetCountryZones(countryListEnt.get(position - 1).getZoneCode()), WebServiceConstants.getCountryZones);
                    else {
                        setAreaSpinnerData(areaListEnt);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spAreas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (areaListEnt != null) {
                    if (position != 0)
                        zoneCode = areaListEnt.get(position - 1).getZoneCode() + "";
                    else
                        zoneCode = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        minPrice = String.valueOf(rvPrice.getSelectedMinValue());
        maxPrice = String.valueOf(rvPrice.getSelectedMaxValue());

    }

    public void Reset() {
        tvHotelName.setText("");
        CrRating.setScore(3);
        spCountry.setSelection(0);
        rvPrice.setSelectedMinValue(0);
        rvPrice.setSelectedMaxValue(10000);
        tvStartPrice.setText("USD " + String.valueOf(0) + ".00");
        tvEndPrice.setText("USD " + String.valueOf(10000) + ".00");
        minPrice = String.valueOf(0);
        maxPrice = String.valueOf(10000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void getSpinnerData() {
        serviceHelper.enqueueCall(webService.GetAllCountries(), WebServiceConstants.getAllCountries);
    }


//    public void getHotelList(HotelSearchModel hotelSearchModel) {
//
//        String LangCode = "en";
//        String CurCode = "AED";
//
//        if (prefHelper.isLogin()) {
//
//            LangCode = prefHelper.getUser().getUser().getLanguageCode();
//            CurCode = prefHelper.getUser().getUser().getCurrencyCode();
//
//        }
//
//        prefHelper.putHotelSearchModel(hotelSearchModel);
//
//        JSONObject mainObj = new JSONObject();
//        JSONArray guestsArray = new JSONArray();
////        JSONObject guestObj = new JSONObject();
//        try {
//            mainObj.put("CheckInTime", DateHelper.getFormatedDate("yyyy-MM-dd", "dd MMM,yyyy", hotelSearchModel.getCheckInDate()));
//            mainObj.put("CheckOutTime",DateHelper.getFormatedDate("yyyy-MM-dd", "dd MMM,yyyy", hotelSearchModel.getCheckOutDate()));
//            mainObj.put("ZoneCode", hotelSearchModel.getZoneCode());
//            mainObj.put("HotelName", hotelSearchModel.getHotelName());
//            mainObj.put("PriceRange", hotelSearchModel.getPriceRange());
//            mainObj.put("Rating", hotelSearchModel.getRatting());
//            mainObj.put("HotelCode", hotelSearchModel.getHotelCode());
//            mainObj.put("TravellingFor", hotelSearchModel.getTravellingFor());
//            mainObj.put("LanguageCode", LangCode);
//            mainObj.put("CurrencyCode", CurCode);
//            mainObj.put("CountryOfResidence", "ES");
//
//            for (int i=0;i<=0+1;i++) {
//                JSONObject guestObj = new JSONObject();
//                guestObj.put("NoOfAdults", hotelSearchModel.getGuestWrapers()[i].getNofAdult());
//                guestObj.put("NoOfChildrens", hotelSearchModel.getGuestWrapers()[i].getNofChild());
//                guestObj.put("NoOfInfant", hotelSearchModel.getGuestWrapers()[i].getNofInfant());
//                guestsArray.put(guestObj);
//            }
////            guestsArray.put(guestObj);
//            mainObj.put("Guests", guestsArray);
//            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mainObj.toString());
//        } catch (Exception ex) {
//
//        }
//
//
//        serviceHelper.enqueueCall(webService.GetHotelList(
//                body
//        ), WebServiceConstants.getHotelList);
//
//    }


    public void getHotelList() {
        HotelSearchModel hotelSearchModel = HotelSearchModel.getInstance();

        String LangCode = AppConstants.LangCode;
        String CurCode = AppConstants.CurCode;

        if (prefHelper.isLogin()) {

            LangCode = prefHelper.getUser().getUser().getLanguageCode();
            CurCode = prefHelper.getUser().getUser().getCurrencyCode();

        }

        JSONObject mainObj = new JSONObject();
        JSONArray guestsArray = new JSONArray();
//        JSONObject guestObj = new JSONObject();
        try {

            mainObj.put("CheckInTime", DateHelper.getFormatedDate("yyyy-MM-dd", "dd MMM,yyyy", hotelSearchModel.getCheckInDate()));
            mainObj.put("CheckOutTime", DateHelper.getFormatedDate("yyyy-MM-dd", "dd MMM,yyyy", hotelSearchModel.getCheckOutDate()));
            mainObj.put("ZoneCode", hotelSearchModel.getZoneCode());
            mainObj.put("HotelName", hotelSearchModel.getHotelName());
            mainObj.put("PriceRange", hotelSearchModel.getPriceRange());
            mainObj.put("Rating", hotelSearchModel.getRatting());
            mainObj.put("HotelCode", hotelSearchModel.getHotelCode());
            mainObj.put("TravellingFor", hotelSearchModel.getTravellingFor());
            mainObj.put("LanguageCode", LangCode);
            mainObj.put("CurrencyCode", CurCode);
            mainObj.put("CountryOfResidence", "ES");
            mainObj.put("PageIndex", 0);
            mainObj.put("DeviceID", prefHelper.getUser().getAuthToken());

            for (int i = 0; i < 3; i++) {
                if (hotelSearchModel.getGuestWrapers()[i] != null) {
                    JSONObject guestObj = new JSONObject();
//                    hotelSearchModel.setTotAdult(hotelSearchModel.getTotAdult() + hotelSearchModel.getGuestWrapers()[i].getNofAdult());
//                    hotelSearchModel.setTotChild(hotelSearchModel.getTotChild() + hotelSearchModel.getGuestWrapers()[i].getNofChild());
//                    hotelSearchModel.setTotInfant(hotelSearchModel.getTotInfant() + hotelSearchModel.getGuestWrapers()[i].getNofInfant());
                    guestObj.put("NoOfAdults", hotelSearchModel.getGuestWrapers()[i].getNofAdult());
                    guestObj.put("NoOfChildrens", hotelSearchModel.getGuestWrapers()[i].getNofChild());
                    guestObj.put("NoOfInfant", hotelSearchModel.getGuestWrapers()[i].getNofInfant());
                    guestsArray.put(guestObj);
                }
                mainObj.put("Guests", guestsArray);
            }
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mainObj.toString());
        } catch (Exception ex) {
            Log.e("", ex.getMessage());
        }


        serviceHelper.enqueueCall(webService.GetHotelList(
                body
        ), WebServiceConstants.getHotelList);

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.getHotelList:

                HotelListEnt hotelListEnt = (HotelListEnt) result;

                if (hotelListEnt != null && itemClickListener != null) {
                    itemClickListener.itemClicked(hotelListEnt, 0);
                    getDockActivity().popFragment();
                }

                break;

            case WebServiceConstants.getAllCountries:

                countryListEnt = (ArrayList<HotelAreaEnt>) result;

                if (countryListEnt != null) {
                    setCountrySpinnerData(countryListEnt);
                }

                break;

            case WebServiceConstants.getCountryZones:

                areaListEnt = (ArrayList<HotelAreaEnt>) result;

                if (areaListEnt != null) {
                    setAreaSpinnerData(areaListEnt);
                }

                break;
        }
    }

    private void setCountrySpinnerData(ArrayList<HotelAreaEnt> hotelAreaEnts) {

        if (hotelAreaEnts != null) {
            countryList = new ArrayList<>();
            countryList.add("Select Country");
            for (int i = 0; i < hotelAreaEnts.size(); i++) {
                /*if(i == 0){
                    countryList.add("Select Country");
                }*/
                countryList.add(hotelAreaEnts.get(i).getName());
            }
        }

        countryAdapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner_item, countryList);
        countryAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spCountry.setAdapter(countryAdapter);
        spCountry.setSelection(0);
        countryAdapter.notifyDataSetChanged();

    }

    private void setAreaSpinnerData(ArrayList<HotelAreaEnt> hotelAreaEnts) {

        areasList = new ArrayList<>();
        if (hotelAreaEnts != null) {
            areasList.add("Select Area");
            for (int i = 0; i < hotelAreaEnts.size(); i++) {
                areasList.add(hotelAreaEnts.get(i).getName());
            }
        }

        areasAdapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner_item, areasList);
        areasAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spAreas.setAdapter(areasAdapter);
        spAreas.setSelection(0);

        areasAdapter.notifyDataSetChanged();

    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {

        if (tvHotelName.getText().toString() != null && tvHotelName.getText().toString().length() > 0)
            hotelName = tvHotelName.getText().toString();

        if (zoneCode != null && zoneCode.length() == 0) {
            zoneCode = HotelSearchModel.getInstance().getZoneCode();
        }

        HotelSearchModel.getInstance().addData((HotelSearchModel.getInstance().getCheckInDate()), HotelSearchModel.getInstance().getCheckOutDate(), HotelSearchModel.getInstance().getGuestWrapers(),

                zoneCode,
                hotelName,
                minPrice + "-" + maxPrice,
                "",
                ratting,
                HotelSearchModel.getInstance().getTravellingFor(),
                "",
                "",
                "",
                ""
        );
        getHotelList();

    }

    private void pagenation() {

    }

}
