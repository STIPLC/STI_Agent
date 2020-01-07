package com.sti.sti_agent.Model.Vehicle.Quote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostVehicleData implements Serializable
{

    @SerializedName("vehicle_value")
    @Expose
    private String vehicleValue;
    @SerializedName("private_commercial")
    @Expose
    private String privateCommercial;
    @SerializedName("third_party_type")
    @Expose
    private String thirdPartyType;
    @SerializedName("policy_type")
    @Expose
    private String policyType;
    @SerializedName("vehicle_type")
    @Expose
    private String vehicleType;


    public PostVehicleData(String vehicleValue, String privateCommercial, String thirdPartyType, String policyType, String vehicleType) {
        this.vehicleValue = vehicleValue;
        this.privateCommercial = privateCommercial;
        this.thirdPartyType = thirdPartyType;
        this.policyType = policyType;
        this.vehicleType = vehicleType;
    }
}