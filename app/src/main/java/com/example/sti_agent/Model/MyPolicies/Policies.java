package com.example.sti_agent.Model.MyPolicies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Policies implements Serializable
{

    @SerializedName("vehicle")
    @Expose
    private List<Vehicle> vehicle = null;
    @SerializedName("swiss")
    @Expose
    private List<Swis> swiss = null;
    @SerializedName("travel")
    @Expose
    private List<Travel> travel = null;
    @SerializedName("all_risk")
    @Expose
    private List<AllRisk> allRisk = null;
    @SerializedName("marine")
    @Expose
    private List<Marine> marine = null;


    public List<Vehicle> getVehicle() {
        return vehicle;
    }

    public void setVehicle(List<Vehicle> vehicle) {
        this.vehicle = vehicle;
    }

    public List<Swis> getSwiss() {
        return swiss;
    }

    public void setSwiss(List<Swis> swiss) {
        this.swiss = swiss;
    }

    public List<Travel> getTravel() {
        return travel;
    }

    public void setTravel(List<Travel> travel) {
        this.travel = travel;
    }

    public List<AllRisk> getAllRisk() {
        return allRisk;
    }

    public void setAllRisk(List<AllRisk> allRisk) {
        this.allRisk = allRisk;
    }

    public List<Marine> getMarine() {
        return marine;
    }

    public void setMarine(List<Marine> marine) {
        this.marine = marine;
    }

}