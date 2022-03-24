package com.example.sharkle;

import com.google.gson.annotations.SerializedName;

//회원가입시 정보 class
public class SignUp
{
    @SerializedName("email")
    private String email;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("password")
    private String password;

    @SerializedName("username")
    private String userName;

    public SignUp(String email, String userId, String password, String userName){
        this.email = email;
        this.userId = userId;
        this.password = password;
        this.userName = userName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }
}
