package com.app.amyal.entities;

public class CardDetailsEnt {

    private String CardCode;

    private String CardNumber;

    private String SeriesCode;

    private String ExpireDate;

    private String CardHolderName;

    private String CardHolderSurname;

    private String CardHolderEmail;

    public CardDetailsEnt(String CardCode,String CardNumber,String SeriesCode,String ExpireDate,String CardHolderName,String CardHolderSurname,String CardHolderEmail){
        setCardCode(CardCode);
        setCardNumber(CardNumber);
        setSeriesCode(SeriesCode);
        setExpireDate(ExpireDate);
        setCardHolderName(CardHolderName);
        setCardHolderSurname(CardHolderSurname);
        setCardHolderEmail(CardHolderEmail);
    }

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String cardCode) {
        CardCode = cardCode;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getSeriesCode() {
        return SeriesCode;
    }

    public void setSeriesCode(String seriesCode) {
        SeriesCode = seriesCode;
    }

    public String getExpireDate() {
        return ExpireDate;
    }

    public void setExpireDate(String expireDate) {
        ExpireDate = expireDate;
    }

    public String getCardHolderName() {
        return CardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        CardHolderName = cardHolderName;
    }

    public String getCardHolderSurname() {
        return CardHolderSurname;
    }

    public void setCardHolderSurname(String cardHolderSurname) {
        CardHolderSurname = cardHolderSurname;
    }

    public String getCardHolderType() {
        return CardHolderEmail;
    }

    public void setCardHolderEmail(String cardHolderEmail) {
        CardHolderEmail = cardHolderEmail;
    }
}
