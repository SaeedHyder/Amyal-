package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.app.amyal.R;
import com.app.amyal.entities.HotelRoomEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.Utils;
import com.app.amyal.interfaces.IHotelRoomBtnListner;
import com.app.amyal.ui.adapters.FilterableListAdapter;
import com.app.amyal.ui.binders.BinderHotelRoom;
import com.app.amyal.ui.views.AnyEditTextView;
import com.app.amyal.ui.views.TitleBar;
import com.google.common.base.Function;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HotelRoomFragment extends BaseFragment implements IHotelRoomBtnListner {

    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.edt_search)
    AnyEditTextView edtSearch;
    @BindView(R.id.ivClose)
    ImageView ivClose;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.lvRooms)
    ListView lvRooms;
    Unbinder unbinder;

    private List<HotelRoomEnt> hotelRoomEntList;
    private FilterableListAdapter<HotelRoomEnt> adapter;

    public static HotelRoomFragment newInstance() {

        Bundle args = new Bundle();

        HotelRoomFragment fragment = new HotelRoomFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new FilterableListAdapter<>(getDockActivity(), new BinderHotelRoom(getDockActivity(), prefHelper,this));

        if (getArguments() != null) {
        }

    }

    private void filterList(String string) {
        adapter.getFilter().filter(string);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel_rooms, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("Port Adriano Hotel");

        titleBar.showRightButton(R.drawable.search, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSearch.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindData(null);

        edtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterList(s.toString());
            }

        });

    }


    public void bindData(ArrayList<HotelRoomEnt> result) {

        hotelRoomEntList = new ArrayList<>();
        hotelRoomEntList.add(new HotelRoomEnt("http://www.hotelroomsearch.net/im/hotels/hr/deluxe-room-1.jpg", "Deluxe Room","AED 1500.00",3.4f,"Very Good"));
        hotelRoomEntList.add(new HotelRoomEnt("https://www.cruisedeckplans.com/assets/floors/2/reg/1110-4.jpg", "Pearl Room","AED 1500.00",3.5f,"Very Good"));
        hotelRoomEntList.add(new HotelRoomEnt("http://www.hotelroomsearch.net/im/hotels/hr/deluxe-room-1.jpg", "Deluxe Room","AED 1500.00",3.4f,"Very Good"));
        hotelRoomEntList.add(new HotelRoomEnt("https://www.cruisedeckplans.com/assets/floors/2/reg/1110-4.jpg", "Pearl Room","AED 1500.00",3.5f,"Very Good"));

        //hotelRoomEntList.add(new HotelRoomEnt("drawable://" + R.drawable.american, "Westhill Cotage", 4f, "Las Vegas", "8.4 Very Good", "AED 1500.00"));

        /*if (flightEntList.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvMyreviews.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvMyreviews.setVisibility(View.VISIBLE);

        }*/
        //notificationCollection.addAll(result);

        adapter.clearList();
        adapter = new FilterableListAdapter<HotelRoomEnt>(getDockActivity(), new ArrayList<HotelRoomEnt>(), new BinderHotelRoom(getDockActivity(), prefHelper,this),
                new Function<HotelRoomEnt, String>() {
                    @Override
                    @Nullable
                    public String apply(
                            @Nullable HotelRoomEnt arg0) {
                        return arg0.getRoomName();
                    }
                });
        lvRooms.setAdapter(adapter);
        adapter.addAll(hotelRoomEntList);
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ivClose)
    public void onViewClicked() {
        if (edtSearch.getText().toString().length() == 0) {
            llSearch.setVisibility(View.GONE);
            Utils.HideKeyBoard(getDockActivity());
        } else {
            edtSearch.setText("");
        }
    }

    @Override
    public void onClick(int position) {
        getDockActivity().replaceDockableFragment(RoomDetailsFragment.newInstance(), RoomDetailsFragment.class.getSimpleName());
    }
}