package com.app.amyal.fragments;

import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.app.amyal.Comparators.SortBasedOnHotelCategory;
import com.app.amyal.Comparators.SortBasedOnHotelName;
import com.app.amyal.R;
import com.app.amyal.entities.CardDetailsEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.helpers.MonthDatePickerDialog;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.ui.views.AnyEditTextView;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.TitleBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddPaymentFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {

    Unbinder unbinder;
    @BindView(R.id.ed_creditcard)
    AnyEditTextView edCreditcard;
    @BindView(R.id.ed_expir_date)
    AnyTextView edExpirDate;
    @BindView(R.id.ed_CVV)
    AnyEditTextView edCVV;
    @BindView(R.id.ed_name_app_card)
    AnyEditTextView edNameAppCard;
    @BindView(R.id.ll_cc)
    LinearLayout llCc;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.txtTermsAndConditions)
    AnyTextView txtTermsAndConditions;
    @BindView(R.id.llbtn)
    LinearLayout llbtn;
    @BindView(R.id.spCardType)
    Spinner spCardType;

    private String DateSelected;

    private MonthDatePickerDialog pd;

    public static int tabPosition = 0;

    ArrayList<String> card_list = new ArrayList<>();
    ArrayAdapter<String> cardAdapter;

    ItemClickListener itemClickListener;

    String cardCode = "";

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public static AddPaymentFragment newInstance() {
        return new AddPaymentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_payment, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //txtTermsAndConditions.setPaintFlags(txtTermsAndConditions.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        setBackgroundImage(tabPosition);
        setCardSpinnerData();

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        pd = new MonthDatePickerDialog();
        pd.setListener(this, year, 2050, true);

        spCardType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    cardCode = "";
                }
                else if (position == 1) {
                    cardCode = "oMC";
                }
                else if (position == 2) {
                    cardCode = "oVI";
                }
                else if (position == 3) {
                    cardCode = "oAX";
                }
                else if (position == 4) {
                    cardCode = "oCB";
                }
                else if (position == 5) {
                    cardCode = "oDC";
                }
                else if (position == 6) {
                    cardCode = "oDS";
                }
                else if (position == 7) {
                    cardCode = "oER";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void setBackgroundImage(int current_position) {
        if (current_position == 0) {
            getMainActivity().setBackground(R.drawable.bg_flight);
        } else if (current_position == 1) {
            getMainActivity().setBackground(R.drawable.bg_hotel);
        } else if (current_position == 2) {
            getMainActivity().setBackground(R.drawable.bg_car);
        } else if (current_position == 3) {
            getMainActivity().setBackground(R.drawable.sp_dark);
        }
    }

    private void setCardSpinnerData() {

        card_list = new ArrayList<>();
        card_list.add("Select Card Type");
        card_list.add("Master Card");
        card_list.add("Visa");
        card_list.add("American Express");
        card_list.add("Carte Blanche");
        card_list.add("Diners Club");
        card_list.add("Discover Card");

        cardAdapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner_item_2, card_list);
        cardAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_2);
        spCardType.setAdapter(cardAdapter);
        spCardType.setSelection(0);

        cardAdapter.notifyDataSetChanged();
    }

    public void setTabPosition(int position) {
        tabPosition = position;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.card_details));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private boolean validate() {
        return edCreditcard.testValidity() && edCVV.testValidity() && edNameAppCard.testValidity();
    }

    @OnClick({R.id.btnSubmit, R.id.txtTermsAndConditions, R.id.ed_expir_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btnSubmit:

                if (validate()) {
                    if(cardCode.length() == 0){
                        UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.please_select_card_type));
                    }
                    else if (edCreditcard.getText().toString().length() < 16) {
                        UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.credit_card_no));
                    } else if (edCVV.getText().toString().length() < 3) {
                        UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.cvv_should));
                    } else {

                        if (itemClickListener != null) {
                            CardDetailsEnt detailsEnt = new CardDetailsEnt(cardCode, edCreditcard.getText().toString(), edCVV.getText().toString(), DateSelected, edNameAppCard.getText().toString(), edNameAppCard.getText().toString(), spCardType.getSelectedItem().toString());
                            itemClickListener.itemClicked(detailsEnt, false,-1);
                        }

                        getDockActivity().popFragment();
                    }
                }
                break;

            case R.id.txtTermsAndConditions:
                //getDockActivity().addDockableFragment(TermsAndCondition.newInstance(), "TermsAndCondition");
                break;

            case R.id.ed_expir_date:
                pd.show(getDockActivity().getSupportFragmentManager(), "MonthYearPickerDialog");
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, i);
        c.set(Calendar.MONTH, i1);
        //c.set(Calendar.DATE, i2);

        // and get that as a Date
        Date dateSpecified = c.getTime();
        if (dateSpecified.before(date)) {
            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.please_select_valid_date));
            edExpirDate.setText("");
        } else {
             DateSelected = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
            edExpirDate.setText(DateSelected);
            edExpirDate.setPaintFlags(Typeface.BOLD);
        }
    }
}
