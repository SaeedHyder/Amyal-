package com.app.amyal.fragments;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.app.amyal.Comparators.SortBasedOnHotelCategory;
import com.app.amyal.Comparators.SortBasedOnHotelName;
import com.app.amyal.R;
import com.app.amyal.entities.HotelGalleryEnt;
import com.app.amyal.entities.HotelListEnt;
import com.app.amyal.entities.HotelListModel;
import com.app.amyal.entities.HotelSearchModel;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.Utils;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.DateHelper;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.retrofit.GsonFactory;
import com.app.amyal.ui.adapters.FilterableListAdapter;
import com.app.amyal.ui.binders.HotelListBinder;
import com.app.amyal.ui.views.AnyEditTextView;
import com.app.amyal.ui.views.TitleBar;
import com.google.common.base.Function;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class SelectHotelsFragment extends BaseFragment implements ItemClickListener {

    //    @BindView(R.id.spSort)
//    Spinner spSort;
    @BindView(R.id.ivMap)
    ImageView ivMap;
    //    @BindView(R.id.llSpinner)
//    LinearLayout llSpinner;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.edt_search)
    AnyEditTextView edtSearch;
    @BindView(R.id.ivClose)
    ImageView ivClose;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.lvHotels)
    ListView lvHotels;
    int pageIndex;

    HotelListModel hotelListModel;
    //    ArrayList<String> sort_list = new ArrayList<>();
    ArrayAdapter<String> sortAdapter;
    HotelListEnt hotelListEnt;
    SelectHotelsFragment context;
    private FilterableListAdapter<HotelListModel> adapter;
    private PopupWindow popup;
    private int preLast;

    public static SelectHotelsFragment newInstance() {
        return new SelectHotelsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new FilterableListAdapter<>(getDockActivity(), new HotelListBinder(getDockActivity(), prefHelper));
        context = this;

    }

    @Override
    public void setTitleBar(final TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.hotels));
        titleBar.showRightButton(R.drawable.add_fav, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFavPopup(v);
            }
        });

        titleBar.showRight1Button(R.drawable.filter, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentHotelFilter fragmentHotelFilter = new FragmentHotelFilter();
                fragmentHotelFilter.setListner(context);
                getDockActivity().replaceDockableFragment(fragmentHotelFilter, FragmentHotelFilter.class.getSimpleName());
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_hotels, container, false);

        getMainActivity().setBackground(R.drawable.bg_hotel);

        ButterKnife.bind(this, view);

        if (getArguments() != null && hotelListEnt == null) {
            hotelListEnt = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.HotelListEnt), HotelListEnt.class);
        }

        init();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (hotelListEnt != null)
            bindData(hotelListEnt);

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

        lvHotels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (hotelListEnt != null && hotelListEnt.getResponseModel() != null) {

                    String LangCode = "en";
                    String CurCode = "AED";

                    if (prefHelper.isLogin()) {

                        LangCode = prefHelper.getUser().getUser().getLanguageCode();
                        CurCode = prefHelper.getUser().getUser().getCurrencyCode();

                    }

                    hotelListModel = adapter.getList().get(position);
                    HotelSearchModel hotelSearchModel = HotelSearchModel.getInstance();
                    hotelSearchModel.setSequenceNumber(hotelListEnt.getSequenceNumber());
                    hotelSearchModel.setHotelCode(hotelListModel.getHotelCode());
                    serviceHelper.enqueueCall(webService.GetHotelDetails(LangCode, CurCode, hotelSearchModel.getHotelCode()/*, hotelListModel.getRoomCombinations().get(0).getRatePlanCode(), hotelListModel.getRoomCombinations().get(0).getRoomType()*/), WebServiceConstants.getHotelDetails);
                }
            }
        });

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.getHotelDetails:

                HotelGalleryEnt hotelGalleryEnt = (HotelGalleryEnt) result;

                if (hotelGalleryEnt != null) {
                    hotelListModel.setHotelGalleryEnt(hotelGalleryEnt);
                    HotelDetailFragment hotelDetailFragment = new HotelDetailFragment();
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.HotelListModel, new Gson().toJson(hotelListModel));
                    hotelDetailFragment.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(hotelDetailFragment, HotelDetailFragment.class.getSimpleName());
                }

                break;

            case WebServiceConstants.getHotelList:

                HotelListEnt hotelListEnt = (HotelListEnt) result;

                if (hotelListEnt != null) {

                    adapter.addAll(hotelListEnt.getResponseModel());
                    adapter.notifyDataSetChanged();
                }

                break;

        }
    }

    private void filterList(String string) {
        adapter.getFilter().filter(string);
    }


    public void bindData(HotelListEnt hotelListEnt) {

        adapter.clearList();
        adapter = new FilterableListAdapter<HotelListModel>(getDockActivity(), new ArrayList<HotelListModel>(), new HotelListBinder(getDockActivity(), prefHelper),
                new Function<HotelListModel, String>() {
                    @Override
                    @Nullable
                    public String apply(
                            @Nullable HotelListModel arg0) {
                        return arg0.getHotelName();
                    }
                });
        lvHotels.setAdapter(adapter);
        adapter.addAll(hotelListEnt.getResponseModel());
        adapter.notifyDataSetChanged();


    }

    @OnClick({R.id.ivMap, R.id.ivClose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivMap:
                Utils.HideKeyBoard(getDockActivity());
                SelectHotelsMapFragment selectHotelsMapFragment = new SelectHotelsMapFragment();
                Bundle orderBundle = new Bundle();
                orderBundle.putString(AppConstants.HotelListEnt, new Gson().toJson(hotelListEnt));
                selectHotelsMapFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(selectHotelsMapFragment, SelectHotelsMapFragment.class.getSimpleName());
                break;
            case R.id.ivClose:
                edtSearch.setText("");
                Utils.HideKeyBoard(getDockActivity());
                ivClose.setVisibility(View.INVISIBLE);
                break;
        }
    }


    @Override
    public void itemClicked(Object object, int position) {

        pageIndex = 0;
        hotelListEnt = (HotelListEnt) object;
        bindData(hotelListEnt);

    }

    @Override
    public void itemClicked(Object object, boolean isfrom, int i) {

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
                    ivClose.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pagenationEvent();
    }


    private void showFavPopup(View parentView) {
        popup = new PopupWindow(myDockActivity);
        View layout = getLayoutInflater().inflate(R.layout.layout_add_to_fav_popup, null);
        final TextView tvAtoZ = (TextView) layout.findViewById(R.id.tvAtoZ);
        final TextView tvZtoA = (TextView) layout.findViewById(R.id.tvZtoA);
        final TextView tvRating = (TextView) layout.findViewById(R.id.tvRating);

        popup.setContentView(layout);
        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        // Show anchored to button
        popup.setBackgroundDrawable(/*mainActivityContext.getResources().getDrawable(R.drawable.shape)*/new BitmapDrawable());
        popup.showAsDropDown(parentView);
        UIHelper.applyDim(getMainActivity());

        tvAtoZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hotelListEnt != null) {
                    Collections.sort(hotelListEnt.getResponseModel(), new SortBasedOnHotelName());
                    bindData(hotelListEnt);
                    popup.dismiss();
                }
            }
        });
        tvZtoA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(hotelListEnt.getResponseModel(), new SortBasedOnHotelName());
                Collections.reverse(hotelListEnt.getResponseModel());
                bindData(hotelListEnt);
                popup.dismiss();
            }
        });
        tvRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(hotelListEnt.getResponseModel(), new SortBasedOnHotelCategory());
                Collections.reverse(hotelListEnt.getResponseModel());
                bindData(hotelListEnt);
                popup.dismiss();
            }
        });
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                UIHelper.clearDim(getMainActivity());
            }
        });
    }

    private void hotelListWebservice() {

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
        RequestBody body = null;
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
            mainObj.put("PageIndex", pageIndex);
            mainObj.put("DeviceID", prefHelper.getUser().getAuthToken());
//            mainObj.put("PageIndex", pageIndex);

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

    private void pagenationEvent() {
        lvHotels.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) {
                        //to avoid multiple calls for last item
                        pageIndex++;
                        hotelListWebservice();
                        preLast = lastItem;
                    }
                }
            }
        });
    }
}
