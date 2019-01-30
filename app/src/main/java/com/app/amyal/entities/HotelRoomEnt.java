package com.app.amyal.entities;

import android.provider.Settings;

/**
 * Created by waq on 12/5/2017.
 */

public class HotelRoomEnt {

    String imageRoom;
    String roomName;
    String price;
    float ratting;
    String rattingInWord;

    public HotelRoomEnt(String imageRoom, String roomName,String price,float ratting,String rattingInWord){
        setImageRoom(imageRoom);
        setRoomName(roomName);
        setPrice(price);
        setRatting(ratting);
        setRattingInWord(rattingInWord);
    }


    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getImageRoom() {
        return imageRoom;
    }

    public void setImageRoom(String imageRoom) {
        this.imageRoom = imageRoom;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public float getRatting() {
        return ratting;
    }

    public void setRatting(float ratting) {
        this.ratting = ratting;
    }

    public String getRattingInWord() {
        return rattingInWord;
    }

    public void setRattingInWord(String rattingInWord) {
        this.rattingInWord = rattingInWord;
    }
}
