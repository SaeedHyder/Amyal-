package com.app.amyal.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserEnt {

    @SerializedName("AuthToken")
    @Expose
    private String authToken;
    @SerializedName("User")
    @Expose
    private User user;

    public String getAuthToken() {
        if (authToken.equals("") || authToken == null)
            return "";
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
