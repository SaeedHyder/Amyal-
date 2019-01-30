package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.amyal.R;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.ui.views.AnyEditTextView;
import com.app.amyal.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by waq on 11/22/2017.
 */

public class ContactUsFragment extends BaseFragment {

    @BindView(R.id.edtFeedBack)
    AnyEditTextView edtFeedBack;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    Unbinder unbinder;
    private boolean isSetting;

    public static ContactUsFragment newInstance(boolean isSetting) {
        ContactUsFragment contactUsFragment = new ContactUsFragment();
        contactUsFragment.isSetting = isSetting;
        return contactUsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contactus, container, false);

        getMainActivity().setBackground(R.drawable.sp_dark);

        unbinder = ButterKnife.bind(this, view);
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
        titleBar.setSubHeading(getString(R.string.contactus));
        if (!isSetting)
            titleBar.showMenuButton();
        else
            titleBar.showBackButton();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {

        if (edtFeedBack.getText().toString().length() > 0) {
            getDockActivity().popBackStackTillEntry(0);
            getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.class.getSimpleName());
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.feedback_sent));
            /*getDockActivity().popBackStackTillEntry(0);
            getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.class.getSimpleName());*/
        } else {
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.feedback_empty));
        }

    }
}
