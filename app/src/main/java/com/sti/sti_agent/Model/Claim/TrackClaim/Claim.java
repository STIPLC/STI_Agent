package com.sti.sti_agent.Model.Claim.TrackClaim;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Claim implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("policy_id")
    @Expose
    private String policyId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("cost_estimate")
    @Expose
    private String costEstimate;
    @SerializedName("loss_estimate")
    @Expose
    private String lossEstimate;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("date_loss")
    @Expose
    private Object dateLoss;
    @SerializedName("sti_estimate")
    @Expose
    private Object stiEstimate;
    private final static long serialVersionUID = -7933375561425927656L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCostEstimate() {
        return costEstimate;
    }

    public void setCostEstimate(String costEstimate) {
        this.costEstimate = costEstimate;
    }

    public String getLossEstimate() {
        return lossEstimate;
    }

    public void setLossEstimate(String lossEstimate) {
        this.lossEstimate = lossEstimate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Object getDateLoss() {
        return dateLoss;
    }

    public void setDateLoss(Object dateLoss) {
        this.dateLoss = dateLoss;
    }

    public Object getStiEstimate() {
        return stiEstimate;
    }

    public void setStiEstimate(Object stiEstimate) {
        this.stiEstimate = stiEstimate;
    }

}
