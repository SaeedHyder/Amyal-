package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.app.amyal.R;
import com.app.amyal.entities.BookingDetailEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.ui.adapters.ArrayListAdapter;
import com.app.amyal.ui.binders.BinderSearchItem;
import com.app.amyal.ui.views.AnyEditTextView;
import com.app.amyal.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by khan_muhammad on 11/30/2017.
 */

public class SearchHotelFragment extends BaseFragment {

    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.edt_search)
    AnyEditTextView edtSearch;
    @BindView(R.id.ivClose)
    ImageView ivClose;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.lvSearch)
    ListView lvSearch;
    Unbinder unbinder;
    private List<BookingDetailEnt> bookingDetailEnts;
    private ArrayListAdapter<BookingDetailEnt> adapter;
    private ItemClickListener itemClickListener;
    private boolean isfrom = false;

    public static SearchHotelFragment newInstance() {
        return new SearchHotelFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new BinderSearchItem(getDockActivity(), prefHelper));
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.search));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_city_airport, container, false);

        getMainActivity().setBackground(R.drawable.bg_flight);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefHelper.setNotificationCount(0);
        //serviceHelper.enqueueCall(webService.getNotificationCount(prefHelper.getMerchantId()), WebServiceConstants.NotificaitonCount);
        bindData(null);

        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                itemClickListener.itemClicked(bookingDetailEnts.get(position), isfrom, -1);
                getDockActivity().popFragment();

            }
        });

        init();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    public void bindData(ArrayList<BookingDetailEnt> result) {

        bookingDetailEnts = new ArrayList<>();
        bookingDetailEnts.add(new BookingDetailEnt("Dubai, United Arab Emirates", "Dubai, United Arab Emirates"));
        bookingDetailEnts.add(new BookingDetailEnt("Singapore, Singapore", "Singapore, Singapore"));
        bookingDetailEnts.add(new BookingDetailEnt("Mexico City, Distrito Federal, MX", "Mexico City, Distrito Federal, MX"));


       /* if (result.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvNotification.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvNotification.setVisibility(View.VISIBLE);

        }*/
        //notificationCollection.addAll(result);
        adapter.clearList();
        lvSearch.setAdapter(adapter);
        adapter.addAll(bookingDetailEnts);
        adapter.notifyDataSetChanged();

    }

    public void setItemClickListener(ItemClickListener itemClickListener, boolean isfrom) {
        this.itemClickListener = itemClickListener;
        this.isfrom = isfrom;
    }


    @OnClick(R.id.ivClose)
    public void onViewClicked() {
        edtSearch.setText("");
    }

    private void init() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtSearch.getText().length() > 0) {
                    ivClose.setVisibility(View.VISIBLE);
                } else
                    ivClose.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
