package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.amyal.R;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ManageBookingFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {

    Unbinder unbinder;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;

    int current_position = 0;

    public static ManageBookingFragment newInstance() {
        return new ManageBookingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FlightManageBookFragment(), "FlightManageBookFragment");
        adapter.addFragment(new HotelManageBokFragment(), "HotelManageBokFragment");
        adapter.addFragment(new CarManageBokFragment(), "CarManageBokFragment");
        adapter.addFragment(new PackageManageBokFragment(), "PackageManageBokFragment");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        unbinder = ButterKnife.bind(this, view);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout)view. findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        tabLayout.setOnTabSelectedListener(this);

        if (getArguments() != null) {
            current_position = getArguments().getInt(AppConstants.tabPosition);
        }

        setBackgroundImage(current_position);
        viewPager.setCurrentItem(current_position);

        return view;
    }


    public void setBackgroundImage(int current_position){
        if(current_position == 0){
            getMainActivity().setBackground(R.drawable.bg_flight);
        }
        else if(current_position == 1){
            getMainActivity().setBackground(R.drawable.bg_hotel);
        }
        else if(current_position == 2){
            getMainActivity().setBackground(R.drawable.bg_car);
        }
        else if(current_position == 3){
            getMainActivity().setBackground(R.drawable.sp_dark);
        }
    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(getDockActivity()).inflate(R.layout.history_tab, null).findViewById(R.id.tab);
        tabOne.setText(getString(R.string.flights));
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.flightico, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getDockActivity()).inflate(R.layout.history_tab, null).findViewById(R.id.tab);
        tabTwo.setText(R.string.hotels);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.hotelico, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(getDockActivity()).inflate(R.layout.history_tab, null).findViewById(R.id.tab);
        tabThree.setText(R.string.cars);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.carico, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(getDockActivity()).inflate(R.layout.history_tab, null).findViewById(R.id.tab);
        tabFour.setText(R.string.specialpackages);
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.spico, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.manage_bookings));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        setBackgroundImage(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
