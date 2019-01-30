package com.app.amyal.retrofit;


import com.app.amyal.entities.AirlinesEnt;
import com.app.amyal.entities.CarHistoryEnt;
import com.app.amyal.entities.CarListEnt;
import com.app.amyal.entities.ContentModel;
import com.app.amyal.entities.CurrencyModel;
import com.app.amyal.entities.FlightBookingHistory;
import com.app.amyal.entities.FlightDetailsEnt;
import com.app.amyal.entities.FlightListEnt;
import com.app.amyal.entities.GuestCountWraper;
import com.app.amyal.entities.HotelAreaEnt;
import com.app.amyal.entities.HotelBookingEnt;
import com.app.amyal.entities.HotelGalleryEnt;
import com.app.amyal.entities.HotelListEnt;
import com.app.amyal.entities.HotelPoliciesEnt;
import com.app.amyal.entities.NotificationEnt;
import com.app.amyal.entities.PackageDetailsEnt;
import com.app.amyal.entities.PackageList;
import com.app.amyal.entities.ReservationEnt;
import com.app.amyal.entities.ResponseWrapper;
import com.app.amyal.entities.TopReview;
import com.app.amyal.entities.UserEnt;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface WebService {

   /* @FormUrlEncoded
    @POST("api/Account/SignUp")
    Call<ResponseWrapper<UserEnt>> signUp(
            @Field("Name") String Name,
            @Field("Email") String Email,
            @Field("Phone") String Phone,
            @Field("Password") String Password,
            @Field("ConfirmPassword") String ConfirmPassword,
            @Field("Image") String Image,
            @Field("FacebookAuthToken") String FacebookAuthToken,
            @Field("GoogleAuthToken") String GoogleAuthToken,
            @Field("TwitterAuthToken") String TwitterAuthToken,
            @Field("DeviceType") String DeviceType,
            @Field("DeviceID") String DeviceID
    );*/

    @Multipart
    @POST("api/Account/SignUp")
    Call<ResponseWrapper<UserEnt>> signUp(
            @Part("Name") RequestBody Name,
            @Part("Email") RequestBody Email,
            @Part("Phone") RequestBody Phone,
            @Part("Password") RequestBody Password,
            @Part("ConfirmPassword") RequestBody ConfirmPassword,
            @Part MultipartBody.Part Image,
            @Part("CountryCode") RequestBody CountryCode,
            @Part("PostalCode") RequestBody PostalCode,
            @Part("City") RequestBody City,
            @Part("Address") RequestBody Address,
            @Part("FacebookAuthToken") RequestBody FacebookAuthToken,
            @Part("GoogleAuthToken") RequestBody GoogleAuthToken,
            @Part("TwitterAuthToken") RequestBody TwitterAuthToken,
            @Part("DeviceType") RequestBody DeviceType,
            @Part("DeviceID") RequestBody DeviceID
    );


    @FormUrlEncoded
    @POST("api/Account/SignIn")
    Call<ResponseWrapper<UserEnt>> signIn(
            @Field("Email") String Email,
            @Field("Password") String Password,
            @Field("DeviceType") String DeviceType,
            @Field("DeviceID") String DeviceID
    );

    @FormUrlEncoded
    @POST("api/Account/SignInWithSocialMedia")
    Call<ResponseWrapper<UserEnt>> signInWithSocialMedia(
            @Field("AuthToken") String AuthToken,
            @Field("DeviceType") String DeviceType,
            @Field("DeviceID") String DeviceID
    );

    @Multipart
    @POST("api/Account/UpdateProfile")
    Call<ResponseWrapper<UserEnt>> updateProfile(
            @Part("Name") RequestBody Name,
            @Part("Phone") RequestBody Phone,
            @Part MultipartBody.Part Image,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("api/Account/UpdatePassword")
    Call<ResponseWrapper<UserEnt>> updatePassword(
            @Field("OldPassword") String OldPassword,
            @Field("Password") String Password,
            @Field("ConfirmPassword") String ConfirmPassword,
            @Header("Authorization") String token
    );


    @GET("api/Account/ForgotPassword")
    Call<ResponseWrapper> forgotPassword(@Query("email") String email);

    @GET("api/Hotel/SearchZones")
    Call<ResponseWrapper<ArrayList<HotelAreaEnt>>> searchZones(
            @Query("ZoneName") String ZoneName,
            @Query("IsAirport") String IsAirport);

    @GET("api/Hotel/SearchAllZones")
    Call<ResponseWrapper<ArrayList<HotelAreaEnt>>> SearchAllZones(
            @Query("ZoneName") String ZoneName);


    @FormUrlEncoded
    @POST("api/Account/UpdatePreferences")
    Call<ResponseWrapper> UpdatePreferences(
            @Field("LanguageCode") String Email,
            @Field("CurrencyCode") String Password,
            @Header("Authorization") String token
    );


    @POST("api/Hotel/GetHotelList")
    Call<ResponseWrapper<HotelListEnt>> GetHotelList(
            @Body RequestBody data
//            @Field("CheckInTime") String CheckInTime,
//            @Field("CheckOutTime") String CheckOutTime,
//            @Field("NoOfRooms") int NoOfRooms,
//            @Field("ZoneCode") String ZoneCode,
//            @Field("Guests[]") ArrayList<GuestCountWraper> Guests,
//            @Field("HotelName") String HotelName,
//            @Field("PriceRange") String PriceRange,
//            @Field("HotelCode") String HotelCode,
//            @Field("Rating") String Rating,
//            @Field("LanguageCode") String LanguageCode,
//            @Field("CurrencyCode") String CurrencyCode,
//            @Field("TravellingFor") String TravellingFor,
//            @Field("CountryOfResidence") String CountryOfResidence
    );

    @GET("api/Hotel/GetAllCountries")
    Call<ResponseWrapper<ArrayList<HotelAreaEnt>>> GetAllCountries();

    @GET("api/Hotel/GetCountryZones")
    Call<ResponseWrapper<ArrayList<HotelAreaEnt>>> GetCountryZones(@Query("CountryCode") int CountryCode);


    @GET("api/Hotel/GetHotelDetails")
    Call<ResponseWrapper<HotelGalleryEnt>> GetRoomDetails(
            @Query("hotelCode") String hotelCode,
            @Query("RatePlanCode") String RatePlanCode,
            @Query("LanguageCode") String LanguageCode,
            @Query("CurrencyCode") String CurrencyCode,
            @Query("RoomType") String RoomType

    );

    @GET("api/Hotel/GetHotelDetails")
    Call<ResponseWrapper<HotelGalleryEnt>> GetHotelDetails(
            @Query("LanguageCode") String LanguageCode,
            @Query("CurrencyCode") String CurrencyCode,
            @Query("hotelCode") String hotelCode
//            @Query("RatePlanCode") String RatePlanCode,
//            @Query("RoomType") String RoomType
    );

    @GET("api/Account/GetCurrencyRates")
    Call<ResponseWrapper<CurrencyModel>> GetCurrencyRate();

    @FormUrlEncoded
    @POST("api/updatedevicetoken")
    Call<ResponseWrapper> updateToken(
            @Field("user_id") String userId,
            @Field("device_type") String deviceType,
            @Field("device_token") String token
    );


    @FormUrlEncoded
    @POST("api/Hotel/GetHotelPolicies")
    Call<ResponseWrapper<HotelPoliciesEnt>> GetHotelPolicies(
            @Field("CheckInTime") String CheckInTime,
            @Field("CheckOutTime") String CheckOutTime,
            @Field("LanguageCode") String LanguageCode,
            @Field("CurrencyCode") String CurrencyCode,
            @Field("SequenceNumber") String SequenceNumber,
            @Field("HotelCode") String HotelCode,
            @Field("RatePlanCode") String RatePlanCode
    );

    @POST("api/Hotel/PostHotelReservation")
    Call<ResponseWrapper<ReservationEnt>> PostHotelReservation(
            @Body RequestBody params,
            @Header("Authorization") String token
    );

    @POST("api/Hotel/GetBookingHistory")
    Call<ResponseWrapper<List<HotelBookingEnt>>> GetBookingHistory(
            @Query("IsCurrent") String IsCurrent,
            @Header("Authorization") String token
    );

    @GET("api/Account/GetNotifications")
    Call<ResponseWrapper<List<NotificationEnt>>> GetNotifications(
            @Header("Authorization") String token
    );

   /* @GET("api/Hotel/GetReviews")
    Call<ResponseWrapper<List<TopReview>>> GetHotelReviews(
            @Query("ReferenceCode") String hotelCode
    );*/

    @GET("api/Reviews/GetReviews")
    Call<ResponseWrapper<List<TopReview>>> GetMyReviews(
            @Header("Authorization") String token
    );

    @GET("/api/Account/GetContentByKey")
    Call<ResponseWrapper<ContentModel>> GetContentByKey(
            @Query("Key") String Key
    );


    @POST("api/Hotel/CancelReservation")
    Call<ResponseWrapper> CancelHotelReservation(
            @Query("ReservationID") String reservationCode,
            @Header("Authorization") String token
    );

    @POST("api/Flight/CancelReservation")
    Call<ResponseWrapper> CancelReservation(
            @Query("ReservationID") String ReservationID,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("api/Reviews/AddReview")
    Call<ResponseWrapper> AddReviewHotel(
            @Field("Review") String Review,
            @Field("Rating") String Rating,
            @Field("ReferenceCode") String ReferenceCode,
            @Field("RoomType") String RoomType,
            @Field("ReservationID") String ReservationID,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("api/Flight/GetFlightList")
    Call<ResponseWrapper<FlightListEnt>> GetFlightList(
            @Field("SourceLocationCode") String SourceLocationCode,
            @Field("DestinationLocationCode") String DestinationLocationCode,
            @Field("DepartureDate") String DepartureDate,
            @Field("ReturnDate") String ReturnDate,
            @Field("NoOfAdults") int NoOfAdults,
            @Field("NoOfChildrens") int NoOfChildrens,
            @Field("NoOfInfant") int NoOfInfant,
            @Field("CurrencyCode") String CurrencyCode,
            @Field("LanguageCode") String LanguageCode,
            @Field("Direction") String Direction,
            @Field("CabinType") String CabinType,
            @Field("NoOfStops") String NoOfStops,
            @Field("Duration") String Duration,
            @Field("DepartureTime") String DepartureTime,
            @Field("ArrivalTime") String ArrivalTime,
            @Field("PriceRange") String PriceRange,
            @Field("AirlineCodes") String AirlineCodes,
            @Field("PageIndex") int PageIndex,
            @Field("DeviceID") String DeviceID
    );


    @FormUrlEncoded
    @POST("api/Flight/GetFlightDetails")
    Call<ResponseWrapper<FlightDetailsEnt>> GetFlightDetails(
            @Field("RatePlanCode") String RatePlanCode
    );


    @POST("api/Flight/PostFlightReservation")
    Call<ResponseWrapper<ReservationEnt>> PostFlightReservation(
            @Body RequestBody params,
            @Header("Authorization") String token
    );


    @POST("api/Flight/GetBookingHistory")
    Call<ResponseWrapper<List<FlightBookingHistory>>> GetFlightBookingHistory(
            @Query("IsCurrent") String IsCurrent,
            @Header("Authorization") String token
    );


    @GET("api/Flight/GetAirlines")
    Call<ResponseWrapper<List<AirlinesEnt>>> GetAirlines();


    @FormUrlEncoded
    @POST("api/Car/GetCarList")
    Call<ResponseWrapper<CarListEnt>> GetCarList(
            @Field("PickUpLocationCode") String PickUpLocationCode,
            @Field("DropOffLocationCode") String DropOffLocationCode,
            @Field("PickUpDate") String PickUpDate,
            @Field("DropOffDate") String DropOffDate,
            @Field("PickUpTime") String PickUpTime,
            @Field("DropOffTime") String DropOffTime,
            @Field("CarCategory") String CarCategory,
            @Field("CurrencyCode") String CurrencyCode,
            @Field("LanguageCode") String LanguageCode,
            @Field("CountryOfResidence") String CountryOfResidence,
            @Field("DriverAge") String DriverAge,
            @Field("TotalPrice") String TotalPrice,
            @Field("Specifications") String Specifications
    );

    @FormUrlEncoded
    @POST("api/Car/GetCarList")
    Call<ResponseWrapper<CarListEnt>> GetCarListHome(
            @Field("PickUpLocationCode") String PickUpLocationCode,
            @Field("DropOffLocationCode") String DropOffLocationCode,
            @Field("PickUpDate") String PickUpDate,
            @Field("DropOffDate") String DropOffDate,
            @Field("PickUpTime") String PickUpTime,
            @Field("DropOffTime") String DropOffTime,
            @Field("CarCategory") String CarCategory,
            @Field("CurrencyCode") String CurrencyCode,
            @Field("LanguageCode") String LanguageCode,
            @Field("CountryOfResidence") String CountryOfResidence,
            @Field("DriverAge") String DriverAge
    );

    @FormUrlEncoded
    @POST("api/Car/GetCarDetails")
    Call<ResponseWrapper<FlightDetailsEnt>> GetCarDetails(
            @Field("RatePlanCode") String RatePlanCode,
            @Field("PickUpLocationCode") String PickUpLocationCode,
            @Field("DropOffLocationCode") String DropOffLocationCode
    );

    @POST("api/Car/PostCarReservation")
    Call<ResponseWrapper<ReservationEnt>> PostCarReservation(
            @Body RequestBody params,
            @Header("Authorization") String token
    );


    @POST("api/Car/GetBookingHistory")
    Call<ResponseWrapper<List<CarHistoryEnt>>> GetCarBookingHistory(
            @Query("IsCurrent") String IsCurrent,
            @Header("Authorization") String token
    );

    @POST("api/Car/CancelReservation")
    Call<ResponseWrapper> CancelCarReservation(
            @Query("ReservationID") String ReservationID,
            @Header("Authorization") String token
    );


    @FormUrlEncoded
    @POST("api/Package/GetPackagesList")
    Call<ResponseWrapper<PackageList>> GetPackagesList(
            @Field("SourceLocationCode") String SourceLocationCode,
            @Field("DestinationLocationCode") String DestinationLocationCode,
            @Field("CheckInTime") String CheckInTime,
            @Field("CheckOutTime") String CheckOutTime,
            @Field("NoOfAdults") int NoOfAdults,
            @Field("NoOfChildrens") int NoOfChildrens,
            @Field("NoOfInfant") int NoOfInfant,
            @Field("CurrencyCode") String CurrencyCode,
            @Field("LanguageCode") String LanguageCode,
            @Field("CountryOfResidence") String CountryOfResidence,
            @Field("PackageCode") String PackageCode,
            @Field("PriceRange") String PriceRange,
            @Field("Duration") String Duration
    );

    @FormUrlEncoded
    @POST("api/Package/GetPackageDetails")
    Call<ResponseWrapper<PackageDetailsEnt>> GetPackageDetails(
            @Field("RatePlanCode") String RatePlanCode,
            @Field("PackageCode") String PackageCode,
            @Field("LanguageCode") String LanguageCode
    );

    @POST("api/Package/PostPackageBooking")
    Call<ResponseWrapper<ReservationEnt>> PostPackageBooking(
            @Body RequestBody params,
            @Header("Authorization") String token
    );


    @POST("api/Package/CancelReservation")
    Call<ResponseWrapper> CancelPackageReservation(
            @Query("ReservationID") String ReservationID,
            @Header("Authorization") String token
    );

    @POST("api/Package/GetBookingHistory")
    Call<ResponseWrapper<List<HotelBookingEnt>>> GetPackageBookingHistory(
            @Query("IsCurrent") String IsCurrent,
            @Header("Authorization") String token
    );


    @POST("api/Account/Currencies")
    Call<ResponseWrapper<ArrayList>> GetCurrency(
    );

}