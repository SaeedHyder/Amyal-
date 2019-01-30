package com.app.amyal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import com.app.amyal.R;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.ui.views.AnyEditTextView;
import com.app.amyal.ui.views.TitleBar;
import com.app.amyal.ui.views.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EditBookingFragment extends BaseFragment {

    @BindView(R.id.edtCheckin)
    AnyEditTextView edtCheckin;
    @BindView(R.id.edtCheckout)
    AnyEditTextView edtCheckout;
    @BindView(R.id.edtNights)
    AnyEditTextView edtNights;
    @BindView(R.id.edtNoOfRooms)
    AnyEditTextView edtNoOfRooms;
    @BindView(R.id.btnDone)
    Button btnDone;
    @BindView(R.id.svmain)
    ScrollView svmain;
    Unbinder unbinder;

    public static EditBookingFragment newInstance() {
        Bundle args = new Bundle();

        EditBookingFragment fragment = new EditBookingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.edit_book_details));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel_edit_booking, container, false);
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

    @OnClick(R.id.btnDone)
    public void onViewClicked() {

        if(edtNoOfRooms.getText().toString() != null && edtNoOfRooms.getText().toString().length()>0){

            int rooms = 0;
            try {
                rooms = Integer.parseInt(edtNoOfRooms.getText().toString());
            }catch (Exception e){
                rooms = 0;
                e.printStackTrace();
            }

            if(rooms < 0){
                UIHelper.showLongToastInCenter(getDockActivity(),"Please select no of Rooms");
            }
            else{
                getDockActivity().popFragment();
            }

        }else{
            UIHelper.showLongToastInCenter(getDockActivity(),"Please select no of Rooms");
        }

    }
}