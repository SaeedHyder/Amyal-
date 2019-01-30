package com.app.amyal.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.amyal.R;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.DialogHelper;
import com.app.amyal.ui.dialogs.DialogFactory;
import com.app.amyal.ui.views.AnyEditTextView;
import com.app.amyal.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by waq on 11/21/2017.
 */

public class ForgotPasswordFragment extends BaseFragment {


    @BindView(R.id.edt_email)
    AnyEditTextView edtEmail;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    public static ForgotPasswordFragment newInstance() {
        return new ForgotPasswordFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_forgotpassword, container, false);

        getMainActivity().setBackground(R.drawable.sp_light);

        ButterKnife.bind(this, view);

        return view;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);

    }


    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {
        if (validate()) {
            serviceHelper.enqueueCall(webService.forgotPassword(edtEmail.getText().toString()), WebServiceConstants.forgotPassword);
        }

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag){
            case WebServiceConstants.forgotPassword:
                final DialogHelper dialogHelper = new DialogHelper(getMainActivity());
                dialogHelper.initForgotPasswordDialog(R.layout.dialog_forgotpassword, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialogHelper.hideDialog();
                        getDockActivity().popFragment();

                    }
                });
                dialogHelper.showDialog();
                break;
        }
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.forgot_password_));
    }

    private boolean validate() {
        if (edtEmail.getText().toString().trim().equals("") ||
                !Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
            edtEmail.setError(getResources().getString(R.string.email_error));
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
