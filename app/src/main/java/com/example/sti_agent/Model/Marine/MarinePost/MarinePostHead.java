package com.example.sti_agent.Model.Marine.MarinePost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MarinePostHead implements Serializable
{

    @SerializedName("persona")
    @Expose
    private MarinePersona persona;
    @SerializedName("cargoes")
    @Expose
    private List<Cargo> cargoes = null;
    @SerializedName("quote_price")
    @Expose
    private String quotePrice;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("payment_source")
    @Expose
    private String paymentSource;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public MarinePostHead(MarinePersona persona, List<Cargo> cargoes, String quotePrice, String pin, String paymentSource, String userId) {
        this.persona = persona;
        this.cargoes = cargoes;
        this.quotePrice = quotePrice;
        this.pin = pin;
        this.paymentSource = paymentSource;
        this.userId = userId;
    }
}