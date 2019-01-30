package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.amyal.R;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by waq on 11/20/2017.
 */

public class WelcomeFragment extends BaseFragment {

    @BindView(R.id.llHotel)
    LinearLayout llHotel;
    @BindView(R.id.llFlight)
    LinearLayout llFlight;
    @BindView(R.id.llSp)
    LinearLayout llSp;
    @BindView(R.id.llCarRent)
    LinearLayout llCarRent;
    @BindView(R.id.txtLogin)
    AnyTextView txtLogin;
    @BindView(R.id.txtRegister)
    AnyTextView txtRegister;
    Unbinder unbinder;

    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);


        getMainActivity().setBackground(R.drawable.sp_light);
        getMainActivity().refreshSideMenu();

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @OnClick({R.id.llHotel, R.id.llFlight, R.id.llSp, R.id.llCarRent, R.id.txtLogin, R.id.txtRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llHotel:

                HomeFragment homeFragment = new HomeFragment();
                Bundle orderBundle = new Bundle();
                orderBundle.putInt(AppConstants.tabPosition,1);
                homeFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(homeFragment, "HomeFragment");

                break;

            case R.id.llFlight:
                homeFragment = new HomeFragment();
                orderBundle = new Bundle();
                orderBundle.putInt(AppConstants.tabPosition,0);
                homeFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(homeFragment, "HomeFragment");
                break;

            case R.id.llSp:
                homeFragment = new HomeFragment();
                orderBundle = new Bundle();
                orderBundle.putInt(AppConstants.tabPosition,3);
                homeFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(homeFragment, "HomeFragment");
                break;

            case R.id.llCarRent:
                homeFragment = new HomeFragment();
                orderBundle = new Bundle();
                orderBundle.putInt(AppConstants.tabPosition,2);
                homeFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(homeFragment, "HomeFragment");
                break;

            case R.id.txtLogin:
                getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                break;

            case R.id.txtRegister:
                getDockActivity().replaceDockableFragment(SignupFragment.newInstance(), "SignupFragment");
                break;
        }
    }
}
