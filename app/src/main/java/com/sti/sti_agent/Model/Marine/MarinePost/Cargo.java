package com.sti.sti_agent.Model.Marine.MarinePost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Cargo implements Serializable
{

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
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("pictures")
    @Expose
    private List<String> pictures = null;

    public Cargo(String period, String policyType, String description, String pfiNumber, String pfiDate, String quantity, String value, String conversionRate, String loadingPort, String dischargePort, String conveyanceMode, String price, List<String> pictures) {
        this.period = period;
        this.policyType = policyType;
        this.description = description;
        this.pfiNumber = pfiNumber;
        this.pfiDate = pfiDate;
        this.quantity = quantity;
        this.value = value;
        this.conversionRate = conversionRate;
        this.loadingPort = loadingPort;
        this.dischargePort = dischargePort;
        this.conveyanceMode = conveyanceMode;
        this.price = price;
        this.pictures = pictures;
    }
}