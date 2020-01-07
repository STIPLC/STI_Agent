package com.sti.sti_agent.Model.ProfileUpdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserGetUpdateHead implements Serializable
{

    @SerializedName("user")
    @Expose
    private UserGetUpdate user;

    public UserGetUpdate getUser() {
        return user;
    }

    public void setUser(UserGetUpdate user) {
        this.user = user;
    }
}
