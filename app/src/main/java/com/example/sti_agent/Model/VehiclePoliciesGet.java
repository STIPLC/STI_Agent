package com.example.sti_agent.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VehiclePoliciesGet implements Serializable
{

    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("policy_number")
    @Expose
    private String policyNumber;
    @SerializedName("period")
    @Expose
    private Integer period;
    @SerializedName("policy_type")
    @Expose
    private String policyType;
    @SerializedName("enhanced_third_party")
    @Expose
    private String enhancedThirdParty;
    @SerializedName("private_commercial")
    @Expose
    private String privateCommercial;
    @SerializedName("motor_cycle_policy")
    @Expose
    private Object motorCyclePolicy;
    @SerializedName("make")
    @Expose
    private String make;
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
    @SerializedName("price")
    @Expose
    private Object price;
    @SerializedName("start")
    @Expose
    private Object start;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("status")
    @Expose
    private String status;
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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
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

    public Object getMotorCyclePolicy() {
        return motorCyclePolicy;
    }

    public void setMotorCyclePolicy(Object motorCyclePolicy) {
        this.motorCyclePolicy = motorCyclePolicy;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
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

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

    public Object getStart() {
        return start;
    }

    public void setStart(Object start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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