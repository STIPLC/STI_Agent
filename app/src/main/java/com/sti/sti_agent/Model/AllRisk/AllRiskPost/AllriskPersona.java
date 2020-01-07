package com.sti.sti_agent.Model.AllRisk.AllRiskPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AllriskPersona implements Serializable
{

    @SerializedName("customer_type")
    @Expose
    public String customerType;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("last_name")
    @Expose
    public String lastName;
    @SerializedName("company_name")
    @Expose
    public String companyName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("account_number")
    @Expose
    public String accountNumber;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("house_address")
    @Expose
    public String houseAddress;
    @SerializedName("office_address")
    @Expose
    public String officeAddress;
    @SerializedName("mailing_address")
    @Expose
    public String mailingAddress;
    @SerializedName("next_of_kin")
    @Expose
    public String nextOfKin;
    @SerializedName("contact_person")
    @Expose
    public String contactPerson;
    @SerializedName("picture")
    @Expose
    public String picture;


    public AllriskPersona(String customerType, String title, String firstName, String lastName, String companyName, String email, String accountNumber, String phone, String gender, String houseAddress, String officeAddress, String mailingAddress, String nextOfKin, String contactPerson, String picture) {
        this.customerType = customerType;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.email = email;
        this.accountNumber = accountNumber;
        this.phone = phone;
        this.gender = gender;
        this.houseAddress = houseAddress;
        this.officeAddress = officeAddress;
        this.mailingAddress = mailingAddress;
        this.nextOfKin = nextOfKin;
        this.contactPerson = contactPerson;
        this.picture = picture;
    }
}
