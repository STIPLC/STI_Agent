package com.example.sti_agent.Model.Swiss.SwissPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Swiss implements Serializable {

    @SerializedName("period")
    @Expose
    private String period;

    public Swiss(String period) {
        this.period = period;
    }
}
