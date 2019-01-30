package com.app.amyal.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Image")
    @Expose
    private String image;

    @SerializedName("LanguageCode")
    @Expose
    private String LanguageCode;

    @SerializedName("CurrencyCode")
    @Expose
    private String CurrencyCode;

    @SerializedName("CountryCode")
    @Expose
    private String CountryCode;
    @SerializedName("PostalCode")
    @Expose
    private String PostalCode;
    @SerializedName("IsSocialLogin")
    @Expose
    private boolean IsSocialLogin;

    public boolean getIsSocialLogin() {
        return IsSocialLogin;
    }

    public void setIsSocialLogin(boolean isSocialLogin) {
        IsSocialLogin = isSocialLogin;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getCountryCode() {

        if (CountryCode == null) {
            return "US";
        } else if (CountryCode.length() == 0) {
            return "US";
        }

        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
