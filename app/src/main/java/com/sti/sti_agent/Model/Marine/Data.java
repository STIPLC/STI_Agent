package com.sti.sti_agent.Model.Marine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable
{

    @SerializedName("price")
    @Expose
    private double price;

    @SerializedName("sum_insured")
    @Expose
    private double sum_insured;


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSum_insured() {
        return sum_insured;
    }

    public void setSum_insured(double sum_insured) {
        this.sum_insured = sum_insured;
    }
}
