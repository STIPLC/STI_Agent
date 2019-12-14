package com.example.sti_agent.Model.MyPolicies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Vehicle implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("policy_number")
    @Expose
    private String policyNumber;
    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("policy_type")
    @Expose
    private String policyType;
    @SerializedName("private")
    @Expose
    private String _private;
    @SerializedName("enhanced_third_party")
    @Expose
    private String enhancedThirdParty;
    @SerializedName("private_commercial")
    @Expose
    private String privateCommercial;
    @SerializedName("motor_cycle_policy")
    @Expose
    private String motorCyclePolicy;
    @SerializedName("make")
    @Expose
    private String make;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("body_type")
    @Expose
    private String bodyType;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("registration_number")
    @Expose
    private String registrationNumber;
    @SerializedName("chasis_number")
    @Expose
    private String chasisNumber;
    @SerializedName("engine_number")
    @Expose
    private String engineNumber;
    @SerializedName("vehicle_value")
    @Expose
    private String vehicleValue;
    @SerializedName("pictures")
    @Expose
    private String pictures;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("payment_reference")
    @Expose
    private String paymentReference;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("price")
    @Expose
    private String price;
    private final static long serialVersionUID = -4560614301526319193L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getPrivate() {
        return _private;
    }

    public void setPrivate(String _private) {
        this._private = _private;
    }

    public String getEnhancedThirdParty() {
        return enhancedThirdParty;
    }

    public void setEnhancedThirdParty(String enhancedThirdParty) {
        this.enhancedThirdParty = enhancedThirdParty;
    }

    public String getPrivateCommercial() {
        return privateCommercial;
    }

    public void setPrivateCommercial(String privateCommercial) {
        this.privateCommercial = privateCommercial;
    }

    public String getMotorCyclePolicy() {
        return motorCyclePolicy;
    }

    public void setMotorCyclePolicy(String motorCyclePolicy) {
        this.motorCyclePolicy = motorCyclePolicy;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getChasisNumber() {
        return chasisNumber;
    }

    public void setChasisNumber(String chasisNumber) {
        this.chasisNumber = chasisNumber;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getVehicleValue() {
        return vehicleValue;
    }

    public void setVehicleValue(String vehicleValue) {
        this.vehicleValue = vehicleValue;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
