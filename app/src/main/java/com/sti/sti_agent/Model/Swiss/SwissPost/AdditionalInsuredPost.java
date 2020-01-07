package com.sti.sti_agent.Model.Swiss.SwissPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AdditionalInsuredPost implements Serializable
{

    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("disability")
    @Expose
    private String disability;
    @SerializedName("price")
    @Expose
    private String price;

    public AdditionalInsuredPost(String firstName, String lastName, String email, String gender, String phone, String dateOfBirth, String maritalStatus, String picture, String disability, String price) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.maritalStatus = maritalStatus;
        this.picture = picture;
        this.disability = disability;
        this.price = price;
    }
}