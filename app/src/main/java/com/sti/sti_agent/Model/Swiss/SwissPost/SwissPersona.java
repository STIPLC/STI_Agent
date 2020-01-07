package com.sti.sti_agent.Model.Swiss.SwissPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SwissPersona implements Serializable
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
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("house_address")
    @Expose
    private String houseAddress;
    @SerializedName("account_number")
    @Expose
    private String accountNumber;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("next_of_kin")
    @Expose
    private String nextOfKin;
    @SerializedName("next_of_kin_phone")
    @Expose
    private String nextOfKinPhone;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("next_of_kin_address")
    @Expose
    private String nextOfKinAddress;
    @SerializedName("disability")
    @Expose
    private String disability;
    @SerializedName("additional_insured")
    @Expose
    private List<AdditionalInsuredPost> additionalInsuredPost = null;

    public SwissPersona(String firstName, String lastName, String email, String gender, String dateOfBirth, String phone, String houseAddress, String accountNumber, String maritalStatus, String picture, String nextOfKin, String nextOfKinPhone, String price, String nextOfKinAddress, String disability, List<AdditionalInsuredPost> additionalInsuredPost) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.houseAddress = houseAddress;
        this.accountNumber = accountNumber;
        this.maritalStatus = maritalStatus;
        this.picture = picture;
        this.nextOfKin = nextOfKin;
        this.nextOfKinPhone = nextOfKinPhone;
        this.price = price;
        this.nextOfKinAddress = nextOfKinAddress;
        this.disability = disability;
        this.additionalInsuredPost = additionalInsuredPost;
    }


    public SwissPersona(String firstName, String lastName, String email, String gender, String dateOfBirth, String phone, String houseAddress, String accountNumber, String maritalStatus, String picture, String nextOfKin, String nextOfKinPhone, String price, String nextOfKinAddress, String disability) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.houseAddress = houseAddress;
        this.accountNumber = accountNumber;
        this.maritalStatus = maritalStatus;
        this.picture = picture;
        this.nextOfKin = nextOfKin;
        this.nextOfKinPhone = nextOfKinPhone;
        this.price = price;
        this.nextOfKinAddress = nextOfKinAddress;
        this.disability = disability;

    }
}