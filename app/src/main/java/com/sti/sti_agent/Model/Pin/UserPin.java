package com.sti.sti_agent.Model.Pin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserPin implements Serializable
{

    @SerializedName("pin")
    @Expose
    private String pin;

    public UserPin(String pin) {
        this.pin = pin;
    }
}
