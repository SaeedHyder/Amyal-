package com.app.amyal.entities;

public class GuestCountWraper {

    String NoOfAdults;
    String NoOfChildrens;
    String NoOfInfant;

    public GuestCountWraper(String noOfAdults, String noOfChildrens) {
        NoOfAdults = noOfAdults;
        NoOfChildrens = noOfChildrens;
        NoOfInfant = "0";
    }

    public String getNoOfAdults() {
        return NoOfAdults;
    }

    public void setNoOfAdults(String noOfAdults) {
        NoOfAdults = noOfAdults;
    }

    public String getNoOfChildrens() {
        return NoOfChildrens;
    }

    public void setNoOfChildrens(String noOfChildrens) {
        NoOfChildrens = noOfChildrens;
    }

}
