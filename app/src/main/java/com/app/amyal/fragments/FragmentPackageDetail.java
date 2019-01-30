package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.app.amyal.R;
import com.app.amyal.entities.FlightEnt;
import com.app.amyal.entities.FlightsList;
import com.app.amyal.entities.Hotel;
import com.app.amyal.entities.Itinerary;
import com.app.amyal.entities.PackageDetailsEnt;
import com.app.amyal.entities.PackageItineraryEnt;
import com.app.amyal.entities.PackageListEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.Utils;
import com.app.amyal.helpers.DateHelper;
import com.app.amyal.helpers.DialogHelper;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.retrofit.GsonFactory;
import com.app.amyal.ui.adapters.ArrayListAdapter;
import com.app.amyal.ui.adapters.FilterableListAdapter;
import com.app.amyal.ui.binders.BinderPackageItineraryItem;
import com.app.amyal.ui.binders.BinderSelectHotels;
import com.app.amyal.ui.binders.SelectFlightsBinder;
import com.app.amyal.ui.binders.itemPackageInclusion;
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
import com.google.common.base.Function;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by khan_muhammad on 12/7/2017.
 */

public class FragmentPackageDetail extends BaseFragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    @BindView(R.id.tvDays)
    AnyTextView tvDays;
    @BindView(R.id.tvNights)
    AnyTextView tvNights;
    @BindView(R.id.slPackage)
    SliderLayout slPackage;
    @BindView(R.id.pagerIndicator)
    PagerIndicator pagerIndicator;
    @BindView(R.id.tvRatting)
    AnyTextView tvRatting;
    @BindView(R.id.tvRattingInWord)
    AnyTextView tvRattingInWord;
    @BindView(R.id.CrPackageRating)
    CustomRatingBar CrPackageRating;
    @BindView(R.id.tvPrice)
    AnyTextView tvPrice;
    @BindView(R.id.tvPerDay)
    AnyTextView tvPerDay;
    @BindView(R.id.tvAboutRoom)
    AnyTextView1 tvAboutRoom;
    @BindView(R.id.tvReadmore)
    AnyTextView tvReadmore;
    @BindView(R.id.lvHotels)
    ListView lvHotels;
    @BindView(R.id.lvItinerary)
    ListView lvItinerary;
    @BindView(R.id.btnAddReview)
    AnyTextView btnAddReview;
    @BindView(R.id.ivUser1)
    ImageView ivUser1;
    @BindView(R.id.tvUserName1)
    AnyTextView tvUserName1;
    @BindView(R.id.CrUser1Rating)
    CustomRatingBar CrUser1Rating;
    @BindView(R.id.tvUser1Date)
    AnyTextView tvUser1Date;
    @BindView(R.id.tvUser1Review)
    AnyTextView tvUser1Review;
    @BindView(R.id.ivUser2)
    ImageView ivUser2;
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
    @BindView(R.id.btnReserve)
    Button btnReserve;
    Unbinder unbinder;
    @BindView(R.id.lvInclusions)
    ListView lvInclusions;
    @BindView(R.id.llUser1)
    LinearLayout llUser1;
    @BindView(R.id.llUser2)
    LinearLayout llUser2;
    @BindView(R.id.llReviews)
    LinearLayout llReviews;
    @BindView(R.id.tvPackageName)
    AnyTextView1 tvPackageName;
    @BindView(R.id.ivItinerary)
    AnyTextView ivItinerary;
    @BindView(R.id.lvFlights)
    ListView lvFlights;
    @BindView(R.id.llFlights)
    LinearLayout llFlights;
    PackageDetailsEnt packageDetailsEnt;
    PackageListEnt packageListEnt;
    private List<PackageItineraryEnt> packageItineraryEnts;
    private ArrayListAdapter<PackageItineraryEnt> packageItineraryAdapter;
    private ArrayListAdapter<String> itempackageInclusionAdapter;
    private ArrayListAdapter<Hotel> adapter;
    private List<FlightEnt> flightEntList;
    private FilterableListAdapter<FlightEnt> flightadapter;


    public static FragmentPackageDetail newInstance() {
        return new FragmentPackageDetail();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        packageItineraryAdapter = new ArrayListAdapter<>(getDockActivity(), new BinderPackageItineraryItem(getDockActivity(), prefHelper));
        itempackageInclusionAdapter = new ArrayListAdapter<>(getDockActivity(), new itemPackageInclusion(getDockActivity(), prefHelper));
        adapter = new ArrayListAdapter<>(getDockActivity(), new BinderSelectHotels(getDockActivity(), prefHelper));
        flightadapter = new FilterableListAdapter<>(getDockActivity(), new SelectFlightsBinder(getDockActivity(), prefHelper));

        if (getArguments() != null) {
            packageDetailsEnt = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.packageDetailsEnt), PackageDetailsEnt.class);
            packageListEnt = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.PackageListEnt), PackageListEnt.class);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pacakge_detail, container, false);

        getMainActivity().setBackground(R.drawable.sp_dark);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (packageDetailsEnt != null) {
            PacakgeDetail();
            setData();

            if (packageListEnt.getFlightsList() != null && packageListEnt.getFlightsList().size() > 0) {
                llFlights.setVisibility(View.VISIBLE);
                bindData(packageListEnt.getFlightsList());
            } else
                llFlights.setVisibility(View.GONE);
        }

        /*lvItinerary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDockActivity().replaceDockableFragment(PackageDayDetailsFragment.newInstance(), PackageDayDetailsFragment.class.getSimpleName());
            }
        });*/

    }


    public void bindData(List<FlightsList> result) {

        String CurCode = "AED";

        if (prefHelper.isLogin()) {
            CurCode = prefHelper.getUser().getUser().getCurrencyCode();
        }


        flightEntList = new ArrayList<>();

        for (FlightsList flightsList : result) {
            flightEntList.add(new FlightEnt(flightsList.getThumb(), flightsList.getAirlineName(), flightsList.getRating(), flightsList.getTotalDuration(), Integer.parseInt(flightsList.getNoOfStops()), flightsList.getCabinType() + " Class", (flightsList.getAmount()), flightsList.getFlightStop(), CurCode));
        }

        flightadapter.clearList();
        flightadapter = new FilterableListAdapter<FlightEnt>(getDockActivity(), new ArrayList<FlightEnt>(), new SelectFlightsBinder(getDockActivity(), prefHelper),
                new Function<FlightEnt, String>() {
                    @Override
                    @Nullable
                    public String apply(
                            @Nullable FlightEnt arg0) {
                        return arg0.getFlightName();
                    }
                });
        lvFlights.setAdapter(flightadapter);
        flightadapter.addAll(flightEntList);
        adapter.notifyDataSetChanged();

    }

    public void setData() {

        if (packageListEnt != null) {

            if (packageListEnt.getDescription() != null && packageListEnt.getDescription().length() > 0)
                tvAboutRoom.setText(packageListEnt.getDescription());
            else {
                tvReadmore.setVisibility(View.GONE);
                tvAboutRoom.setText(R.string.no_discription);
            }

            if (packageDetailsEnt.getPackageDetail().getDuration() != null && packageDetailsEnt.getPackageDetail().getDuration().length() > 0) {
                String duration = packageDetailsEnt.getPackageDetail().getDuration();
                duration = duration.substring(duration.indexOf("P") + 1, duration.indexOf("D"));
                tvDays.setText(duration + " " + getString(R.string.days));
            }


            tvPackageName.setSelected(true);
            tvPackageName.setText(packageListEnt.getPackageName());
            tvPrice.setText(packageListEnt.getCurrencyCode() + " " + packageListEnt.getTotalAmount());

            if (packageListEnt.getTotalReviewCount() != null && packageListEnt.getTotalReviewCount().length() > 0 && Integer.parseInt(packageListEnt.getTotalReviewCount()) >= 10) {
                CrPackageRating.setScore(Integer.parseInt(packageListEnt.getRating()));
                tvRatting.setVisibility(View.VISIBLE);
                tvRattingInWord.setVisibility(View.VISIBLE);
                String ratting = Utils.getTextRatting(Float.parseFloat(packageListEnt.getRating()));
                if (ratting.length() > 0) {
                    tvRatting.setText(packageListEnt.getRating() + " ");
                    tvRattingInWord.setText(ratting);
                }
            } else {
                CrPackageRating.setVisibility(View.GONE);
                tvRatting.setVisibility(View.GONE);
                tvRattingInWord.setVisibility(View.GONE);
            }
        }

        if (packageDetailsEnt.getHotels() != null && packageDetailsEnt.getHotels().size() > 0) {
            bindHotels(packageDetailsEnt.getHotels());
            Utils.justifyListViewHeightBasedOnChildren(lvHotels);
        }

        if (packageDetailsEnt.getPackageDetail().getProductTypes() != null && packageDetailsEnt.getPackageDetail().getProductTypes().size() > 0) {
            bindHotelInclusions(packageDetailsEnt.getPackageDetail().getProductTypes());
            Utils.justifyListViewHeightBasedOnChildren(lvInclusions);
        }

        if (packageDetailsEnt.getPackageDetail().getItineraries() != null && packageDetailsEnt.getPackageDetail().getItineraries().size() > 0) {
            bindPackageItinerary(packageDetailsEnt.getPackageDetail().getItineraries());
            Utils.justifyListViewHeightBasedOnChildren(lvItinerary);
        } else {
            lvItinerary.setVisibility(View.GONE);
            ivItinerary.setVisibility(View.GONE);
        }

        /*if (packageDetailsEnt.getPackageDetail().getImages() != null && packageDetailsEnt.getPackageDetail().getImages().size() > 0)
            setImageGallery();*/

        if (packageListEnt.getImages() != null && packageListEnt.getImages().size() > 0)
            setImageGallery();

    }

    public void PacakgeDetail() {

        if (packageListEnt.getTopReviews() != null && packageListEnt.getTopReviews().size() > 0) {

            llReviews.setVisibility(View.VISIBLE);

            if (packageListEnt.getTopReviews().size() >= 2) {
                BtnViewAll.setVisibility(View.VISIBLE);
                llUser1.setVisibility(View.VISIBLE);
                llUser2.setVisibility(View.VISIBLE);

                tvUserName1.setText(packageListEnt.getTopReviews().get(0).getUserName());
                //tvUser1Date.setText(hotelListModel.getTopReviews().get(0).getDate());
                tvUser1Date.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMDHMS, AppConstants.DateFormat_DMY3, packageListEnt.getTopReviews().get(0).getDate()));
                tvUser1Review.setText(packageListEnt.getTopReviews().get(0).getReview());
                CrUser1Rating.setScore(Float.parseFloat(packageListEnt.getTopReviews().get(0).getRating()));

                if (packageListEnt.getTopReviews().get(0).getUserImage() != null && packageListEnt.getTopReviews().get(0).getUserImage().length() > 0) {
                    Picasso.with(getDockActivity())
                            .load(packageListEnt.getTopReviews().get(0).getUserImage())
                            .into(ivUser1);
                }

                tvUserName2.setText(packageListEnt.getTopReviews().get(1).getUserName());
                //tvUser2Date.setText(hotelListModel.getTopReviews().get(1).getDate());
                tvUser2Date.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMDHMS, AppConstants.DateFormat_DMY3, packageListEnt.getTopReviews().get(1).getDate()));
                tvUser2Review.setText(packageListEnt.getTopReviews().get(1).getReview());
                CrUser2Rating.setScore(Float.parseFloat(packageListEnt.getTopReviews().get(1).getRating()));


                if (packageListEnt.getTopReviews().get(1).getUserImage() != null && packageListEnt.getTopReviews().get(1).getUserImage().length() > 0) {
                    Picasso.with(getDockActivity())
                            .load(packageListEnt.getTopReviews().get(1).getUserImage())
                            .into(ivUser2);
                }

            } else {
                BtnViewAll.setVisibility(View.GONE);
                llUser2.setVisibility(View.GONE);

                if (packageListEnt.getTopReviews().size() > 0) {

                    tvUserName1.setText(packageListEnt.getTopReviews().get(0).getUserName());
                    //tvUser1Date.setText(hotelListModel.getTopReviews().get(0).getDate());
                    tvUser1Date.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMDHMS, AppConstants.DateFormat_DMY3, packageListEnt.getTopReviews().get(0).getDate()));
                    tvUser1Review.setText(packageListEnt.getTopReviews().get(0).getReview());
                    CrUser1Rating.setScore(Float.parseFloat(packageListEnt.getTopReviews().get(0).getRating()));

                    if (packageListEnt.getTopReviews().get(0).getUserImage() != null && packageListEnt.getTopReviews().get(0).getUserImage().length() > 0) {
                        Picasso.with(getDockActivity())
                                .load(packageListEnt.getTopReviews().get(0).getUserImage())
                                .into(ivUser1);
                    }

                } else {
                    llUser1.setVisibility(View.GONE);
                    llReviews.setVisibility(View.GONE);
                }

            }

        } else {

            llReviews.setVisibility(View.GONE);

        }

    }

    public void bindPackageItinerary(List<Itinerary> result) {

        packageItineraryEnts = new ArrayList<>();

        for (int i = 0; i < result.size(); i++) {

            String iconPath = "";
            boolean isLastDay = false;

            if (i == result.size() - 1) {
                isLastDay = true;
            }

            if (i == 0) {
                iconPath = "drawable://" + R.drawable.flight_1;
            } else {
                iconPath = "drawable://" + R.drawable.location;
            }

            packageItineraryEnts.add(new PackageItineraryEnt(iconPath, result.get(i).getDay() + result.get(i).getTitle(), result.get(i).getDescription(), isLastDay));
        }

        packageItineraryAdapter.clearList();
        lvItinerary.setAdapter(packageItineraryAdapter);
        packageItineraryAdapter.addAll(packageItineraryEnts);
        packageItineraryAdapter.notifyDataSetChanged();

    }

    public void bindHotelInclusions(List<String> result) {

        itempackageInclusionAdapter.clearList();
        lvInclusions.setAdapter(itempackageInclusionAdapter);
        itempackageInclusionAdapter.addAll(result);
        itempackageInclusionAdapter.notifyDataSetChanged();

    }

    public void bindHotels(List<Hotel> result) {

        adapter.clearList();
        lvHotels.setAdapter(adapter);
        adapter.addAll(result);
        adapter.notifyDataSetChanged();

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.specialpackages));
        titleBar.showBackButton();
    }

    private void setImageGallery() {

        slPackage.removeAllSliders();

        ArrayList<String> arrayList = new ArrayList<String>();

        for (int i = 0; i < packageListEnt.getImages().size(); i++) {
            arrayList.add(i, packageListEnt.getImages().get(i).toString());
        }

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

            slPackage.addSlider(textSliderView);
        }

        slPackage.setPresetTransformer(SliderLayout.Transformer.Accordion);
        slPackage.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slPackage.setCustomAnimation(new DescriptionAnimation());
        slPackage.addOnPageChangeListener(this);
        slPackage.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        slPackage.stopAutoCycle();
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

    @OnClick({R.id.tvReadmore, R.id.btnAddReview, R.id.BtnViewAll, R.id.btnReserve})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tvReadmore:
                tvAboutRoom.setMaxLines(15);
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
                hotelReviewsFragment.setisHotel(3);
                hotelReviewsFragment.setReviews(packageListEnt.getTopReviews());
                getDockActivity().replaceDockableFragment(hotelReviewsFragment, HotelReviewsFragment.class.getSimpleName());
                break;

            case R.id.btnReserve:

                if (prefHelper.isLogin()) {
                    PackageAddInfoFragment packageAddInfoFragment = new PackageAddInfoFragment();
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.packageDetailsEnt, new Gson().toJson(packageDetailsEnt));
                    orderBundle.putString(AppConstants.PackageListEnt, new Gson().toJson(packageListEnt));
                    packageAddInfoFragment.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(packageAddInfoFragment, PackageAddInfoFragment.class.getSimpleName());
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

}
