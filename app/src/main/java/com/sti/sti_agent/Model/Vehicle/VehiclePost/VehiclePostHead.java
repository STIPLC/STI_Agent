package com.sti.sti_agent.Model.Vehicle.VehiclePost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VehiclePostHead  implements Serializable
{

    @SerializedName("persona")
    @Expose
    public Persona persona;
    @SerializedName("vehicle")
    @Expose
    public List<Vehicle> vehicle = null;
    @SerializedName("quote_price")
    @Expose
    public String quotePrice;
    @SerializedName("pin")
    @Expose
    public String pin;
    @SerializedName("payment_source")
    @Expose
    public String paymentSource;
    @SerializedName("user_id")
    @Expose
    public String userId;


    public VehiclePostHead(Persona persona, List<Vehicle> vehicle, String quotePrice, String pin, String paymentSource, String userId) {
        this.persona = persona;
        this.vehicle = vehicle;
        this.quotePrice = quotePrice;
        this.pin = pin;
        this.paymentSource = paymentSource;
        this.userId = userId;
    }
}