package com.app.amyal.entities;

public class CarSearchModel {

    String PickUpLocationCode;
    String DropOffLocationCode;
    String PickUpDate;
    String DropOffDate;
    String PickUpTime;
    String DropOffTime;
    String CarCategory;
    String CurrencyCode;
    String LanguageCode;
    String CountryOfResidence;
    String DriverAge;

    String ratePlanCode;
    String sequenceNumber;
    String CarCode;

    public CarSearchModel(String PickUpLocationCode, String DropOffLocationCode,String PickUpDate,String DropOffDate,String PickUpTime,String DropOffTime,String CarCategory,String CurrencyCode,
                          String LanguageCode,String CountryOfResidence,String DriverAge,String ratePlanCode,String sequenceNumber){

        setPickUpLocationCode(PickUpLocationCode);
        setDropOffLocationCode(DropOffLocationCode);
        setPickUpDate(PickUpDate);
        setDropOffDate(DropOffDate);
        setPickUpTime(PickUpTime);
        setDropOffTime(DropOffTime);
        setCarCategory(CarCategory);
        setCurrencyCode(CurrencyCode);
        setLanguageCode(LanguageCode);
        setCountryOfResidence(CountryOfResidence);
        setDriverAge(DriverAge);
        setRatePlanCode(ratePlanCode);
        setSequenceNumber(sequenceNumber);
    }

    public String getCarCode() {
        return CarCode;
    }

    public void setCarCode(String carCode) {
        CarCode = carCode;
    }

    public String getPickUpLocationCode() {
        return PickUpLocationCode;
    }

    public void setPickUpLocationCode(String pickUpLocationCode) {
        PickUpLocationCode = pickUpLocationCode;
    }

    public String getDropOffLocationCode() {
        return DropOffLocationCode;
    }

    public void setDropOffLocationCode(String dropOffLocationCode) {
        DropOffLocationCode = dropOffLocationCode;
    }

    public String getPickUpDate() {
        return PickUpDate;
    }

    public void setPickUpDate(String pickUpDate) {
        PickUpDate = pickUpDate;
    }

    public String getDropOffDate() {
        return DropOffDate;
    }

    public void setDropOffDate(String dropOffDate) {
        DropOffDate = dropOffDate;
    }

    public String getPickUpTime() {
        return PickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        PickUpTime = pickUpTime;
    }

    public String getDropOffTime() {
        return DropOffTime;
    }

    public void setDropOffTime(String dropOffTime) {
        DropOffTime = dropOffTime;
    }

    public String getCarCategory() {
        return CarCategory;
    }

    public void setCarCategory(String carCategory) {
        CarCategory = carCategory;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public String getCountryOfResidence() {
        return CountryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        CountryOfResidence = countryOfResidence;
    }

    public String getDriverAge() {
        return DriverAge;
    }

    public void setDriverAge(String driverAge) {
        DriverAge = driverAge;
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
}
