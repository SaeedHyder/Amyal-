package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.app.amyal.R;
import com.app.amyal.entities.NavigationEnt;
import com.app.amyal.entities.UserEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.helpers.DialogHelper;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.ui.adapters.ArrayListAdapter;
import com.app.amyal.ui.binders.NavigationItemBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.TitleBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class SideMenuFragment extends BaseFragment {

    @BindView(R.id.txtUsername)
    AnyTextView txtUsername;

    @BindView(R.id.sideoptions)
    ListView sideoptions;

    @BindView(R.id.txtViewProfile)
    AnyTextView txtViewProfile;
    @BindView(R.id.llUser)
    LinearLayout llUser;

    Unbinder unbinder;
    @BindView(R.id.civProfilePic)
    CircleImageView civProfilePic;

    private ArrayList<NavigationEnt> navigationEnts;
    private ArrayListAdapter<NavigationEnt> adapter;


    public static SideMenuFragment newInstance() {
        return new SideMenuFragment();

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<NavigationEnt>(getDockActivity(), new NavigationItemBinder(getDockActivity()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sidemenu, container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getDockActivity().getWindow() != null)
            if (getDockActivity().getWindow().getDecorView() != null)
                UIHelper.hideSoftKeyboard(getDockActivity(), getDockActivity()
                        .getWindow().getDecorView());

        BindData();
    }


   /* private void BindWasherData() {
        navigationEnts = new ArrayList<>();
        navigationEnts.add(new NavigationEnt(R.drawable.home, getResources().getString(R.string.home)));
        navigationEnts.add(new NavigationEnt(R.drawable.requests, getString(R.string.newrequest)));
        navigationEnts.add(new NavigationEnt(R.drawable.performance, getString(R.string.myperformance)));
        navigationEnts.add(new NavigationEnt(R.drawable.profile_nav, getResources().getString(R.string.profile)));
        navigationEnts.add(new NavigationEnt(R.drawable.settings, getResources().getString(R.string.settings)));
        navigationEnts.add(new NavigationEnt(R.drawable.notification, getResources().getString(R.string.notification)));
        navigationEnts.add(new NavigationEnt(R.drawable.logout, getResources().getString(R.string.logout)));
        bindWasherview();

        if (prefHelper.getEmployee() != null) {
            Picasso.with(getDockActivity()).load(prefHelper.getEmployee().getProfileImage()).fit().placeholder(R.drawable.img_holder).into(imgDriver);
            txtDrivername.setText(prefHelper.getEmployee().getFullName() + "");
        }

    }

    private void bindWasherview() {
        adapter.clearList();
        sideoptions.setAdapter(adapter);
        adapter.addAll(navigationEnts);
        adapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(sideoptions);
        sideoptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (navigationEnts.get(position).getTitle().equals(getString(R.string.home))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(WasherHomeFragment.newInstance(), WasherHomeFragment.class.getSimpleName());

                } else if (navigationEnts.get(position).getTitle().equals(getString(R.string.newrequest))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(WasherRequestFragment.newInstance(), WasherRequestFragment.class.getSimpleName());
                } else if (navigationEnts.get(position).getTitle().equals(getString(R.string.myperformance))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(WasherPerformanceFragment.newInstance(), WasherPerformanceFragment.class.getSimpleName());

                } else if (navigationEnts.get(position).getTitle().equals(getResources().getString(R.string.profile))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(WasherProfileFragment.newInstance(), WasherProfileFragment.class.getSimpleName());
                } else if (navigationEnts.get(position).getTitle().equals(getResources().getString(R.string.settings))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(WasherSettingsFragment.newInstance(), WasherSettingsFragment.class.getSimpleName());
                } else if (navigationEnts.get(position).getTitle().equals(getResources().getString(R.string.notification))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(WasherNotificationFragment.newInstance(), WasherNotificationFragment.class.getSimpleName());
                } else if (navigationEnts.get(position).getTitle().equals(getString(R.string.logout))) {
                    final DialogHelper logoutdialog = new DialogHelper(getDockActivity());
                    logoutdialog.initlogout(R.layout.logout_dialog, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            serviceHelper.enqueueCall(webService.employeeLogout(prefHelper.getEmployeeId()), employeeLogout);
                            logoutdialog.hideDialog();

                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getMainActivity().getResideMenu().closeMenu();
                            logoutdialog.hideDialog();
                        }
                    });
                    logoutdialog.setCancelable(false);
                    logoutdialog.showDialog();
                }


            }
        });
    }*/


    private void BindData() {

        navigationEnts = new ArrayList<>();
        navigationEnts.add(new NavigationEnt(R.drawable.home, getResources().getString(R.string.home)));
        navigationEnts.add(new NavigationEnt(R.drawable.history, getString(R.string.booking_history)));
        navigationEnts.add(new NavigationEnt(R.drawable.contact, getResources().getString(R.string.contactus)));
        navigationEnts.add(new NavigationEnt(R.drawable.settings, getResources().getString(R.string.settings)));

        if (prefHelper.isLogin()) {
            navigationEnts.add(new NavigationEnt(R.drawable.logout, getResources().getString(R.string.logout)));
            llUser.setVisibility(View.VISIBLE);

            UserEnt userEnt = prefHelper.getUser();

            if(userEnt!= null && userEnt.getUser() != null){
                txtUsername.setText(userEnt.getUser().getName());
                if(userEnt.getUser().getImage()!= null && userEnt.getUser().getImage().length() > 0){

                    Picasso.with(getDockActivity())
                            .load(userEnt.getUser().getImage())
                            .into(civProfilePic);
                }
            }

        } else {
            navigationEnts.add(new NavigationEnt(R.drawable.logout, getResources().getString(R.string.login)));
            llUser.setVisibility(View.INVISIBLE);
        }

        bindview();

        /*if (prefHelper.getUser() != null) {

            Picasso.with(getDockActivity()).load(prefHelper.getUser().getProfileImage()).fit().placeholder(R.drawable.img_holder).into(imgDriver);
            txtDrivername.setText(prefHelper.getUser().getFullName() + "");
        }*/

    }

    private void bindview() {
        adapter.clearList();
        sideoptions.setAdapter(adapter);
        adapter.addAll(navigationEnts);
        adapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(sideoptions);
        sideoptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (navigationEnts.get(position).getTitle().equals(getString(R.string.home))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.class.getSimpleName());

                } else if (navigationEnts.get(position).getTitle().equals(getString(R.string.booking_history))) {
                    if (prefHelper.isLogin()) {
                        getMainActivity().getResideMenu().closeMenu();
                        getDockActivity().popBackStackTillEntry(0);
                        getDockActivity().replaceDockableFragment(HistoryFragment.newInstance(), HistoryFragment.class.getSimpleName());
                    } else {
                        UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.please_login_first));
                    }
                } else if (navigationEnts.get(position).getTitle().equals(getResources().getString(R.string.contactus))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(ContactUsFragment.newInstance(false), ContactUsFragment.class.getSimpleName());

                } else if (navigationEnts.get(position).getTitle().equals(getResources().getString(R.string.settings))) {
                    if (prefHelper.isLogin()) {
                        getMainActivity().getResideMenu().closeMenu();
                        getDockActivity().popBackStackTillEntry(0);
                        getDockActivity().replaceDockableFragment(SettingsFragment.newInstance(), SettingsFragment.class.getSimpleName());
                    } else {
                        UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.please_login_first));
                    }
                } else if (navigationEnts.get(position).getTitle().equals(getString(R.string.logout))) {

                    final DialogHelper logoutdialog = new DialogHelper(getDockActivity());
                    logoutdialog.initlogout(R.layout.dialog_logout, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            logoutdialog.hideDialog();
                            //serviceHelper.enqueueCall(webService.userLogout(prefHelper.getUserId()), WebServiceConstants.userLogout);
                            getMainActivity().getResideMenu().closeMenu();
                            prefHelper.setLoginStatus(false);
                            getDockActivity().popBackStackTillEntry(0);
                            getDockActivity().replaceDockableFragment(WelcomeFragment.newInstance(), WelcomeFragment.class.getSimpleName());

                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getMainActivity().getResideMenu().closeMenu();
                            logoutdialog.hideDialog();
                        }
                    });
                    logoutdialog.setCancelable(false);
                    logoutdialog.showDialog();

                } else if (navigationEnts.get(position).getTitle().equals(getString(R.string.login))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), LoginFragment.class.getSimpleName());
                }
            }
        });
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.txtViewProfile)
    public void onViewClicked() {
        getMainActivity().getResideMenu().closeMenu();
        getDockActivity().popBackStackTillEntry(0);
        getDockActivity().replaceDockableFragment(MyProfileFragment.newInstance(), MyProfileFragment.class.getSimpleName());
    }


    /*@Override
    public void ResponseSuccess(Object result, Integer Tag) {
        switch (Tag) {
            case WebServiceConstants.userLogout:
                getDockActivity().StopLocationService();
                prefHelper.setLoginStatus(false);
                getMainActivity().getResideMenu().closeMenu();
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(UserSelectionFragment.newInstance(), UserSelectionFragment.class.getSimpleName());
                break;
            case employeeLogout:
                getDockActivity().StopLocationService();
                prefHelper.setLoginStatus(false);
                getMainActivity().getResideMenu().closeMenu();
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(UserSelectionFragment.newInstance(), UserSelectionFragment.class.getSimpleName());

                break;
        }
    }*/
}
