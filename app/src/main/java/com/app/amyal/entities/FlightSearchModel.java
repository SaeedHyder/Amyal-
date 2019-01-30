package com.app.amyal.entities;


public class FlightSearchModel {

    String sourceLocationCode;
    String destinationLocationCode;
    String departureDate;
    String returnDate;
    int nofAdult;
    int nofChild;
    int nofInfant;
    String currencyCode;
    String languageCode;
    String direction;
    String cabinType;
    String noOfStops;

    String duration;
    String departureTime;
    String arrivalTime;
    String airlineCodes;

    String ratePlanCode;
    String sequenceNumber;

    String fromLocationCode;

    String toLocationCode;

    String priceRange;

    public FlightSearchModel( String sourceLocationCode,String destinationLocationCode,String departureDate,String returnDate,int nofAdult,
                              int nofChild,int nofInfant,String currencyCode,String languageCode,String direction,String cabinType,String noOfStops,String duration,
                              String departureTime,String arrivalTime,String airlineCodes,String ratePlanCode, String sequenceNumber ,String fromLocationCode,String toLocationCode,String priceRange){

        setSourceLocationCode(sourceLocationCode);
        setDestinationLocationCode(destinationLocationCode);
        setDepartureDate(departureDate);
        setReturnDate(returnDate);
        setNofAdult(nofAdult);
        setNofChild(nofChild);
        setNofInfant(nofInfant);
        setCurrencyCode(currencyCode);
        setLanguageCode(languageCode);
        setDirection(direction);
        setCabinType(cabinType);
        setNoOfStops(noOfStops);
        setDuration(duration);
        setDepartureTime(departureTime);
        setArrivalTime(arrivalTime);
        setAirlineCodes(airlineCodes);
        setRatePlanCode(ratePlanCode);
        setSequenceNumber(sequenceNumber);
        setFromLocationCode(fromLocationCode);
        setToLocationCode(toLocationCode);
        setPriceRange(priceRange);

    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getFromLocationCode() {
        return fromLocationCode;
    }

    public void setFromLocationCode(String fromLocationCode) {
        this.fromLocationCode = fromLocationCode;
    }

    public String getToLocationCode() {
        return toLocationCode;
    }

    public void setToLocationCode(String toLocationCode) {
        this.toLocationCode = toLocationCode;
    }

    public String getSourceLocationCode() {
        return sourceLocationCode;
    }

    public void setSourceLocationCode(String sourceLocationCode) {
        this.sourceLocationCode = sourceLocationCode;
    }

    public String getDestinationLocationCode() {
        return destinationLocationCode;
    }

    public void setDestinationLocationCode(String destinationLocationCode) {
        this.destinationLocationCode = destinationLocationCode;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public int getNofAdult() {
        return nofAdult;
    }

    public void setNofAdult(int nofAdult) {
        this.nofAdult = nofAdult;
    }

    public int getNofChild() {
        return nofChild;
    }

    public void setNofChild(int nofChild) {
        this.nofChild = nofChild;
    }

    public int getNofInfant() {
        return nofInfant;
    }

    public void setNofInfant(int nofInfant) {
        this.nofInfant = nofInfant;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getCabinType() {
        return cabinType;
    }

    public void setCabinType(String cabinType) {
        this.cabinType = cabinType;
    }

    public String getNoOfStops() {
        return noOfStops;
    }

    public void setNoOfStops(String noOfStops) {
        this.noOfStops = noOfStops;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getAirlineCodes() {
        return airlineCodes;
    }

    public void setAirlineCodes(String airlineCodes) {
        this.airlineCodes = airlineCodes;
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
