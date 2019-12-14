package com.example.sti_agent.Model.Claim;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ClaimPost implements Serializable
{

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("claim_type")
    @Expose
    private String claimType;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("policy_number")
    @Expose
    private String policyNumber;
    @SerializedName("loss_estimate")
    @Expose
    private Integer lossEstimate;
    @SerializedName("cost_estimate")
    @Expose
    private String costEstimate;
    @SerializedName("pictures")
    @Expose
    private List<String> pictures = null;
    @SerializedName("pin")
    @Expose
    private String pin;


    public ClaimPost(String description, String claimType, String paymentStatus, String policyNumber, Integer lossEstimate, String costEstimate, List<String> pictures, String pin) {
        this.description = description;
        this.claimType = claimType;
        this.paymentStatus = paymentStatus;
        this.policyNumber = policyNumber;
        this.lossEstimate = lossEstimate;
        this.costEstimate = costEstimate;
        this.pictures = pictures;
        this.pin = pin;
    }

    public ClaimPost(String description, String claimType, String paymentStatus, String policyNumber, Integer lossEstimate, List<String> pictures, String pin) {
        this.description = description;
        this.claimType = claimType;
        this.paymentStatus = paymentStatus;
        this.policyNumber = policyNumber;
        this.lossEstimate = lossEstimate;
        this.pictures = pictures;
        this.pin = pin;
    }
}
