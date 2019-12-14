package com.example.sti_agent.Model.Swiss.SwissPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SwissPostHead implements Serializable
{

    @SerializedName("persona")
    @Expose
    private SwissPersona persona;
    @SerializedName("swiss")
    @Expose
    private Swiss swiss;
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

    public SwissPostHead(SwissPersona persona, Swiss swiss, String quotePrice, String pin, String paymentSource, String userId) {
        this.persona = persona;
        this.swiss = swiss;
        this.quotePrice = quotePrice;
        this.pin = pin;
        this.paymentSource = paymentSource;
        this.userId = userId;
    }
}