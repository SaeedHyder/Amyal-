package com.app.amyal.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.app.amyal.R;
import com.app.amyal.entities.AmentityEnt;
import com.app.amyal.entities.Descriptions;
import com.app.amyal.entities.HotelGalleryEnt;
import com.app.amyal.entities.HotelListModel;
import com.app.amyal.entities.HotelSearchModel;
import com.app.amyal.entities.RoomCombination;
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
import com.app.amyal.ui.binders.HotelDetailRoomItemBinder;
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
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by khan_muhammad on 12/4/2017.
 */

public class HotelDetailFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {


    @BindView(R.id.slHotel)
    SliderLayout slHotel;
    @BindView(R.id.pagerIndicator)
    PagerIndicator pagerIndicator;
    @BindView(R.id.tvHotelName)
    AnyTextView1 tvHotelName;
    @BindView(R.id.tvRatting)
    AnyTextView tvRatting;
    @BindView(R.id.tvRattingInWord)
    AnyTextView tvRattingInWord;
    @BindView(R.id.CrHotelRating)
    CustomRatingBar CrHotelRating;
    @BindView(R.id.tvPrice)
    AnyTextView tvPrice;
    @BindView(R.id.ivMap)
    ImageView ivMap;
    @BindView(R.id.tvAddress)
    AnyTextView tvAddress;
    @BindView(R.id.tvTrain)
    AnyTextView tvTrain;
    @BindView(R.id.tvCheckInTime)
    AnyTextView tvCheckInTime;
    @BindView(R.id.tvCheckOutTime)
    AnyTextView tvCheckOutTime;
    @BindView(R.id.tvAboutHotel)
    AnyTextView1 tvAboutHotel;
    @BindView(R.id.tvReadmore)
    AnyTextView tvReadmore;
    @BindView(R.id.llHotelPolicies)
    RelativeLayout llHotelPolicies;
    @BindView(R.id.llHotelFacilities)
    RelativeLayout llHotelFacilities;
    @BindView(R.id.btnAddReview)
    AnyTextView btnAddReview;

    @BindView(R.id.tvUserName1)
    AnyTextView tvUserName1;
    @BindView(R.id.CrUser1Rating)
    CustomRatingBar CrUser1Rating;
    @BindView(R.id.tvUser1Date)
    AnyTextView tvUser1Date;
    @BindView(R.id.tvUser1Review)
    AnyTextView tvUser1Review;

    @BindView(R.id.tvUserName2)
    AnyTextView tvUserName2;
    @BindView(R.id.CrUser2Rating)
    CustomRatingBar CrUser2Rating;
    @BindView(R.id.tvUser2Date)
    AnyTextView tvUser2Date;
    @BindView(R.id.tvUser2Review)
    AnyTextView tvUser2Review;
    @BindView(R.id.BtnViewAll)
    AnyTextView BtnViewAll;
    Unbinder unbinder;
    @BindView(R.id.btnBook)
    Button btnBook;
    @BindView(R.id.ivMap1)
    RoundedImageView ivMap1;

    HotelListModel hotelListModel;
    @BindView(R.id.llTextRatting)
    LinearLayout llTextRatting;
    @BindView(R.id.lvRooms)
    ListView lvRooms;
    @BindView(R.id.llUser1)
    LinearLayout llUser1;
    @BindView(R.id.llUser2)
    LinearLayout llUser2;
    @BindView(R.id.llReviews)
    LinearLayout llReviews;

    int roomPosition = 0;
    @BindView(R.id.ivUser1)
    CircleImageView ivUser1;
    @BindView(R.id.ivUser2)
    CircleImageView ivUser2;
    @BindView(R.id.llAbout)
    LinearLayout llAbout;
    @BindView(R.id.rlReview)
    RelativeLayout rlReview;
    @BindView(R.id.rlCheckIn)
    RelativeLayout rlCheckIn;
    @BindView(R.id.gvAmentities)
    GridView gvAmentities;
    @BindView(R.id.llAmentities)
    LinearLayout llAmentities;
    @BindView(R.id.rlCheckout)
    RelativeLayout rlCheckout;

    private ArrayListAdapter<RoomCombination> adapter;
    private ArrayListAdapter<AmentityEnt> adapterAmenities;
    private List<AmentityEnt> amentityEntList;

