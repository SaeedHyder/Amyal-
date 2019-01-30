package com.app.amyal.global;

public class AppConstants {

    public static final int CAMERA_PERMISSION = 4201;
    public static final int LOCATION_PERMISSION = 4521;
    public static final String Device_Type = "android";
    public static final String SOCIAL_MEDIA_TYPE = "facebook";
    //Firebase Constants
    // broadcast receiver intent filters
    public static final String TOPIC_GLOBAL = "global";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    public static final String LOG_OUT_RECEIVER = "logOutReceiver";
    public static String twitter = "twitter";
    public static int INTENT_ID = 100;
    // id to handle the notification in the notification tray
    public static int NOTIFICATION_ID = 100;
    public static String DateFormat_DMY2 = "dd-MM-yy";
    public static String DateFormat_DMY3 = "dd MMM, yyyy";
    public static String DateFormat_YMD = "yyyy-MM-dd";
    public static String DateFormat_HM = "HH:mm";
    public static String DateFormat_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static String IsReturnTrip = "IsReturnTrip";
    public static String IsInBound = "IsInBound";
    public static String IsEdit = "IsEdit";
    public static String IsCheckInDate = "IsCheckInDate";
    public static String IsFacilities = "IsFacilities";
    public static String tabPosition = "tabPosition";
    public static String Name = "Name";
    public static String Email = "Email";
    public static String ProfileImage = "ProfileImage";
    public static String SocialMediaPlatform = "SocialMediaPlatform";
    public static String SocialMediaId = "SocialMediaId";
    public static String HotelListEnt = "HotelListEnt";
    public static String HotelListModel = "HotelListModel";
    public static String HotelReviewsModel = "HotelReviewsModel";
    public static String HotelPolicyModel = "HotelPolicyModel";
    public static String ReservationEnt = "ReservationEnt";
    public static String HotelBookingEnt = "HotelBookingEnt";
    public static String FlightListEnt = "FlightListEnt";
    public static String PackageList = "PackageList";
    public static String PackageListEnt = "PackageListEnt";
    public static String packageDetailsEnt = "packageDetailsEnt";
    public static String FlightsList = "FlightsList";
    public static String CarListModel = "CarListModel";
    public static String TopReviews = "TopReviews";
    public static String CarHistoryEnt = "CarHistoryEnt";
    public static String TERMCONDITION = "Terms & Conditions";
    public static String privacypolicy = "Privacy & Policy";
    public static String ABOUT = "About Amyal";
    public static String FlightTravellerEnt = "FlightsList";
    public static String position = "position";
    public static String FlightBookingHistory = "FlightBookingHistory";
    public static String CarListEnt = "CarListEnt";
    public static String IsManageBook = "IsManageBook";
    public static String Poor = "Poor";
    public static String Fair = "Fair";
    public static String Good = "Good";
    public static String VeryGood = "Very Good";
    public static String Excellent = "Excellent";
    public static int EXTERNAL_STORAGE = 12012;
    public static String LangCode = "es";
    public static String CurCode = "SAR";
    public static String CountryCode = "US";
    public static String Nonstop = "Nonstop";

    public static enum LanguageCode {
        en,
        fr,
        ar,
    }

    public static enum CurrencyCode {
        USD,
        EUR,
        AED,
    }

    public static enum CabinType {
        First,
        Business,
        Economy,
        PremiumFirst,
        PremiumBusiness,
        PremiumEconomy,
    }


    public static enum Directions {
        Outbound,
        Inbound,
        RoundTrip,
    }


}
