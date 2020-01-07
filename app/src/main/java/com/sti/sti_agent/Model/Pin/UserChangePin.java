package com.sti.sti_agent.Model.Pin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserChangePin implements Serializable {

    @SerializedName("old_pin")
    @Expose
    private String old_pin;

    @SerializedName("pin")
    @Expose
    private String pin;

    public UserChangePin(String old_pin,String pin) {
        this.pin = pin;
    }
}
