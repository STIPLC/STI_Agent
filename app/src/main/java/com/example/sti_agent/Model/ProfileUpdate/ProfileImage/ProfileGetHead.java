package com.example.sti_agent.Model.ProfileUpdate.ProfileImage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfileGetHead implements Serializable {

    @SerializedName("user")
    @Expose
    private ProfileGetUser user;

    public ProfileGetUser getUser() {
        return user;
    }

    public void setUser(ProfileGetUser user) {
        this.user = user;
    }

}
