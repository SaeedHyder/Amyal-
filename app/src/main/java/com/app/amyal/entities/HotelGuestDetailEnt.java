package com.app.amyal.entities;

public class HotelGuestDetailEnt {

    private String Name;
    private String SurName;
    private String Age;
    private String Phone;
    private String Email;
    private String Country;
    private String CountryCode;
    private String City;
    private String Address;
    private String PostalCode;
    private String State;
    private String dob;
    private String gender;
    private boolean MainGuest;

//    public HotelGuestDetailEnt(String name, String surName, String age, String phone, String email, String country, String countryCode, String city, String address, String postalCode, String state, boolean mainGuest, String gender) {
//        setName(name);
//        setSurName(surName);
//        setAge(age);
//        setPhone(phone);
//        setEmail(email);
//        setCountry(country);
//        setCountryCode(countryCode);
//        setCity(city);
//        setAddress(address);
//        setPostalCode(postalCode);
//        setState(state);
//        setMainGuest(mainGuest);
//    }

    public HotelGuestDetailEnt(String name, String age, String email, String dob, String gender, boolean mainGuest) {
        Name = name;
        Age = age;
        Email = email;
        this.dob = dob;
        this.gender = gender;
        MainGuest = mainGuest;
    }

    public String getDob() {

        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public boolean isMainGuest() {
        return MainGuest;
    }

    public void setMainGuest(boolean mainGuest) {
        MainGuest = mainGuest;
    }
}
