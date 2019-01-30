package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class TermsConditionFragment extends BaseFragment {

    @BindView(R.id.tv_body)
    TextView tvBody;
    Unbinder unbinder;

    public static TermsConditionFragment newInstance() {
        return new TermsConditionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms_conditions, container, false);

        getMainActivity().setBackground(R.drawable.sp_dark);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceHelper.enqueueCall(webService.GetContentByKey(AppConstants.TERMCONDITION), AppConstants.TERMCONDITION);

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.terms_conditions));
        titleBar.showRightButton(R.drawable.cross, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().popFragment();
            }
        });
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
