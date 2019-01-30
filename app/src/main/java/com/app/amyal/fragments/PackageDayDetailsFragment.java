package com.app.amyal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.amyal.R;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.TitleBar;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PackageDayDetailsFragment extends BaseFragment  implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    @BindView(R.id.slPlace)
    SliderLayout slPlace;
    @BindView(R.id.pagerIndicator)
    PagerIndicator pagerIndicator;
    @BindView(R.id.tvTitle)
    AnyTextView tvTitle;
    @BindView(R.id.tvDesc)
    AnyTextView tvDesc;
    Unbinder unbinder;

    public static PackageDayDetailsFragment newInstance() {
        Bundle args = new Bundle();

        PackageDayDetailsFragment fragment = new PackageDayDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pkg_day_detail, container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading("Day 1");
        titleBar.showBackButton();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setPlaceGallery();

    }

    private void setPlaceGallery() {

        slPlace.removeAllSliders();
        ArrayList<String> arrayList = new ArrayList<String>();

        arrayList.add(0, "http://www.asiawebdirect.com/media/images/hotels/451/642933.jpg");
        arrayList.add(1, "http://www.flyertravelex.com/data/frontImages/tours/tours_image/1440406519_tanjung_beach_langkawi_malaysia_peaceful_hd-wallpaper-1539723.jpg");
        arrayList.add(2, "http://www.asiawebdirect.com/media/images/hotels/451/642933.jpg");

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

            slPlace.addSlider(textSliderView);
        }

        slPlace.setPresetTransformer(SliderLayout.Transformer.Accordion);
        slPlace.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slPlace.setCustomAnimation(new DescriptionAnimation());
        slPlace.addOnPageChangeListener(this);
        slPlace.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        slPlace.stopAutoCycle();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
}