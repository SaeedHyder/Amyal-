package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.amyal.R;
import com.app.amyal.entities.AmentityEnt;
import com.app.amyal.entities.CarList;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.Utils;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by waq on 12/6/2017.
 */

public class CarDetailFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    @BindView(R.id.slCar)
    SliderLayout slCar;
    @BindView(R.id.pagerIndicator)
    PagerIndicator pagerIndicator;
    @BindView(R.id.tvCarName)
    AnyTextView tvCarName;
    @BindView(R.id.tvRatting)
    AnyTextView tvRatting;
    @BindView(R.id.tvRattingInWord)
    AnyTextView tvRattingInWord;
    @BindView(R.id.CrCarRating)
    CustomRatingBar CrCarRating;
    @BindView(R.id.tvPrice)
    AnyTextView tvPrice;
    @BindView(R.id.tvPerDay)
    AnyTextView tvPerDay;
    @BindView(R.id.tvAboutRoom)
    AnyTextView1 tvAboutRoom;
    @BindView(R.id.tvReadmore)
    AnyTextView tvReadmore;
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
    @BindView(R.id.tvPick_upName)
    AnyTextView tvPickUpName;
    @BindView(R.id.tvPick_upAddress)
    AnyTextView tvPickUpAddress;
    @BindView(R.id.tvPick_upHours)
    AnyTextView tvPickUpHours;
    @BindView(R.id.tvDrop_offName)
    AnyTextView tvDropOffName;
    @BindView(R.id.tvDrop_offAddress)
    AnyTextView tvDropOffAddress;
    @BindView(R.id.tvDrop_offHours)
    AnyTextView tvDropOffHours;
    @BindView(R.id.txtCarType)
    AnyTextView txtCarType;
    @BindView(R.id.txtAirCondition)
    AnyTextView txtAirCondition;
    @BindView(R.id.txtTransmission)
    AnyTextView txtTransmission;
    @BindView(R.id.txtFuelType)
    AnyTextView txtFuelType;
    @BindView(R.id.llReviews)
    LinearLayout llReviews;
    @BindView(R.id.llUser1)
    LinearLayout llUser1;
    @BindView(R.id.llUser2)
    LinearLayout llUser2;

    private List<AmentityEnt> amentityEntList;
    private ArrayListAdapter<AmentityEnt> adapter;

    CarList carListModel;

    public static HotelDetailFragment newInstance() {
        return new HotelDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        adapter = new ArrayListAdapter<>(getDockActivity(), new BinderAmentities(getDockActivity(), prefHelper));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_detail, container, false);

        getMainActivity().setBackground(R.drawable.bg_car);

        unbinder = ButterKnife.bind(this, view);

        if (getArguments() != null) {
            carListModel = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.CarListModel), CarList.class);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (carListModel != null) {

            setImageGallery();

            tvCarName.setText(carListModel.getCarName());
            tvAboutRoom.setText(carListModel.getDescription());

            tvPickUpName.setText(carListModel.getCarDetailsEnt().getPickUpLocationDetails().getName());
            tvPickUpAddress.setText(carListModel.getCarDetailsEnt().getPickUpLocationDetails().getAddress());
            tvPickUpHours.setText(carListModel.getCarDetailsEnt().getPickUpLocationDetails().getOperatingHoursStart() + " - " + carListModel.getCarDetailsEnt().getPickUpLocationDetails().getOperatingHoursEnd());

            tvDropOffName.setText(carListModel.getCarDetailsEnt().getDropOffLocationDetails().getName());
            tvDropOffAddress.setText(carListModel.getCarDetailsEnt().getDropOffLocationDetails().getAddress());
            tvDropOffHours.setText(carListModel.getCarDetailsEnt().getDropOffLocationDetails().getOperatingHoursStart() + " - " + carListModel.getCarDetailsEnt().getDropOffLocationDetails().getOperatingHoursEnd());

            txtCarType.setText(carListModel.getCarType());
            txtAirCondition.setText(carListModel.getAirConditioned());
            txtFuelType.setText(carListModel.getFuelType());
            txtTransmission.setText(carListModel.getTransmission());

            tvPrice.setText(carListModel.getCurrencyCode() + " " + carListModel.getTotalAmount());

            if (carListModel.getTotalReviewCount() != null && carListModel.getTotalReviewCount().length() > 0 && Integer.parseInt(carListModel.getTotalReviewCount()) >= 10 && carListModel.getRating() != null
                    && carListModel.getRating().length() > 0) {
                CrCarRating.setVisibility(View.VISIBLE);

                CrCarRating.setScore(Integer.parseInt(carListModel.getRating()));

                String ratting = Utils.getTextRatting(Float.parseFloat(carListModel.getRating()));
                if (ratting.length() > 0) {
                    tvRattingInWord.setVisibility(View.VISIBLE);
                    tvRattingInWord.setText(carListModel.getRating() + " " + ratting);
                } else {
                    tvRattingInWord.setVisibility(View.GONE);
                }

            } else {
                CrCarRating.setVisibility(View.GONE);
                tvRattingInWord.setVisibility(View.GONE);
            }


            if (carListModel.getTopReviews() != null && carListModel.getTopReviews().size() > 0) {

                llReviews.setVisibility(View.VISIBLE);

                if (carListModel.getTopReviews().size() >= 2) {
                    BtnViewAll.setVisibility(View.VISIBLE);
                    llUser1.setVisibility(View.VISIBLE);
                    llUser2.setVisibility(View.VISIBLE);

                    tvUserName1.setText(carListModel.getTopReviews().get(0).getUserName());
                    //tvUser1Date.setText(carListModel.getTopReviews().get(0).getDate());
                    tvUser1Date.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMDHMS, AppConstants.DateFormat_DMY3, carListModel.getTopReviews().get(0).getDate()));
                    tvUser1Review.setText(carListModel.getTopReviews().get(0).getReview());
                    CrUser1Rating.setScore(Float.parseFloat(carListModel.getTopReviews().get(0).getRating()));

                    if (carListModel.getTopReviews().get(0).getUserImage() != null && carListModel.getTopReviews().get(0).getUserImage().length() > 0) {
                        Picasso.with(getDockActivity())
                                .load(carListModel.getTopReviews().get(0).getUserImage())
                                .into(ivUser1);
                    }

                    tvUserName2.setText(carListModel.getTopReviews().get(1).getUserName());
                    //tvUser2Date.setText(carListModel.getTopReviews().get(1).getDate());
                    tvUser2Date.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMDHMS, AppConstants.DateFormat_DMY3, carListModel.getTopReviews().get(1).getDate()));
                    tvUser2Review.setText(carListModel.getTopReviews().get(1).getReview());
                    CrUser2Rating.setScore(Float.parseFloat(carListModel.getTopReviews().get(1).getRating()));


                    if (carListModel.getTopReviews().get(1).getUserImage() != null && carListModel.getTopReviews().get(1).getUserImage().length() > 0) {
                        Picasso.with(getDockActivity())
                                .load(carListModel.getTopReviews().get(1).getUserImage())
                                .into(ivUser2);
                    }

                } else {
                    BtnViewAll.setVisibility(View.GONE);
                    llUser2.setVisibility(View.GONE);

                    if (carListModel.getTopReviews().size() > 0) {

                        tvUserName1.setText(carListModel.getTopReviews().get(0).getUserName());
                        //tvUser1Date.setText(carListModel.getTopReviews().get(0).getDate());
                        tvUser1Date.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMDHMS, AppConstants.DateFormat_DMY3, carListModel.getTopReviews().get(0).getDate()));
                        tvUser1Review.setText(carListModel.getTopReviews().get(0).getReview());
                        CrUser1Rating.setScore(Float.parseFloat(carListModel.getTopReviews().get(0).getRating()));

                        if (carListModel.getTopReviews().get(0).getUserImage() != null && carListModel.getTopReviews().get(0).getUserImage().length() > 0) {
                            Picasso.with(getDockActivity())
                                    .load(carListModel.getTopReviews().get(0).getUserImage())
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

    }

    /*public void bindAmentities(ArrayList<AmentityEnt> result) {

        amentityEntList = new ArrayList<>();
        amentityEntList.add(new AmentityEnt("drawable://" + R.drawable.peaople, "05"));
        amentityEntList.add(new AmentityEnt("drawable://" + R.drawable.bags, "02"));
        amentityEntList.add(new AmentityEnt("drawable://" + R.drawable.doors, "04"));
        amentityEntList.add(new AmentityEnt("drawable://" + R.drawable.ac, "AC"));


        adapter.clearList();
        gvAmentities.setAdapter(adapter);
        adapter.addAll(amentityEntList);
        adapter.notifyDataSetChanged();

    }*/


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.hire_car));
        titleBar.showBackButton();
    }

    private void setImageGallery() {

        slCar.removeAllSliders();
        ArrayList<String> arrayList = new ArrayList<String>();

        arrayList.add(0, carListModel.getImage());

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

            slCar.addSlider(textSliderView);
        }

        slCar.setPresetTransformer(SliderLayout.Transformer.Accordion);
        slCar.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slCar.setCustomAnimation(new DescriptionAnimation());
        slCar.addOnPageChangeListener(this);
        slCar.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        slCar.stopAutoCycle();
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

                if (carListModel.getTopReviews() != null && carListModel.getTopReviews().size() > 0) {

                    HotelReviewsFragment hotelReviewsFragment = new HotelReviewsFragment();
                    hotelReviewsFragment.setisHotel(2);
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.CarListModel, new Gson().toJson(carListModel));
                    hotelReviewsFragment.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(hotelReviewsFragment, HotelReviewsFragment.class.getSimpleName());
                }else{
                    UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.no_reviews));
                }


                break;

            case R.id.btnReserve:

                if (prefHelper.isLogin()) {
                    CarAddInformation carAddInformation = new CarAddInformation();
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.CarListModel, new Gson().toJson(carListModel));
                    carAddInformation.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(carAddInformation, CarAddInformation.class.getSimpleName());
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
