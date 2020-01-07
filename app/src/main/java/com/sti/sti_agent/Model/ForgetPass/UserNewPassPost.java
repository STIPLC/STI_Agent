package com.sti.sti_agent.Model.ForgetPass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserNewPassPost implements Serializable
{

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("new_password")
    @Expose
    private String new_password;

    public UserNewPassPost(String email, String token, String new_password) {
        this.email = email;
        this.token = token;
        this.new_password = new_password;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public String getNew_password() {
        return new_password;
    }
}

