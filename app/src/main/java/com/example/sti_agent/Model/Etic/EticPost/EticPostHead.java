package com.example.sti_agent.Model.Etic.EticPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EticPostHead implements Serializable
{

    @SerializedName("persona")
    @Expose
    private EticPersona persona;
    @SerializedName("payment_source")
    @Expose
    private String paymentSource;
    @SerializedName("quote_price")
    @Expose
    private String quotePrice;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("trip")
    @Expose
    private Trip trip;


    public EticPostHead(EticPersona persona, String paymentSource, String quotePrice, String pin, Trip trip) {
        this.persona = persona;
        this.paymentSource = paymentSource;
        this.quotePrice = quotePrice;
        this.pin = pin;
        this.trip = trip;
    }
}
