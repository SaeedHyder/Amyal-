package com.app.amyal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.app.amyal.R;
import com.app.amyal.entities.User;
import com.app.amyal.entities.UserEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by waq on 1/10/2018.
 */

public class LanguageCurrencyFragment extends BaseFragment {


    @BindView(R.id.spLanguage)
    Spinner spLanguage;
    @BindView(R.id.spCurrency)
    Spinner spCurrency;
    @BindView(R.id.btnDone)
    Button btnDone;
    Unbinder unbinder;

    ArrayList<String> languageList = new ArrayList<>();
    ArrayAdapter<String> languageAdapter;

    ArrayList<String> currencyList = new ArrayList<>();
    ArrayAdapter<String> currencyAdapter;

    public static LanguageCurrencyFragment newInstance() {
        return new LanguageCurrencyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lamguage_currency, container, false);

        getMainActivity().setBackground(R.drawable.sp_dark);

        unbinder = ButterKnife.bind(this, view);

        getCurrency();
        setLanguageSpinnerData();

        setData();
        return view;
    }

    private void setLanguageSpinnerData() {

        languageList = new ArrayList<>();
        languageList.add("Language");
        languageList.add("English");
//        languageList.add("French");
        languageList.add("Arabic");

        languageAdapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner_item_2, languageList);
        languageAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spLanguage.setAdapter(languageAdapter);
        spLanguage.setSelection(0);

        languageAdapter.notifyDataSetChanged();
    }

    private void setCurrencySpinnerData(ArrayList data) {

        data.add(0, "Currency");
//        currencyList.add("USD");
//        currencyList.add("Euro");
//        currencyList.add("AED");

        currencyAdapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner_item_2, data);
        currencyAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spCurrency.setAdapter(currencyAdapter);
        spCurrency.setSelection(0);

        currencyAdapter.notifyDataSetChanged();

        spCurrency.setSelection(data.indexOf(prefHelper.getUser().getUser().getCurrencyCode()));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                UserEnt userEnt = prefHelper.getUser();
//                userEnt.getUser().setLanguageCode(spLanguage.getSelectedItem().toString());
//                prefHelper.putUser(userEnt);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                if (position == 1) {
//                    UserEnt userEnt = prefHelper.getUser();
//                    userEnt.getUser().setCurrencyCode(AppConstants.CurrencyCode.USD.toString());
//                    prefHelper.putUser(userEnt);
//                } else if (position == 2) {
//                    UserEnt userEnt = prefHelper.getUser();
//                    userEnt.getUser().setCurrencyCode(AppConstants.CurrencyCode.EUR.toString());
//                    prefHelper.putUser(userEnt);
//                } else if (position == 3) {
//                    UserEnt userEnt = prefHelper.getUser();
//                    userEnt.getUser().setCurrencyCode(AppConstants.CurrencyCode.AED.toString());
//                    prefHelper.putUser(userEnt);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.language_currency));
        titleBar.showBackButton();
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.UpdatePreferences:

                getDockActivity().popFragment();
                User user = prefHelper.getUser().getUser();
                UserEnt userEnt = prefHelper.getUser();
                user.setLanguageCode(AppConstants.LanguageCode.en.toString());
                user.setCurrencyCode(spCurrency.getSelectedItem().toString());
                userEnt.setUser(user);
                prefHelper.putUser(userEnt);
                break;
            case WebServiceConstants.POSTCURRENCY:

                setCurrencySpinnerData((ArrayList) result);

                break;

        }
    }

    @OnClick(R.id.btnDone)
    public void onViewClicked() {

        if (spLanguage.getSelectedItemPosition() != 0) {
            if (spCurrency.getSelectedItemPosition() != 0) {
                serviceHelper.enqueueCall(webService.UpdatePreferences(prefHelper.getUser().getUser().getLanguageCode(), prefHelper.getUser().getUser().getCurrencyCode(),
                        "Bearer " + prefHelper.getUser().getAuthToken()), WebServiceConstants.UpdatePreferences);
            } else {
                UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.please_selec_curency));
            }
        } else {
            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.please_select_language));
        }
    }

    private void setData() {
        User user = prefHelper.getUser().getUser();
        if (user.getLanguageCode() != null && !user.getLanguageCode().equals("")) {
            if (user.getLanguageCode().equals(AppConstants.LanguageCode.en.toString())) {
                spLanguage.setSelection(1);
            } else if (user.getLanguageCode().equals(AppConstants.LanguageCode.ar.toString())) {
                spLanguage.setSelection(2);
            }
        }
    }

    private void getCurrency() {
        serviceHelper.enqueueCall(webService.GetCurrency(), WebServiceConstants.POSTCURRENCY);
    }
}
