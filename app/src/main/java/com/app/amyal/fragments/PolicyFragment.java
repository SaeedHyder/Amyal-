package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.amyal.R;
import com.app.amyal.entities.ContentModel;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by waq on 11/21/2017.
 */

public class PolicyFragment extends BaseFragment {

    @BindView(R.id.tv_body)
    AnyTextView tvBody;
    Unbinder unbinder;

    public static PolicyFragment newInstance() {
        return new PolicyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_policy, container, false);

        getMainActivity().setBackground(R.drawable.sp_dark);


        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceHelper.enqueueCall(webService.GetContentByKey(AppConstants.privacypolicy), AppConstants.privacypolicy);

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.privacy_policy));
        titleBar.showBackButton();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        ContentModel contentModel = (ContentModel) result;
        tvBody.setText(Html.fromHtml(contentModel.getContentValue()));

    }

}
