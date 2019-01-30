package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.amyal.R;
import com.app.amyal.entities.MyReviewsEnt;
import com.app.amyal.entities.TopReview;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.ui.adapters.ArrayListAdapter;
import com.app.amyal.ui.binders.MyReviewsBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by khan_muhammad on 11/23/2017.
 */

public class MyReviewsFragment extends BaseFragment {


    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.lv_myreviews)
    ListView lvMyreviews;
    Unbinder unbinder;
    private List<MyReviewsEnt> myReviewsEnts;
    private ArrayListAdapter<MyReviewsEnt> adapter;

    public static MyReviewsFragment newInstance() {
        return new MyReviewsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new MyReviewsBinder(getDockActivity(), prefHelper));
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.my_reviews));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myreviews, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceHelper.enqueueCall(webService.GetMyReviews("Bearer "+prefHelper.getUser().getAuthToken()), WebServiceConstants.GetReviews);

    }

      @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.GetReviews:
                bindData((List<TopReview>) result);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void bindData(List<TopReview> result) {

        myReviewsEnts = new ArrayList<>();

        for(TopReview topReview : result){
            myReviewsEnts.add(new MyReviewsEnt(topReview.getUserName(), Float.parseFloat(topReview.getRating()), topReview.getReview(), topReview.getDate()));
        }

        /*myReviewsEnts.add(new MyReviewsEnt("Port Adrino Hotel", 3.5f, "Lorem Ipsum is simply dummy text Lorem Ipsum is simply dummy text Lorem Ipsum is simply dummy text", "2017-08-23 13:20:22"));
        myReviewsEnts.add(new MyReviewsEnt("Nissan Days", 4.0f, "Lorem Ipsum is simply dummy text Lorem Ipsum is simply dummy text Lorem Ipsum is simply dummy text", "2017-08-23 13:20:22"));
        myReviewsEnts.add(new MyReviewsEnt("Port Adrino Hotel", 2.5f, "Lorem Ipsum is simply dummy text Lorem Ipsum is simply dummy text Lorem Ipsum is simply dummy text", "2017-08-23 13:20:22"));*/

        /*if (myReviewsEnts.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvMyreviews.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvMyreviews.setVisibility(View.VISIBLE);

        }*/

        adapter.clearList();
        lvMyreviews.setAdapter(adapter);
        adapter.addAll(myReviewsEnts);
        adapter.notifyDataSetChanged();

    }

}
