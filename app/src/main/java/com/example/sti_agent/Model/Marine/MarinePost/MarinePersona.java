package com.example.sti_agent.Model.Marine.MarinePost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MarinePersona implements Serializable
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
    @SerializedName("identification_means")
    @Expose
    private String identificationMeans;
    @SerializedName("next_of_kin")
    @Expose
    private String nextOfKin;
    @SerializedName("next_of_kin_phone")
    @Expose
    private String nextOfKinPhone;
    @SerializedName("next_of_kin_address")
    @Expose
    private String nextOfKinAddress;
    @SerializedName("employer")
    @Expose
    private String employer;
    @SerializedName("employer_address")
    @Expose
    private String employerAddress;
    @SerializedName("customer_type")
    @Expose
    private String customerType;
    @SerializedName("company")
    @Expose
    private String companyName;
    @SerializedName("mailing_address")
    @Expose
    private String mailingAddress;
    @SerializedName("tin_number")
    @Expose
    private String tinNumber;
    @SerializedName("office_address")
    @Expose
    private String officeAddress;
    @SerializedName("contact_person")
    @Expose
    private String contactPerson;

    public MarinePersona(String firstName, String lastName, String email, String gender, String phone, String houseAddress, String accountNumber, String maritalStatus, String picture, String identificationMeans, String nextOfKin, String nextOfKinPhone, String nextOfKinAddress, String employer, String employerAddress, String customerType, String companyName, String mailingAddress, String tinNumber, String officeAddress, String contactPerson) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.houseAddress = houseAddress;
        this.accountNumber = accountNumber;
        this.maritalStatus = maritalStatus;
        this.picture = picture;
        this.identificationMeans = identificationMeans;
        this.nextOfKin = nextOfKin;
        this.nextOfKinPhone = nextOfKinPhone;
        this.nextOfKinAddress = nextOfKinAddress;
        this.employer = employer;
        this.employerAddress = employerAddress;
        this.customerType = customerType;
        this.companyName = companyName;
        this.mailingAddress = mailingAddress;
        this.tinNumber = tinNumber;
        this.officeAddress = officeAddress;
        this.contactPerson = contactPerson;
    }
}