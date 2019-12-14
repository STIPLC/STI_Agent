package com.example.sti_agent.Model.Etic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable
{

    @SerializedName("price")
    @Expose
    private String price;



    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
