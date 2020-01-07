package com.sti.sti_agent.Model.AllRisk.AllRiskPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item implements Serializable
{

    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("receipt")
    @Expose
    private String receipt;
    @SerializedName("serial_number")
    @Expose
    private String serial;
    @SerializedName("imei")
    @Expose
    private String imei;

    public Item(String item, String value, String period, String receipt, String serial, String imei) {
        this.item = item;
        this.value = value;
        this.period = period;
        this.receipt = receipt;
        this.serial = serial;
        this.imei = imei;
    }
}