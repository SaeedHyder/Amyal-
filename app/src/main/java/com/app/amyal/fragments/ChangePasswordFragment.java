package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.amyal.R;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.WebServiceConstants;
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

public class ChangePasswordFragment extends BaseFragment {


    @BindView(R.id.edtOldPassword)
    AnyEditTextView edtOldPassword;
    @BindView(R.id.edtNewPassword)
    AnyEditTextView edtNewPassword;
    @BindView(R.id.edtConfirmPassword)
    AnyEditTextView edtConfirmPassword;
    @BindView(R.id.btnDone)
    Button btnDone;
    Unbinder unbinder;

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

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
        titleBar.setSubHeading(getString(R.string.changepassword));
        titleBar.showBackButton();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    private boolean validated() {

        if (edtOldPassword.getText().toString().trim().equals(edtNewPassword.getText().toString().trim())) {
            edtOldPassword.setError(getString(R.string.password_different));
            return false;
        }

        if (edtOldPassword.getText().toString().trim().equals("") || edtOldPassword.getText().toString().length() < 6) {
            edtOldPassword.setError(getString(R.string.password_length_alert));
            return false;
        } else if (edtNewPassword.getText().toString().trim().equals("") || edtNewPassword.getText().toString().length() < 6) {
            edtNewPassword.setError(getString(R.string.password_length_alert));
            return false;
        } else if (edtConfirmPassword.getText().toString().trim().equals("") || edtConfirmPassword.getText().toString().length() < 6) {
            edtConfirmPassword.setError(getString(R.string.password_length_alert));
            return false;
        } else if (!edtNewPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
            edtConfirmPassword.setError(getString(R.string.confirm_pas_not_match));
            return false;
        } else {
            return true;
        }

    }

    @OnClick(R.id.btnDone)
    public void onViewClicked() {

        if (validated() && prefHelper.getUser() != null) {
            serviceHelper.enqueueCall(webService.updatePassword(edtOldPassword.getText().toString(),
                    edtNewPassword.getText().toString(),
                    edtConfirmPassword.getText().toString(),
                    "Bearer " + prefHelper.getUser().getAuthToken()),
                    WebServiceConstants.changePassword);
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.changePassword:
                UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.pasword_change_sucess));
                getDockActivity().popFragment();
                break;
        }
    }

}
