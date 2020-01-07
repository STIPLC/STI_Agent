package com.sti.sti_agent.Model.Etic.EticPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EticPersona implements Serializable
{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("account_number")
    @Expose
    private String accountNumber;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("house_address")
    @Expose
    private String houseAddress;
    @SerializedName("employer")
    @Expose
    private String employer;
    @SerializedName("employer_address")
    @Expose
    private String employerAddress;
    @SerializedName("employer_phone")
    @Expose
    private String employerPhone;
    @SerializedName("employer_email")
    @Expose
    private String employerEmail;
    @SerializedName("cover_commencement_date")
    @Expose
    private String coverCommencementDate;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("next_of_kin")
    @Expose
    private String nextOfKin;
    @SerializedName("next_of_kin_address")
    @Expose
    private String nextOfKinAddress;
    @SerializedName("next_of_kin_phone")
    @Expose
    private String nextOfKinPhone;
    @SerializedName("identification_means")
    @Expose
    private String identificationMeans;


    public EticPersona(String title, String firstName, String lastName, String email, String accountNumber, String phone, String gender, String houseAddress, String employer, String employerAddress, String employerPhone, String employerEmail, String coverCommencementDate, String picture, String nextOfKin, String nextOfKinAddress, String nextOfKinPhone, String identificationMeans) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accountNumber = accountNumber;
        this.phone = phone;
        this.gender = gender;
        this.houseAddress = houseAddress;
        this.employer = employer;
        this.employerAddress = employerAddress;
        this.employerPhone = employerPhone;
        this.employerEmail = employerEmail;
        this.coverCommencementDate = coverCommencementDate;
        this.picture = picture;
        this.nextOfKin = nextOfKin;
        this.nextOfKinAddress = nextOfKinAddress;
        this.nextOfKinPhone = nextOfKinPhone;
        this.identificationMeans = identificationMeans;
    }
}
