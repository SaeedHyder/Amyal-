package com.app.amyal.entities;

public class HotelSearchModel {

    static HotelSearchModel instance;
    String checkInDate;
    String checkOutDate;
    String zoneCode;
    String hotelName;
    String priceRange;
    String hotelCode;
    String ratting;
    String travellingFor;
    GuestWraper[] guestWrapers = new GuestWraper[3];
    String amountWithTax;
    String currencyCode;
    String sequenceNumber;
    String ratePlanCode;
    int totAdult;
    int totChild;
    int totInfant;
    String bookingCode;
    String bookingExpiry;

    private HotelSearchModel() {

    }

    public static HotelSearchModel getInstance() {

        if (instance == null) {
            instance = new HotelSearchModel();
        }
        return instance;
    }

    public void cleardata() {

        checkInDate = checkOutDate = bookingCode = zoneCode = hotelName = priceRange = hotelCode = ratting = travellingFor = amountWithTax = currencyCode = sequenceNumber = ratePlanCode=bookingExpiry = "";
        totAdult = totChild = totInfant = 0;
        guestWrapers = new GuestWraper[3];
    }

    public String getBookingExpiry() {
        return bookingExpiry;
    }

    public void setBookingExpiry(String bookingExpiry) {
        this.bookingExpiry = bookingExpiry;
    }

    public void addData(String checkInDate, String checkOutDate, GuestWraper[] guestWrapers, String zoneCode, String hotelName, String priceRange, String hotelCode, String ratting, String travellingFor, String sequenceNumber, String ratePlanCode, String amountWithTax, String currencyCode) {

        setCheckInDate(checkInDate);
        setCheckOutDate(checkOutDate);
        setGuestWrapers(guestWrapers);
        setZoneCode(zoneCode);
        setHotelName(hotelName);
        setPriceRange(priceRange);
        setHotelCode(hotelCode);
        setRatting(ratting);
        setTravellingFor(travellingFor);
        setSequenceNumber(sequenceNumber);
        setRatePlanCode(ratePlanCode);
        setAmountWithTax(amountWithTax);
        setCurrencyCode(currencyCode);
    }

    public int getTotAdult() {
        return totAdult;
    }

    public void setTotAdult(int totAdult) {
        this.totAdult = totAdult;
    }

    public int getTotChild() {
        return totChild;
    }

    public void setTotChild(int totChild) {
        this.totChild = totChild;
    }

    public int getTotInfant() {
        return totInfant;
    }

    public void setTotInfant(int totInfant) {
        this.totInfant = totInfant;
    }

    public GuestWraper[] getGuestWrapers() {
        return guestWrapers;
    }

    public void setGuestWrapers(GuestWraper[] guestWrapers) {
        this.guestWrapers = guestWrapers;
    }

    public void setArrayAt(int index, GuestWraper guestWraper) {
        guestWrapers[index] = guestWraper;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public String getAmountWithTax() {
        return amountWithTax;
    }

    public void setAmountWithTax(String amountWithTax) {
        this.amountWithTax = amountWithTax;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getRatting() {
        return ratting;
    }

    public void setRatting(String ratting) {
        this.ratting = ratting;
    }

    public String getTravellingFor() {
        return travellingFor;
    }

    public void setTravellingFor(String travellingFor) {
        this.travellingFor = travellingFor;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }


}
