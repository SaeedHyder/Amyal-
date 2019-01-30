package com.app.amyal.entities;

/**
 * Created by waq on 2/6/2018.
 */

public class FlightTravellerEnt {

    String Name;
    String MiddleName;
    String SurName;
    String Age;
    String Phone;
    String Email;
    String Country;
    String CountryCode;
    String City;
    String Address;
    String PostalCode;
    String State;
    String PassportNumber;
    boolean MainGuest;
    boolean Gender;
    String BirthDate;
    String TravellerNo;
    String PassportNo;
    boolean isInfant;
    String PassportExpiary;
    int guestType;

    public FlightTravellerEnt() {


    }

    public FlightTravellerEnt(String Name, boolean MainGuest, String TravellerNo, boolean isInfant) {

        setName(Name);
        setMainGuest(MainGuest);
        setTravellerNo(TravellerNo);
        setInfant(isInfant);
    }

    public int getGuestType() {
        return guestType;
    }

    public void setGuestType(int guestType) {
        this.guestType = guestType;
    }

    public String getPassportExpiary() {
        return PassportExpiary;
    }

    public void setPassportExpiary(String passportExpiary) {
        PassportExpiary = passportExpiary;
    }

    public boolean isInfant() {
        return isInfant;
    }

    public void setInfant(boolean infant) {
        isInfant = infant;
    }

    public String getPassportNo() {
        return PassportNo;
    }

    public void setPassportNo(String passportNo) {
        PassportNo = passportNo;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public boolean isGender() {
        return Gender;
    }

    public void setGender(boolean gender) {
        Gender = gender;
    }

    public String getTravellerNo() {
        return TravellerNo;
    }

    public void setTravellerNo(String travellerNo) {
        TravellerNo = travellerNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurName() {
        return SurName;
    }

    public void setSurName(String surName) {
        SurName = surName;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPassportNumber() {
        return PassportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        PassportNumber = passportNumber;
    }

    public boolean isMainGuest() {
        return MainGuest;
    }

    public void setMainGuest(boolean mainGuest) {
        MainGuest = mainGuest;
    }

    public boolean getIsFemale() {
        return Gender;
    }

    public void setIsFemale(boolean gender) {
        Gender = gender;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }
}
