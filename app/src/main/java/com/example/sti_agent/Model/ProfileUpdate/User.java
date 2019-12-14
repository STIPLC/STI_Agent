package com.example.sti_agent.Model.ProfileUpdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable
{

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;

    public User(String email, String phone, String username, String firstName, String lastName) {
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}