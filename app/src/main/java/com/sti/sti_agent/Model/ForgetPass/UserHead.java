package com.sti.sti_agent.Model.ForgetPass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserHead implements Serializable
{

    @SerializedName("user")
    @Expose
    private UserEmail user;

    public UserHead(UserEmail user) {
        this.user = user;
    }


}