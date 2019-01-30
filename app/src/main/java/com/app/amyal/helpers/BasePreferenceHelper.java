package com.app.amyal.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.app.amyal.entities.CarSearchModel;
import com.app.amyal.entities.CurrencyModel;
import com.app.amyal.entities.FlightSearchModel;
import com.app.amyal.entities.HotelSearchModel;
import com.app.amyal.entities.PackageSerachModel;
import com.app.amyal.entities.UserEnt;
import com.app.amyal.retrofit.GsonFactory;

import java.lang.reflect.Field;

public class BasePreferenceHelper extends PreferenceHelper {

    private static final String KEY_BOOKING_CODE = "bookingcode";
    private Context context;

    private static final String KEY_USER = "key_user";
    private static final String KEY_CURRENCY = "key_currency";

    protected static final String KEY_LOGIN_STATUS = "islogin";

    private static final String FILENAME = "preferences";

    protected static final String Firebase_TOKEN = "Firebasetoken";

    protected static final String NotificationCount = "NotificationCount";

    protected static final String Key_HotelSearchModel = "HotelSearchModel";
    protected static final String Key_FlightSearchModel = "FlightSearchModel";
    protected static final String Key_PackageSerachModel= "PackageSerachModel";
    protected static final String Key_CarSearchModel = "CarSearchModel";
    private String KEY_HOTEL_CURRENCY_CODE="hotelcurrencycode";


    public BasePreferenceHelper(Context c) {
        this.context = c;
    }

    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE);
    }

    public void setLoginStatus( boolean isLogin ) {
        putBooleanPreference( context, FILENAME, KEY_LOGIN_STATUS, isLogin );
    }

    public boolean isLogin() {
        return getBooleanPreference(context, FILENAME, KEY_LOGIN_STATUS);
    }

    public void setHotelCurrencyCode(String code){
        putStringPreference(context, FILENAME,KEY_HOTEL_CURRENCY_CODE,code);
    }

    public String getHotelCurrencyCode() {
        return getStringPreference(context, FILENAME, KEY_HOTEL_CURRENCY_CODE);
    }

    public String getFirebase_TOKEN() {
        return getStringPreference(context, FILENAME, Firebase_TOKEN);
    }

    public void setFirebase_TOKEN(String _token) {
        putStringPreference(context, FILENAME, Firebase_TOKEN, _token);
    }
    public int getNotificationCount() {
        return getIntegerPreference(context, FILENAME, NotificationCount);
    }

    public void setNotificationCount(int count) {
        putIntegerPreference(context, FILENAME, NotificationCount, count);
    }

    public UserEnt getUser() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, KEY_USER), UserEnt.class);
    }

    public CurrencyModel getCurrency() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, KEY_CURRENCY), CurrencyModel.class);
    }

    public void putUser(UserEnt user) {
        putStringPreference(context, FILENAME, KEY_USER, GsonFactory
                .getConfiguredGson().toJson(user));
    }

    public void putCurrency(CurrencyModel user) {
        putStringPreference(context, FILENAME, KEY_CURRENCY, GsonFactory
                .getConfiguredGson().toJson(user));
    }

//    public HotelSearchModel getHotelSearchModel() {
//        return GsonFactory.getConfiguredGson().fromJson(
//                getStringPreference(context, FILENAME, Key_HotelSearchModel), HotelSearchModel.class);
//    }
//
//    public void putHotelSearchModel(HotelSearchModel hotelSearchModel) {
//        putStringPreference(context, FILENAME, Key_HotelSearchModel, GsonFactory
//                .getConfiguredGson().toJson(hotelSearchModel));
//    }

    public FlightSearchModel getFlightSearchModel() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, Key_FlightSearchModel), FlightSearchModel.class);
    }

    public void putFlightSearchModel(FlightSearchModel flightSearchModel) {
        putStringPreference(context, FILENAME, Key_FlightSearchModel, GsonFactory
                .getConfiguredGson().toJson(flightSearchModel));
    }

    public CarSearchModel getCarSearchModel() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, Key_CarSearchModel), CarSearchModel.class);
    }

    public void putCarSearchModel(CarSearchModel hotelSearchModel) {
        putStringPreference(context, FILENAME, Key_CarSearchModel, GsonFactory
                .getConfiguredGson().toJson(hotelSearchModel));
    }

    public PackageSerachModel getPackageSerachModel() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, Key_PackageSerachModel), PackageSerachModel.class);
    }

    public void putPackageSerachModel(PackageSerachModel packageSerachModel) {
        putStringPreference(context, FILENAME, Key_PackageSerachModel, GsonFactory
                .getConfiguredGson().toJson(packageSerachModel));
    }


}
