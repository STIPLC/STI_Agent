package com.example.sti_agent.Model.Etic.FormSuccessDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomerDetails implements Serializable
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
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("picture")
        @Expose
        private String picture;
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
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("id")
        @Expose
        private Integer id;

        public String getTitle() {
        return title;
    }

        public void setTitle(String title) {
        this.title = title;
    }

        public String getFirstName() {
        return firstName;
    }

        public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

        public String getLastName() {
        return lastName;
    }

        public void setLastName(String lastName) {
        this.lastName = lastName;
    }

        public String getEmail() {
        return email;
    }

        public void setEmail(String email) {
        this.email = email;
    }

        public String getAccountNumber() {
        return accountNumber;
    }

        public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

        public String getGender() {
        return gender;
    }

        public void setGender(String gender) {
        this.gender = gender;
    }

        public String getAddress() {
        return address;
    }

        public void setAddress(String address) {
        this.address = address;
    }

        public String getPhone() {
        return phone;
    }

        public void setPhone(String phone) {
        this.phone = phone;
    }

        public String getPicture() {
        return picture;
    }

        public void setPicture(String picture) {
        this.picture = picture;
    }

        public String getEmployer() {
        return employer;
    }

        public void setEmployer(String employer) {
        this.employer = employer;
    }

        public String getEmployerAddress() {
        return employerAddress;
    }

        public void setEmployerAddress(String employerAddress) {
        this.employerAddress = employerAddress;
    }

        public String getEmployerPhone() {
        return employerPhone;
    }

        public void setEmployerPhone(String employerPhone) {
        this.employerPhone = employerPhone;
    }

        public String getEmployerEmail() {
        return employerEmail;
    }

        public void setEmployerEmail(String employerEmail) {
        this.employerEmail = employerEmail;
    }

        public Integer getUserId() {
        return userId;
    }

        public void setUserId(Integer userId) {
        this.userId = userId;
    }

        public String getUpdatedAt() {
        return updatedAt;
    }

        public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

        public String getCreatedAt() {
        return createdAt;
    }

        public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

        public Integer getId() {
        return id;
    }

        public void setId(Integer id) {
        this.id = id;
    }

    }