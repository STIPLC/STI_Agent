package com.sti.sti_agent.Model.Pin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class changePin implements Serializable
{

    @SerializedName("user")
    @Expose
    private UserChangePin user;

    public changePin(UserChangePin user) {
        this.user = user;
    }
}