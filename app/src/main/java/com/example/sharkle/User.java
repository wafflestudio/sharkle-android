package com.example.sharkle;

import com.google.gson.annotations.SerializedName;

//로그인에 필요한 요소 email, password

public class User
{
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
