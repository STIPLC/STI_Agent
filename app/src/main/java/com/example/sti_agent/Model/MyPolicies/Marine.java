package com.example.sti_agent.Model.MyPolicies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Marine implements Serializable
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
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("pfi_number")
    @Expose
    private String pfiNumber;
    @SerializedName("pfi_date")
    @Expose
    private String pfiDate;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("conversion_rate")
    @Expose
    private String conversionRate;
    @SerializedName("loading_port")
    @Expose
    private String loadingPort;
    @SerializedName("discharge_port")
    @Expose
    private String dischargePort;
    @SerializedName("conveyance_mode")
    @Expose
    private String conveyanceMode;
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
    @SerializedName("interest")
    @Expose
    private String interest;
    @SerializedName("price")
    @Expose
    private String price;
    private final static long serialVersionUID = 4531034351227970257L;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPfiNumber() {
        return pfiNumber;
    }

    public void setPfiNumber(String pfiNumber) {
        this.pfiNumber = pfiNumber;
    }

    public String getPfiDate() {
        return pfiDate;
    }

    public void setPfiDate(String pfiDate) {
        this.pfiDate = pfiDate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(String conversionRate) {
        this.conversionRate = conversionRate;
    }

    public String getLoadingPort() {
        return loadingPort;
    }

    public void setLoadingPort(String loadingPort) {
        this.loadingPort = loadingPort;
    }

    public String getDischargePort() {
        return dischargePort;
    }

    public void setDischargePort(String dischargePort) {
        this.dischargePort = dischargePort;
    }

    public String getConveyanceMode() {
        return conveyanceMode;
    }

    public void setConveyanceMode(String conveyanceMode) {
        this.conveyanceMode = conveyanceMode;
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

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}