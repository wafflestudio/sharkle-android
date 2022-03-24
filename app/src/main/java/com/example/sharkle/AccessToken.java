package com.example.sharkle;

import com.google.gson.annotations.SerializedName;

public class AccessToken {

    @SerializedName("access")
    private String accessToken;

    public AccessToken(String accessToken){
       this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
