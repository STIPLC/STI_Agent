package com.example.sti_agent.Model.ProfileUpdate.ProfileImage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfileImagePostHead implements Serializable {

    @SerializedName("user")
    @Expose
    private ProfileImageUser user;

    public ProfileImagePostHead(ProfileImageUser user) {
        this.user = user;
    }

    public ProfileImageUser getUser() {
        return user;
    }

    public void setUser(ProfileImageUser user) {
        this.user = user;
    }

}