    public static HotelDetailFragment newInstance() {
        return new HotelDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new HotelDetailRoomItemBinder(getDockActivity(), prefHelper));
        adapterAmenities = new ArrayListAdapter<>(getDockActivity(), new BinderAmentities(getDockActivity(), prefHelper));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel_detail, container, false);

        getMainActivity().setBackground(R.drawable.bg_hotel);

        unbinder = ButterKnife.bind(this, view);

        if (getArguments() != null && hotelListModel == null) {
            hotelListModel = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.HotelListModel), HotelListModel.class);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //serviceHelper.enqueueCall(webService.aboutUs(prefHelper.getMerchantId(), AppConstants.AboutUs), WebServiceConstants.aboutus);
        if (hotelListModel != null) {
            maps(hotelListModel.getLatitude(), hotelListModel.getLongitude());
            setData();
            setImageGallery();
            Utils.justifyListViewHeightBasedOnChildren(lvRooms);
        }

        lvRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                roomPosition = position;
                getRoomDetail(position);
                //getDockActivity().replaceDockableFragment(HotelRoomFragment.newInstance(), HotelRoomFragment.class.getSimpleName());
            }
        });

    }

    public void getRoomDetail(int position) {

        if (hotelListModel != null) {

            String LangCode = "en";
            String CurCode = "SUR";

            if (prefHelper.isLogin()) {

                LangCode = prefHelper.getUser().getUser().getLanguageCode();
                CurCode = prefHelper.getUser().getUser().getCurrencyCode();

            }

            HotelSearchModel hotelSearchModel = HotelSearchModel.getInstance();
            hotelSearchModel.setRatePlanCode(hotelListModel.getRoomCombinations().get(position).getRatePlanCode());

            serviceHelper.enqueueCall(webService.GetRoomDetails(hotelListModel.getHotelCode(), hotelListModel.getRoomCombinations().get(position).getRatePlanCode(), LangCode, CurCode, hotelListModel.getRoomCombinations().get(position).getRoomType()), WebServiceConstants.getHotelDetails);
        }

    }


    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.getHotelDetails:

                HotelGalleryEnt hotelGalleryEnt = (HotelGalleryEnt) result;


                if (hotelGalleryEnt != null) {
                    hotelGalleryEnt.setPosition(roomPosition);
                    hotelListModel.setRoomGalleryEnt(hotelGalleryEnt);
                    RoomDetailsFragment roomDetailsFragment = new RoomDetailsFragment();
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.HotelListModel, new Gson().toJson(hotelListModel));
                    roomDetailsFragment.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(roomDetailsFragment, HotelDetailFragment.class.getSimpleName());
                }

                break;
        }
    }

    private void setData() {

        tvHotelName.setSelected(true);
        tvHotelName.setText(hotelListModel.getHotelName());
        CrHotelRating.setScore(Float.parseFloat(hotelListModel.getRating().equals("") ? "0" : hotelListModel.getRating()));
        tvAddress.setText(hotelListModel.getAddress());
//        if (hotelListModel.getDescription() == null)
//            llAbout.setVisibility(View.GONE);
        tvAboutHotel.setText(hotelListModel.getDescription());

        tvPrice.setText(hotelListModel.getCurrencyCode() + " " + hotelListModel.getHotelPriceMin() + " - " + hotelListModel.getHotelPriceMax());

        if (hotelListModel.getTotalReviewCount() != null && hotelListModel.getTotalReviewCount().length() > 0 && Integer.parseInt(hotelListModel.getTotalReviewCount()) >= 10) {
            llTextRatting.setVisibility(View.VISIBLE);
            String ratting = Utils.getTextRatting(Float.parseFloat(hotelListModel.getRating()));
            if (ratting.length() > 0) {
                tvRatting.setText(hotelListModel.getRating() + " ");
                tvRattingInWord.setText(ratting);
            }
        } else {
            llTextRatting.setVisibility(View.GONE);
        }

        if (hotelListModel.getRoomCombinations() != null && hotelListModel.getRoomCombinations().size() > 0) {
            adapter.clearList();
            lvRooms.setAdapter(adapter);
            adapter.addAll(hotelListModel.getRoomCombinations());
            adapter.notifyDataSetChanged();
        }

        if (hotelListModel.getTopReviews() != null && hotelListModel.getTopReviews().size() > 0) {

            llReviews.setVisibility(View.VISIBLE);

            if (hotelListModel.getTopReviews().size() >= 2) {
                BtnViewAll.setVisibility(View.VISIBLE);
                llUser1.setVisibility(View.VISIBLE);
                llUser2.setVisibility(View.VISIBLE);

                tvUserName1.setText(hotelListModel.getTopReviews().get(0).getUserName());
                //tvUser1Date.setText(hotelListModel.getTopReviews().get(0).getDate());
                tvUser1Date.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMDHMS, AppConstants.DateFormat_DMY3, hotelListModel.getTopReviews().get(0).getDate()));
                tvUser1Review.setText(hotelListModel.getTopReviews().get(0).getReview());
                CrUser1Rating.setScore(Float.parseFloat(hotelListModel.getTopReviews().get(0).getRating()));

                if (hotelListModel.getTopReviews().get(0).getUserImage() != null && hotelListModel.getTopReviews().get(0).getUserImage().length() > 0) {
                    Picasso.with(getDockActivity())
                            .load(hotelListModel.getTopReviews().get(0).getUserImage())
                            .into(ivUser1);
                }

                tvUserName2.setText(hotelListModel.getTopReviews().get(1).getUserName());
                tvUser2Date.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMDHMS, AppConstants.DateFormat_DMY3, hotelListModel.getTopReviews().get(1).getDate()));
                tvUser2Review.setText(hotelListModel.getTopReviews().get(1).getReview());
                CrUser2Rating.setScore(Float.parseFloat(hotelListModel.getTopReviews().get(1).getRating()));


                if (hotelListModel.getTopReviews().get(1).getUserImage() != null && hotelListModel.getTopReviews().get(1).getUserImage().length() > 0) {
                    Picasso.with(getDockActivity())
                            .load(hotelListModel.getTopReviews().get(1).getUserImage())
                            .into(ivUser2);
                }

            } else {
                BtnViewAll.setVisibility(View.GONE);
                llUser2.setVisibility(View.GONE);

                if (hotelListModel.getTopReviews().size() > 0) {

                    tvUserName1.setText(hotelListModel.getTopReviews().get(0).getUserName());
                    //tvUser1Date.setText(hotelListModel.getTopReviews().get(0).getDate());
                    tvUser1Date.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMDHMS, AppConstants.DateFormat_DMY3, hotelListModel.getTopReviews().get(0).getDate()));
                    tvUser1Review.setText(hotelListModel.getTopReviews().get(0).getReview());
                    CrUser1Rating.setScore(Float.parseFloat(hotelListModel.getTopReviews().get(0).getRating()));

                    if (hotelListModel.getTopReviews().get(0).getUserImage() != null && hotelListModel.getTopReviews().get(0).getUserImage().length() > 0) {
                        Picasso.with(getDockActivity())
                                .load(hotelListModel.getTopReviews().get(0).getUserImage())
                                .into(ivUser1);
                    }

                } else {
                    llUser1.setVisibility(View.GONE);
                    llReviews.setVisibility(View.GONE);
                }

            }

        } else {
            rlReview.setVisibility(View.GONE);
            llReviews.setVisibility(View.GONE);

        }
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.hotel_deatils)); //hotelListModel.getHotelName()
        titleBar.showBackButton();
    }

    private void maps(String latitude, String longitude) {

        /*String path = "https://maps.googleapis.com/maps/api/staticmap?zoom=15&scale=2&size=360x160&maptype=roadmap" +
                "&markers=color:red|25.1972,55.2744&key=AIzaSyCQpUf689kOKkfB_y8vPxUhO1SiGO0QSEg";*/
        String path = "https://maps.googleapis.com/maps/api/staticmap?zoom=15&scale=2&size=360x160&maptype=roadmap" + "&markers=color:red|" + latitude + "," + longitude + "&key=AIzaSyCQpUf689kOKkfB_y8vPxUhO1SiGO0QSEg";
        Picasso.with(getDockActivity()).load(path).fit().into(ivMap1);
    }

    private void setImageGallery() {
        setDetail();
        slHotel.removeAllSliders();
        List<String> arrayList = hotelListModel.getHotelGalleryEnt().getImages();

        if (arrayList != null) {
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

                slHotel.addSlider(textSliderView);
            }

            slHotel.setPresetTransformer(SliderLayout.Transformer.Accordion);
            slHotel.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            slHotel.setCustomAnimation(new DescriptionAnimation());
            slHotel.addOnPageChangeListener(this);
            slHotel.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
            slHotel.stopAutoCycle();
        }
    }

    @OnClick({R.id.ivMap1, R.id.tvReadmore, R.id.btnAddReview, R.id.BtnViewAll, R.id.llHotelPolicies, R.id.llHotelFacilities, R.id.btnBook})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivMap1:

                if (hotelListModel != null && hotelListModel.getLatitude() != null && hotelListModel.getLongitude() != null) {
                    String geoUri = "http://maps.google.com/maps?q=loc:" + hotelListModel.getLatitude() + "," + hotelListModel.getLongitude() + " (" + getString(R.string.app_name) + ")";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                    startActivity(intent);
                }
                break;

            case R.id.tvReadmore:
                tvAboutHotel.setMaxLines(15);
                tvReadmore.setVisibility(View.GONE);
                break;

            case R.id.btnAddReview:

                final DialogHelper addReviewDialog = new DialogHelper(getDockActivity());
                addReviewDialog.initAddReviewDialog(R.layout.dialog_add_review, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (addReviewDialog.getReview(R.id.edtReview).length() > 0) {
                            addReviewDialog.hideDialog();
                        } else {
                            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_enter_review));
                        }
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addReviewDialog.hideDialog();
                    }
                });
                addReviewDialog.setCancelable(false);
                addReviewDialog.showDialog();

                break;

            case R.id.BtnViewAll:
                HotelReviewsFragment hotelReviewsFragment = new HotelReviewsFragment();
                hotelReviewsFragment.setisHotel(1);
                hotelReviewsFragment.setReviews(hotelListModel.getTopReviews());
               /* Bundle bundle = new Bundle();
                bundle.putString(AppConstants.HotelReviewsModel, new Gson().toJson(hotelListModel.getTopReviews()));
                hotelReviewsFragment.setArguments(bundle);*/
                getDockActivity().replaceDockableFragment(hotelReviewsFragment, HotelReviewsFragment.class.getSimpleName());
                break;

            case R.id.llHotelPolicies:

                HotelPolicyFacilityFragment hotelPolicyFacilityFragment = HotelPolicyFacilityFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putBoolean(AppConstants.IsFacilities, false);
                hotelPolicyFacilityFragment.setArguments(bundle);
                getDockActivity().replaceDockableFragment(hotelPolicyFacilityFragment, HotelPolicyFacilityFragment.class.getSimpleName() + "Policies");

                break;

            case R.id.llHotelFacilities:

                hotelPolicyFacilityFragment = HotelPolicyFacilityFragment.newInstance();
                bundle = new Bundle();
                bundle.putBoolean(AppConstants.IsFacilities, true);
                hotelPolicyFacilityFragment.setArguments(bundle);
                getDockActivity().replaceDockableFragment(hotelPolicyFacilityFragment, HotelPolicyFacilityFragment.class.getSimpleName() + "Facilities");

                break;

            case R.id.btnBook:
                getDockActivity().replaceDockableFragment(HotelRoomFragment.newInstance(), HotelRoomFragment.class.getSimpleName());
                break;
        }
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


    private void setDetail() {
        HotelGalleryEnt hotelGalleryEnt = hotelListModel.getHotelGalleryEnt();
        if (hotelGalleryEnt.getCheckInTime().equals("")) {
            rlCheckIn.setVisibility(View.GONE);
            rlCheckout.setVisibility(View.GONE);
        }
        tvCheckInTime.setText(hotelGalleryEnt.getCheckInTime());
        tvCheckOutTime.setText(hotelGalleryEnt.getCheckOutTime());
        if (hotelGalleryEnt.getAmenities() != null) {
            bindAmentities(hotelGalleryEnt.getAmenities());
            Utils.setDynamicHeight(gvAmentities);
        } else {
            gvAmentities.setVisibility(View.GONE);
        }
        for (Descriptions description : hotelGalleryEnt.getDescriptions()) {

            if (description.getKey().equalsIgnoreCase("LNG")) {
                tvAboutHotel.setText(description.getValue());
            }
        }

    }

    public void bindAmentities(List<String> result) {

        if (result != null && result.size() > 0) {
            amentityEntList = new ArrayList<>();

            for (String amenity : result) {

                amentityEntList.add(new AmentityEnt(Utils.getIcon(amenity), amenity));
            }

            adapterAmenities.clearList();
            gvAmentities.setAdapter(adapterAmenities);
            adapterAmenities.addAll(amentityEntList);
            adapterAmenities.notifyDataSetChanged();
        } else {
            llAmentities.setVisibility(View.GONE);
            BtnViewAll.setVisibility(View.GONE);
//            viewSepratorAmenties.setVisibility(View.GONE);
        }


    }
}
