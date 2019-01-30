package com.app.amyal.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.app.amyal.R;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.TitleBar;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by waq on 11/22/2017.
 */

public class SettingsFragment extends BaseFragment {

    @BindView(R.id.txtNotification)
    AnyTextView txtNotification;
    @BindView(R.id.toggleNotification)
    ToggleButton toggleNotification;
    @BindView(R.id.llChangePassword)
    LinearLayout llChangePassword;
    @BindView(R.id.llPolicy)
    LinearLayout llPolicy;
    Unbinder unbinder;
    @BindView(R.id.llManageBookings)
    LinearLayout llManageBookings;
    @BindView(R.id.llLanguageCurrency)
    LinearLayout llLanguageCurrency;
    @BindView(R.id.llAbout)
    LinearLayout llAbout;
    @BindView(R.id.ivFb)
    ImageView ivFb;
    @BindView(R.id.ivTw)
    ImageView ivTw;
    @BindView(R.id.ivGoogle)
    ImageView ivGoogle;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        getMainActivity().setBackground(R.drawable.sp_dark);
        unbinder = ButterKnife.bind(this, view);
        checkSocialLogin();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //serviceHelper.enqueueCall(webService.aboutUs(prefHelper.getMerchantId(), AppConstants.AboutUs), WebServiceConstants.aboutus);

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.settings));
        titleBar.showMenuButton();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    //    ShareDialog shareDialog = new ShareDialog(this);
    @OnClick({R.id.llManageBookings, R.id.llLanguageCurrency, R.id.llAbout, R.id.ivFb, R.id.ivTw, R.id.ivGoogle, R.id.llChangePassword, R.id.llPolicy, R.id.llTermsConditions,
            R.id.llNotification, R.id.llContactUs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llChangePassword:
                getDockActivity().replaceDockableFragment(ChangePasswordFragment.newInstance(), "ChangePasswordFragment");
                break;
            case R.id.llPolicy:
                getDockActivity().replaceDockableFragment(PolicyFragment.newInstance(), "PolicyFragment");
                break;
            case R.id.llManageBookings:
                getDockActivity().replaceDockableFragment(ManageBookingFragment.newInstance(), ManageBookingFragment.class.getSimpleName());
                break;
            case R.id.llLanguageCurrency:
                getDockActivity().replaceDockableFragment(LanguageCurrencyFragment.newInstance(), "LanguageCurrencyFragment");
                break;
            case R.id.llAbout:
                getDockActivity().replaceDockableFragment(AboutFragment.newInstance(), "AboutFragment");
                break;
            case R.id.ivFb:
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://www.amyal.com/"))
                        .setQuote("Please use Amyaal")
                        .build();
//
//                    shareDialog.show(content);


                ShareDialog shareDialog = new ShareDialog(this);
                shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);

                break;
            case R.id.ivTw:
                shareContent("com.twitter.android", "Amyaal", "https://www.amyal.com/");
//                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented_in_beta));
                break;
            case R.id.ivGoogle:
                shareContent("com.google.android.apps.plus", "Amyaal", "https://www.amyal.com/");
//                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.will_be_implemented_in_beta));
                break;
            case R.id.llTermsConditions:
                getDockActivity().replaceDockableFragment(TermsConditionFragment.newInstance(), "TermsConditionFragment");
                break;

            case R.id.llContactUs:
                getDockActivity().replaceDockableFragment(ContactUsFragment.newInstance(true), ContactUsFragment.class.getSimpleName());
                break;

            case R.id.llNotification:
                getDockActivity().replaceDockableFragment(NotificationsFragment.newInstance(), "NotificationsFragment");
                break;
        }
    }

    private void shareContent(String application, String title, String description) {
        Intent intent = getMainActivity().getPackageManager().getLaunchIntentForPackage(application);
        if (intent != null) {

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, description);
            sharingIntent.setPackage(application);
            startActivity(Intent.createChooser(sharingIntent, title));


        } else {
            Toast.makeText(getMainActivity(), getResources().getString(R.string.no_app_found), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkSocialLogin() {
        if (prefHelper.getUser().getUser().getIsSocialLogin())
            llChangePassword.setVisibility(View.GONE);
    }
}
