package com.example.sti_agent.Model.AllRisk.AllRiskPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AllRiskPostHead implements Serializable
{

    @SerializedName("persona")
    @Expose
    private AllriskPersona persona;
    @SerializedName("payment_source")
    @Expose
    private String paymentSource;
    @SerializedName("quote_price")
    @Expose
    private String quotePrice;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("total_items")
    @Expose
    private Integer totalItems;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public AllRiskPostHead(AllriskPersona persona, String paymentSource, String quotePrice, String pin, Integer totalItems, List<Item> items) {
        this.persona = persona;
        this.paymentSource = paymentSource;
        this.quotePrice = quotePrice;
        this.pin = pin;
        this.totalItems = totalItems;
        this.items = items;
    }
}