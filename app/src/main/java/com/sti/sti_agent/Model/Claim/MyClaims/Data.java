package com.sti.sti_agent.Model.Claim.MyClaims;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable
{

    @SerializedName("claims")
    @Expose
    private List<Claim> claims = null;

    public List<Claim> getClaims() {
        return claims;
    }

    public void setClaims(List<Claim> claims) {
        this.claims = claims;
    }

}
