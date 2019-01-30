package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.app.amyal.R;
import com.app.amyal.entities.NotificationEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.ui.adapters.ArrayListAdapter;
import com.app.amyal.ui.binders.BinderNotification;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.TitleBar;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NotificationsFragment extends BaseFragment {

    @BindView(R.id.lv_notification)
    ListView lvNotification;
    Unbinder unbinder;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    private List<NotificationEnt> notificationCollection;
    private ArrayListAdapter<NotificationEnt> adapter;

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new BinderNotification(getDockActivity(),prefHelper));
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.notification));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefHelper.setNotificationCount(0);

        serviceHelper.enqueueCall(webService.GetNotifications("Bearer "+prefHelper.getUser().getAuthToken()), WebServiceConstants.GetNotifications);

        getMainActivity().setBackground(R.drawable.sp_dark);

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.GetNotifications:
                notificationCollection = (List<NotificationEnt>) result;
                if (notificationCollection != null && notificationCollection.size() > 0)
                    bindData(notificationCollection);
                else
                    txtNoData.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void bindData(List<NotificationEnt> result) {
        
        if (result != null && result.size() >= 0) {
            txtNoData.setVisibility(View.GONE);
            lvNotification.setVisibility(View.VISIBLE);
        } else {
            txtNoData.setVisibility(View.VISIBLE);
            lvNotification.setVisibility(View.GONE);
        }

        adapter.clearList();
        lvNotification.setAdapter(adapter);
        adapter.addAll(notificationCollection);
        adapter.notifyDataSetChanged();

    }

}
