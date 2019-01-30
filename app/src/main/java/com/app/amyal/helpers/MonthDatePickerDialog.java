package com.app.amyal.helpers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.app.amyal.R;

import java.util.Calendar;

public class MonthDatePickerDialog extends DialogFragment {

    private static final int MAX_YEAR = 2099;
    private DatePickerDialog.OnDateSetListener  listener;
    private int minYear;
    private int maxYear;
    private boolean isShowMonth;

    public void setListener(DatePickerDialog.OnDateSetListener  listener,int minYear, int maxYear, boolean isShowMonth) {
        this.listener = listener;
        this.minYear = minYear;
        this.maxYear = maxYear;
        this.isShowMonth = isShowMonth;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.date_picker_layout, null);
        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);

        if(isShowMonth) {
            monthPicker.setVisibility(View.VISIBLE);
            monthPicker.setMinValue(1);
            monthPicker.setMaxValue(12);
            monthPicker.setValue(cal.get(Calendar.MONTH) + 1);
        }else{
            monthPicker.setVisibility(View.GONE);
        }

        int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(minYear);
        yearPicker.setMaxValue(maxYear);
        yearPicker.setValue(year);

        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(isShowMonth) {
                            int month = monthPicker.getValue()-1;
                            listener.onDateSet(null, yearPicker.getValue(), month, 0);
                        }else{
                            listener.onDateSet(null, yearPicker.getValue(), -1, 0);
                        }

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MonthDatePickerDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}