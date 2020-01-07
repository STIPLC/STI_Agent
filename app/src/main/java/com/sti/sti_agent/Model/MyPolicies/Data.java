package com.sti.sti_agent.Model.MyPolicies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable
{

    @SerializedName("policies")
    @Expose
    private Policies policies;


    public Policies getPolicies() {
        return policies;
    }

    public void setPolicies(Policies policies) {
        this.policies = policies;
    }

}
