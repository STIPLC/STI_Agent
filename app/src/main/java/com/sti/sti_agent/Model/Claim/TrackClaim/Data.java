package com.sti.sti_agent.Model.Claim.TrackClaim;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable
{

    @SerializedName("claim")
    @Expose
    private Claim claim;

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

}