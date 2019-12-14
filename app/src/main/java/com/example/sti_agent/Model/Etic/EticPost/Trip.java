package com.example.sti_agent.Model.Etic.EticPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Trip implements Serializable
{

    @SerializedName("trip_duration")
    @Expose
    private String tripDuration;
    @SerializedName("travel_mode")
    @Expose
    private String travelMode;
    @SerializedName("disability")
    @Expose
    private String disability;
    @SerializedName("disability_details")
    @Expose
    private String disabilityDetails;
    @SerializedName("place_departure")
    @Expose
    private String placeDeparture;
    @SerializedName("place_arrival")
    @Expose
    private String placeArrival;
    @SerializedName("address_country_of_visit")
    @Expose
    private String addressCountryOfVisit;

    public Trip(String tripDuration, String travelMode, String disability, String disabilityDetails, String placeDeparture, String placeArrival, String addressCountryOfVisit) {
        this.tripDuration = tripDuration;
        this.travelMode = travelMode;
        this.disability = disability;
        this.disabilityDetails = disabilityDetails;
        this.placeDeparture = placeDeparture;
        this.placeArrival = placeArrival;
        this.addressCountryOfVisit = addressCountryOfVisit;
    }
}