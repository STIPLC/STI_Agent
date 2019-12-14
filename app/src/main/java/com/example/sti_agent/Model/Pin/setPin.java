package com.example.sti_agent.Model.Pin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class setPin implements Serializable
{

    @SerializedName("user")
    @Expose
    private UserPin user;

    public setPin(UserPin user) {
        this.user = user;
    }
}