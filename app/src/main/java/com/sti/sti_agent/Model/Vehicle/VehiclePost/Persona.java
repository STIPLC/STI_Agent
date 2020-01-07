package com.sti.sti_agent.Model.Vehicle.VehiclePost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Persona implements Serializable
{

    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("last_name")
    @Expose
    public String lastName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("house_address")
    @Expose
    public String houseAddress;
    @SerializedName("account_number")
    @Expose
    public String accountNumber;
    @SerializedName("marital_status")
    @Expose
    public String maritalStatus;
    @SerializedName("picture")
    @Expose
    public String picture;
    @SerializedName("identification_means")
    @Expose
    public String identificationMeans;
    @SerializedName("next_of_kin")
    @Expose
    public String nextOfKin;
    @SerializedName("next_of_kin_phone")
    @Expose
    public String nextOfKinPhone;
    @SerializedName("next_of_kin_address")
    @Expose
    public String nextOfKinAddress;
    @SerializedName("business")
    @Expose
    public String business;
    @SerializedName("employer")
    @Expose
    public String employer;
    @SerializedName("employer_address")
    @Expose
    public String employerAddress;
    @SerializedName("customer_type")
    @Expose
    public String customerType;
    @SerializedName("company_name")
    @Expose
    public String companyName;
    @SerializedName("mailing_address")
    @Expose
    public String mailingAddress;
    @SerializedName("tin_number")
    @Expose
    public String tinNumber;
    @SerializedName("office_address")
    @Expose
    public String officeAddress;
    @SerializedName("contact_person")
    @Expose
    public String contactPerson;

    public Persona(String firstName, String lastName, String email, String gender, String phone, String houseAddress, String accountNumber, String maritalStatus, String picture, String identificationMeans, String nextOfKin, String nextOfKinPhone, String nextOfKinAddress, String business, String employer, String employerAddress, String customerType, String companyName, String mailingAddress, String tinNumber, String officeAddress, String contactPerson) {
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
        this.business = business;
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
