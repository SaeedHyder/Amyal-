package com.app.amyal.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.app.amyal.R;
import com.app.amyal.entities.BookingDetailEnt;
import com.app.amyal.entities.CarHistoryEnt;
import com.app.amyal.entities.Detail;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.Utils;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.DialogHelper;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.retrofit.GsonFactory;
import com.app.amyal.ui.adapters.ArrayListAdapter;
import com.app.amyal.ui.binders.BinderBookingItem;
import com.app.amyal.ui.dialogs.DialogFactory;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.CustomRatingBar;
import com.app.amyal.ui.views.TitleBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by khan_muhammad on 12/6/2017.
 */

public class CarHistoryDetailFragment extends BaseFragment {


    @BindView(R.id.ivCar)
    ImageView ivCar;
    @BindView(R.id.tvCarName)
    AnyTextView tvCarName;
    @BindView(R.id.tvRatting)
    AnyTextView tvRatting;
    @BindView(R.id.tvRattingInWord)
    AnyTextView tvRattingInWord;
    @BindView(R.id.CrRating)
    CustomRatingBar CrRating;
    @BindView(R.id.tvPrice)
    AnyTextView tvPrice;
    @BindView(R.id.tvTitleDetails)
    AnyTextView tvTitleDetails;
    @BindView(R.id.lvBookDetail)
    ListView lvBookDetail;
    @BindView(R.id.tvTotalBill)
    AnyTextView tvTotalBill;
    @BindView(R.id.btnAddReview)
    Button btnAddReview;
    @BindView(R.id.btnCancelBooking)
    Button btnCancelBooking;
    CarHistoryEnt carHistoryEnt;
    boolean isManageBoking = false;
    private List<BookingDetailEnt> bookingDetailEnts;
    private ArrayListAdapter<BookingDetailEnt> adapter;

    public static CarHistoryDetailFragment newInstance() {
        Bundle args = new Bundle();

        CarHistoryDetailFragment fragment = new CarHistoryDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new BinderBookingItem(getDockActivity(), prefHelper));

        if (getArguments() != null) {
            isManageBoking = getArguments().getBoolean(AppConstants.IsManageBook);
            carHistoryEnt = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.CarHistoryEnt), CarHistoryEnt.class);
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(carHistoryEnt.getDetailName());
    }

    public void bindData(List<Detail> result) {

        bookingDetailEnts = new ArrayList<>();

        for (Detail detail : result) {
            bookingDetailEnts.add(new BookingDetailEnt(detail.getKey(), detail.getValue()));
        }

        adapter.clearList();
        lvBookDetail.setAdapter(adapter);
        adapter.addAll(bookingDetailEnts);
        adapter.notifyDataSetChanged();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_history_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (carHistoryEnt != null && carHistoryEnt.getDetails() != null && carHistoryEnt.getDetails().size() > 0) {
            bindData(carHistoryEnt.getDetails());
            setData();
            Utils.justifyListViewHeightBasedOnChildren(lvBookDetail);

        }

    }

    public void setData() {

        if (carHistoryEnt.getImage() != null && carHistoryEnt.getImage().length() > 0) {
            Picasso.with(getDockActivity())
                    .load(carHistoryEnt.getImage())
                    .into(ivCar);
        }

        tvCarName.setText(carHistoryEnt.getDetailName());
        tvPrice.setText(carHistoryEnt.getCurrencyCode() + " " + carHistoryEnt.getAmount());

        if (carHistoryEnt.getHotelReviewsCount() != null && carHistoryEnt.getHotelReviewsCount().length() > 0 && Integer.parseInt(carHistoryEnt.getHotelReviewsCount()) >= 10) {
            tvRatting.setVisibility(View.VISIBLE);
            tvRattingInWord.setVisibility(View.VISIBLE);

            String ratting = "";
            if (carHistoryEnt.getRoomRating().length() > 0) {
                ratting = Utils.getTextRatting(Float.parseFloat(carHistoryEnt.getRoomRating()));
                CrRating.setScore(Float.parseFloat(carHistoryEnt.getRoomRating()));
            } else {
                CrRating.setVisibility(View.GONE);
            }

            if (ratting.length() > 0) {
                tvRatting.setText(carHistoryEnt + " ");
                tvRattingInWord.setText(ratting);
            }

        } else {
            CrRating.setVisibility(View.GONE);
            tvRatting.setVisibility(View.GONE);
            tvRattingInWord.setVisibility(View.GONE);
        }

        tvTotalBill.setText(carHistoryEnt.getCurrencyCode() + " " + carHistoryEnt.getAmount());

        if (isManageBoking) {
            btnCancelBooking.setVisibility(View.VISIBLE);
        } else {
            if (carHistoryEnt.getIsReviewed().equalsIgnoreCase("1")) {
                btnAddReview.setVisibility(View.GONE);
            } else {
                btnAddReview.setVisibility(View.VISIBLE);
            }
        }

    }


    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.AddReview:

                btnAddReview.setVisibility(View.GONE);
                UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.review_added_success), false);
                getDockActivity().popFragment();
                HistoryFragment historyFragment = new HistoryFragment();
                Bundle orderBundle = new Bundle();
                orderBundle.putInt(AppConstants.tabPosition, 2);
                historyFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(historyFragment, HistoryFragment.class.getSimpleName());

                break;

            case WebServiceConstants.CancelHotelReservation:

                UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.booking_canceled_success), false);
                btnCancelBooking.setVisibility(View.GONE);
                getDockActivity().popFragment();
                ManageBookingFragment manageBookingFragment = new ManageBookingFragment();
                orderBundle = new Bundle();
                orderBundle.putInt(AppConstants.tabPosition, 2);
                manageBookingFragment.setArguments(orderBundle);
                getDockActivity().replaceDockableFragment(manageBookingFragment, ManageBookingFragment.class.getSimpleName());

                break;
        }
    }

    @OnClick({R.id.btnAddReview, R.id.btnCancelBooking})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btnAddReview:

                final DialogHelper addReviewDialog = new DialogHelper(getDockActivity());
                addReviewDialog.initAddReviewDialog(R.layout.dialog_add_review, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (addReviewDialog.getReview(R.id.edtReview).length() > 0) {

                            serviceHelper.enqueueCall(webService.AddReviewHotel(
                                    addReviewDialog.getReview(R.id.edtReview),
                                    addReviewDialog.getRatting(R.id.CrRating),
                                    carHistoryEnt.getReferenceID(),
                                    carHistoryEnt.getDetailName(),
                                    carHistoryEnt.getReservationID(),
                                    "Bearer " + prefHelper.getUser().getAuthToken()
                            ), WebServiceConstants.AddReview);

                            addReviewDialog.hideDialog();
                        } else {
                            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_enter_review));
                        }
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addReviewDialog.hideDialog();
                    }
                });
                addReviewDialog.setCancelable(false);
                addReviewDialog.showDialog();

                break;

            case R.id.btnCancelBooking:
                DialogFactory.createQuitDialog(getMainActivity(),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                serviceHelper.enqueueCall(webService.CancelCarReservation(
                                        carHistoryEnt.getReservationID(),
                                        "Bearer " + prefHelper.getUser().getAuthToken()
                                ), WebServiceConstants.CancelHotelReservation);

                            }
                        }, R.string.are_you_sure_cancel_booking).show();


                break;
        }
    }
}
