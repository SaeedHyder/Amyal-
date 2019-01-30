package com.app.amyal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.amyal.R;
import com.app.amyal.entities.AmentityEnt;
import com.app.amyal.entities.Descriptions;
import com.app.amyal.entities.HotelListModel;
import com.app.amyal.entities.HotelPoliciesEnt;
import com.app.amyal.entities.HotelSearchModel;
import com.app.amyal.entities.RoomExtra;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.Utils;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.DateHelper;
import com.app.amyal.helpers.DialogHelper;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.retrofit.GsonFactory;
import com.app.amyal.ui.adapters.ArrayListAdapter;
import com.app.amyal.ui.binders.BinderAmentities;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.AnyTextView1;
import com.app.amyal.ui.views.CustomRatingBar;
import com.app.amyal.ui.views.TitleBar;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RoomDetailsFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {


    @BindView(R.id.slRoom)
    SliderLayout slRoom;
    @BindView(R.id.pagerIndicator)
    PagerIndicator pagerIndicator;
    @BindView(R.id.tvRoomName)
    AnyTextView tvRoomName;
    @BindView(R.id.tvRatting)
    AnyTextView tvRatting;
    @BindView(R.id.tvRattingInWord)
    AnyTextView tvRattingInWord;
    @BindView(R.id.CrHotelRating)
    CustomRatingBar CrHotelRating;
    @BindView(R.id.tvPrice)
    AnyTextView tvPrice;
    @BindView(R.id.tvAboutRoom)
    AnyTextView1 tvAboutRoom;
    @BindView(R.id.tvReadmore)
    AnyTextView tvReadmore;
    @BindView(R.id.gvAmentities)
    GridView gvAmentities;
    @BindView(R.id.llAmentities)
    LinearLayout llAmentities;
    @BindView(R.id.btnBook)
    Button btnBook;
    Unbinder unbinder;
    @BindView(R.id.tvCheckInTime)
    AnyTextView tvCheckInTime;
    @BindView(R.id.tvCheckOutTime)
    AnyTextView tvCheckOutTime;
    @BindView(R.id.tvNearBy)
    AnyTextView1 tvNearBy;
    @BindView(R.id.llHotelPolicies)
    RelativeLayout llHotelPolicies;
    @BindView(R.id.llHotelFacilities)
    RelativeLayout llHotelFacilities;

    @BindView(R.id.BtnViewAll)
    AnyTextView BtnViewAll;
    @BindView(R.id.gvRoomExtras)
    GridView gvRoomExtras;

    @BindView(R.id.tvRoomSpace)
    AnyTextView tvRoomSpace;

    @BindView(R.id.vRoomSpace)
    View vRoomSpace;
    HotelListModel hotelListModel;
    @BindView(R.id.gvRoomExtrasview)
    View gvRoomExtrasview;
    @BindView(R.id.viewSepratorAmenties)
    View viewSepratorAmenties;
    @BindView(R.id.viewNearby)
    View viewNearby;
    private List<AmentityEnt> amentityEntList;
    private ArrayListAdapter<AmentityEnt> adapter;
    private List<AmentityEnt> roomExtrasEntList;
    private ArrayListAdapter<AmentityEnt> roomExtrasAdapter;

    public static RoomDetailsFragment newInstance() {
        Bundle args = new Bundle();

        RoomDetailsFragment fragment = new RoomDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new BinderAmentities(getDockActivity(), prefHelper));
        roomExtrasAdapter = new ArrayListAdapter<>(getDockActivity(), new BinderAmentities(getDockActivity(), prefHelper));

        if (getArguments() != null && hotelListModel == null) {
            hotelListModel = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.HotelListModel), HotelListModel.class);
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.room_details));
        titleBar.showBackButton();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_details, container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (hotelListModel != null) {
            setImageGallery(hotelListModel.getRoomGalleryEnt().getImages());
            if (hotelListModel.getRoomGalleryEnt().getAmenities() != null) {
                bindAmentities(hotelListModel.getRoomGalleryEnt().getAmenities());
                Utils.setDynamicHeight(gvAmentities);
            } else {
                gvAmentities.setVisibility(View.GONE);
            }
            int position = hotelListModel.getRoomGalleryEnt().getPosition();
            setData(hotelListModel.getRoomGalleryEnt().getPosition());

            if (hotelListModel.getRoomCombinations().get(position).getRoomExtras() != null && hotelListModel.getRoomCombinations().get(position).getRoomExtras().size() > 0) {
                bindRoomExtras(hotelListModel.getRoomCombinations().get(position).getRoomExtras());
                Utils.setDynamicHeight(gvRoomExtras);
            } else {
                gvRoomExtras.setVisibility(View.GONE);
                gvRoomExtrasview.setVisibility(View.GONE);
            }
        }

    }

    public void setData(int position) {

        HotelSearchModel hotelSearchModel = HotelSearchModel.getInstance();
        hotelSearchModel.setAmountWithTax(hotelListModel.getRoomCombinations().get(position).getAmountWithTax());
        hotelSearchModel.setCurrencyCode(hotelListModel.getRoomCombinations().get(position).getCurrencyCode());

        tvRoomName.setSelected(true);
        tvRoomName.setText(hotelListModel.getRoomCombinations().get(position).getRoomType());

        if (hotelListModel.getRoomGalleryEnt().getNearByPlaces() != null && hotelListModel.getRoomGalleryEnt().getNearByPlaces().size() > 0) {

            String Place = hotelListModel.getRoomGalleryEnt().getNearByPlaces().get(0).getDistance() + " " + hotelListModel.getRoomGalleryEnt().getNearByPlaces().get(0).getDistanceUnit() + " from " + hotelListModel.getRoomGalleryEnt().getNearByPlaces().get(0).getPlace();
            tvNearBy.setSelected(true);
            tvNearBy.setText(Place);
        } else {
            tvNearBy.setText("");
            tvNearBy.setVisibility(View.GONE);
            viewNearby.setVisibility(View.GONE);
        }

        if (hotelListModel.getRoomGalleryEnt().getDescriptions() != null) {
            for (Descriptions description : hotelListModel.getRoomGalleryEnt().getDescriptions()) {

                if (description.getKey().equalsIgnoreCase("LNG")) {
                    tvAboutRoom.setText(description.getValue());
                }
            }
        }

        tvPrice.setText(hotelListModel.getCurrencyCode() + " " + hotelListModel.getRoomCombinations().get(position).getAmountWithTax());
        tvCheckInTime.setText(hotelListModel.getRoomGalleryEnt().getCheckInTime());
        tvCheckOutTime.setText(hotelListModel.getRoomGalleryEnt().getCheckOutTime());

        if (hotelListModel.getRoomGalleryEnt().getTotalReviewsCount() != null && hotelListModel.getRoomGalleryEnt().getTotalReviewsCount().length() > 0 && Integer.parseInt(hotelListModel.getRoomGalleryEnt().getTotalReviewsCount()) >= 10) {
            tvRatting.setVisibility(View.VISIBLE);
            tvRattingInWord.setVisibility(View.VISIBLE);

            String ratting = "";
            if (hotelListModel.getRoomGalleryEnt().getRoomRating().length() > 0) {
                ratting = Utils.getTextRatting(Float.parseFloat(hotelListModel.getRoomGalleryEnt().getRoomRating()));
                CrHotelRating.setScore(Float.parseFloat(hotelListModel.getRoomGalleryEnt().getRoomRating()));
            } else {
                CrHotelRating.setVisibility(View.GONE);
            }

            if (ratting.length() > 0) {
                tvRatting.setText(hotelListModel.getRoomGalleryEnt().getRoomRating() + " ");
                tvRattingInWord.setText(ratting);
            }

        } else {
            CrHotelRating.setVisibility(View.GONE);
            tvRatting.setVisibility(View.GONE);
            tvRattingInWord.setVisibility(View.GONE);

        }

        if (hotelListModel.getRoomCombinations().get(position).getRoomOccupancy() != null && hotelListModel.getRoomCombinations().get(position).getRoomOccupancy().length() > 0) {
            tvRoomSpace.setText(hotelListModel.getRoomCombinations().get(position).getRoomOccupancy());
        } else {
            tvRoomSpace.setVisibility(View.GONE);
            vRoomSpace.setVisibility(View.GONE);
        }

       /* if (hotelListModel.getRoomGalleryEnt().getRoomRating().length() > 0)
            CrHotelRating.setScore(Float.parseFloat(hotelListModel.getRoomGalleryEnt().getRoomRating()));
        else
            CrHotelRating.setVisibility(View.GONE);*/

    }

    public void bindAmentities(List<String> result) {

        if (result != null && result.size() > 0) {
            amentityEntList = new ArrayList<>();

            for (String amenity : result) {

                amentityEntList.add(new AmentityEnt(Utils.getIcon(amenity), amenity));
            }

            adapter.clearList();
            gvAmentities.setAdapter(adapter);
            adapter.addAll(amentityEntList);
            adapter.notifyDataSetChanged();
        } else {
            llAmentities.setVisibility(View.GONE);
            BtnViewAll.setVisibility(View.GONE);
            viewSepratorAmenties.setVisibility(View.GONE);
        }

    }

    public void bindRoomExtras(List<RoomExtra> result) {

        if (result != null && result.size() > 0) {
            roomExtrasEntList = new ArrayList<>();

            for (RoomExtra roomExtra : result) {

                roomExtrasEntList.add(new AmentityEnt("drawable://" + R.drawable.tick1, roomExtra.getValue()));
            }

            roomExtrasAdapter.clearList();
            gvRoomExtras.setAdapter(roomExtrasAdapter);
            roomExtrasAdapter.addAll(roomExtrasEntList);
            roomExtrasAdapter.notifyDataSetChanged();
        }

    }

    private void setImageGallery(List<String> arrayList) {

        slRoom.removeAllSliders();
        /*ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(0, "https://www.dusit.com/wp-content/blogs.dir/37/files/rooms_deluxe-room/dtmn_accommodation_deluxeroom_1088x648.jpg");
        arrayList.add(1, "http://0b4be8c38b52c56cf50d-11e10875c2ded76a664f4f2a5cbc7b78.r84.cf1.rackcdn.com/responsive/790:492/c3431454.r54.cf0.rackcdn.com/XLGallery/Deluxe_Room_Empire_Style.jpg");
        arrayList.add(2, "https://www.dusit.com/wp-content/blogs.dir/37/files/rooms_deluxe-room/dtmn_accommodation_deluxeroom_1088x648.jpg");
        arrayList.add(3, "http://0b4be8c38b52c56cf50d-11e10875c2ded76a664f4f2a5cbc7b78.r84.cf1.rackcdn.com/responsive/790:492/c3431454.r54.cf0.rackcdn.com/XLGallery/Deluxe_Room_Empire_Style.jpg");*/

        for (String image : arrayList) {
            DefaultSliderView textSliderView = new DefaultSliderView(getDockActivity());
            // initialize a SliderLayout
            textSliderView
                    .image(image)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", image + "");

            slRoom.addSlider(textSliderView);
        }

        slRoom.setPresetTransformer(SliderLayout.Transformer.Accordion);
        slRoom.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slRoom.setCustomAnimation(new DescriptionAnimation());
        slRoom.addOnPageChangeListener(this);
        slRoom.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        slRoom.stopAutoCycle();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.getHotelPolicies:

                HotelPoliciesEnt hotelPoliciesEnt = (HotelPoliciesEnt) result;

                HotelSearchModel.getInstance().setBookingCode(hotelPoliciesEnt.getBookingCode());
                String[] txt = hotelPoliciesEnt.getBookingExpiry().split(" ");
                HotelSearchModel.getInstance().setBookingExpiry("Booking code will expire in " + (int) Double.parseDouble(txt[0]) + " " + txt[1]);


                if (hotelPoliciesEnt != null) {
                    HotelPolicyFacilityFragment hotelPolicyFacilityFragment = new HotelPolicyFacilityFragment();
                    Bundle orderBundle = new Bundle();
                    orderBundle.putBoolean(AppConstants.IsFacilities, false);
                    orderBundle.putString(AppConstants.HotelPolicyModel, new Gson().toJson(hotelPoliciesEnt));
                    hotelPolicyFacilityFragment.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(hotelPolicyFacilityFragment, HotelDetailFragment.class.getSimpleName());
                }

                break;
        }
    }


    @OnClick({R.id.tvReadmore, R.id.btnBook, R.id.llHotelPolicies, R.id.llHotelFacilities, R.id.BtnViewAll})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tvReadmore:
                tvAboutRoom.setMaxLines(15);
                tvReadmore.setVisibility(View.GONE);
                break;

            case R.id.llHotelPolicies:

                String LangCode = "en";
                String CurCode = "AED";

                if (prefHelper.isLogin()) {

                    LangCode = prefHelper.getUser().getUser().getLanguageCode();
                    CurCode = prefHelper.getUser().getUser().getCurrencyCode();

                }

                serviceHelper.enqueueCall(webService.GetHotelPolicies(
                        DateHelper.getFormatedDate("yyyy-MM-dd", "dd MMM,yyyy", HotelSearchModel.getInstance().getCheckInDate()),
                        DateHelper.getFormatedDate("yyyy-MM-dd", "dd MMM,yyyy", HotelSearchModel.getInstance().getCheckOutDate()),
                        LangCode,
                        CurCode,
                        HotelSearchModel.getInstance().getSequenceNumber(),
                        HotelSearchModel.getInstance().getHotelCode(),
                        HotelSearchModel.getInstance().getRatePlanCode()
                ), WebServiceConstants.getHotelPolicies);

                break;

            case R.id.llHotelFacilities:

                HotelPolicyFacilityFragment hotelPolicyFacilityFragment = new HotelPolicyFacilityFragment();
                Bundle orderBundle = new Bundle();
                orderBundle.putBoolean(AppConstants.IsFacilities, true);
                orderBundle.putString(AppConstants.HotelListModel, new Gson().toJson(hotelListModel.getRoomGalleryEnt()));
                hotelPolicyFacilityFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(hotelPolicyFacilityFragment, HotelDetailFragment.class.getSimpleName());

                break;

            case R.id.BtnViewAll:

                BtnViewAll.setVisibility(View.GONE);
                llAmentities.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                break;

            case R.id.btnBook:

                if (prefHelper.isLogin()) {
                    if (hotelListModel != null) {

                        //Read policy check
                        if (HotelSearchModel.getInstance().getBookingCode() == null || HotelSearchModel.getInstance().getBookingCode().equals("")) {

                            UIHelper.showLongToastInCenter(getDockActivity(), getDockActivity().getResources().getString(R.string.read_policy));
                            return;

                        }
                        HotelAddInfoFragment hotelAddInfoFragment = new HotelAddInfoFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstants.HotelListModel, new Gson().toJson(hotelListModel));
                        hotelAddInfoFragment.setArguments(bundle);
                        getDockActivity().replaceDockableFragment(hotelAddInfoFragment, HotelAddInfoFragment.class.getSimpleName());
                    }
                } else {
                    final DialogHelper logoutdialog = new DialogHelper(getDockActivity());
                    logoutdialog.initLoginAlertDialog(R.layout.dialog_login_alert, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            logoutdialog.hideDialog();
                            LoginFragment loginFragment = new LoginFragment();
                            loginFragment.setIsCameFromBuyFlow(true);
                            getDockActivity().replaceDockableFragment(loginFragment, LoginFragment.class.getSimpleName());
                        }
                    });
                    logoutdialog.setCancelable(true);
                    logoutdialog.showDialog();
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}