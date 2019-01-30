package com.app.amyal.interfaces;

import java.util.Date;

public interface IDateSelectListner {

     void onDatesSelected(String fromDate, String toDate);
     void onDateSelected(String fromDate,Date fromdate);
     void onDatesSelected(String fromDate, String toDate, Date todate,Date fromdate);
}
