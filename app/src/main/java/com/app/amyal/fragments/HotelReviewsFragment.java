package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.amyal.R;
import com.app.amyal.entities.CarList;
import com.app.amyal.entities.HotelListEnt;
import com.app.amyal.entities.MyReviewsEnt;
import com.app.amyal.entities.TopReview;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.retrofit.GsonFactory;
import com.app.amyal.ui.adapters.ArrayListAdapter;
import com.app.amyal.ui.binders.BinderHotelReviews;
import com.app.amyal.ui.binders.MyReviewsBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by waq on 12/4/2017.
 */

public class HotelReviewsFragment extends BaseFragment {


    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.lv_myreviews)
    ListView lvMyreviews;
    Unbinder unbinder;

    private List<MyReviewsEnt> myReviewsEnts;
    private ArrayListAdapter<MyReviewsEnt> adapter;

    int tabPosition = 1;

    List<TopReview> topReviews;

    CarList carListModel;

    public static HotelReviewsFragment newInstance() {
        return new HotelReviewsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new BinderHotelReviews(getDockActivity(), prefHelper));

        if (getArguments() != null) {
            carListModel = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.CarListModel), CarList.class);
            if(carListModel!= null && carListModel.getTopReviews()!= null)
                topReviews = carListModel.getTopReviews();
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.reviews));
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

        setBackgroundImage(tabPosition);

        if (topReviews != null && topReviews.size() > 0)
            bindData(topReviews);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setisHotel(int tabPosition) {
        this.tabPosition = tabPosition;
    }

    public void setReviews(List<TopReview> topReviews) {
        this.topReviews = topReviews;
    }

    public void setBackgroundImage(int current_position) {
        if (current_position == 1) {
            getMainActivity().setBackground(R.drawable.bg_hotel);
        } else if (current_position == 2) {
            getMainActivity().setBackground(R.drawable.bg_car);
        } else if (current_position == 3) {
            getMainActivity().setBackground(R.drawable.sp_dark);
        }
    }

    public void bindData(List<TopReview> topReviews) {

        myReviewsEnts = new ArrayList<>();

        for (TopReview topReview : topReviews) {
            myReviewsEnts.add(new MyReviewsEnt(topReview.getUserName(), Float.parseFloat(topReview.getRating()), topReview.getReview(), topReview.getDate()));
            //myReviewsEnts.add(new MyReviewsEnt("Mark Rufalo", 4.0f, "Lorem Ipsum is simply dummy text Lorem Ipsum is simply dummy text Lorem Ipsum is simply dummy text", "2017-08-23 13:20:22"));
        }

        if (myReviewsEnts.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvMyreviews.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvMyreviews.setVisibility(View.VISIBLE);
        }

        adapter.clearList();
        lvMyreviews.setAdapter(adapter);
        adapter.addAll(myReviewsEnts);
        adapter.notifyDataSetChanged();

    }

}
