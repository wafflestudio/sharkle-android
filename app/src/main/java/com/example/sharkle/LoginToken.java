package com.example.sharkle;

import com.google.gson.annotations.SerializedName;

public class LoginToken {

    @SerializedName("refresh")
    private String refreshToken;
    @SerializedName("access")
    private String accessToken;

    public LoginToken(String refreshToken, String accessToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken()
    {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken)
    {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

}
