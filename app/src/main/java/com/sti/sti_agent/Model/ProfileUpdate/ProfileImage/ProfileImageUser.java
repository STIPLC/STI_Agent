package com.sti.sti_agent.Model.ProfileUpdate.ProfileImage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfileImageUser implements Serializable {

    @SerializedName("picture")
    @Expose
    private String picture;

    public ProfileImageUser(String picture) {
        this.picture = picture;
    }
}