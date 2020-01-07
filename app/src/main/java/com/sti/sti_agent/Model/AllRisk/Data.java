package com.sti.sti_agent.Model.AllRisk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable
{

    @SerializedName("price")
    @Expose
    private long price;



    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

}
