package com.sti.sti_agent.Model.ProfileUpdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserEditHead  implements Serializable
{

    @SerializedName("user")
    @Expose
    private User user;

    public UserEditHead(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
