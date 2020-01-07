package com.sti.sti_agent.Model.Etic.FormSuccessDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Policy implements Serializable
{

    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("policy_number")
    @Expose
    private String policyNumber;
    @SerializedName("trip_duration")
    @Expose
    private String tripDuration;
    @SerializedName("place_departure")
    @Expose
    private String placeDeparture;
    @SerializedName("place_arrival")
    @Expose
    private String placeArrival;
    @SerializedName("travel_mode")
    @Expose
    private String travelMode;
    @SerializedName("address_country_of_visit")
    @Expose
    private String addressCountryOfVisit;
    @SerializedName("disability")
    @Expose
    private String disability;
    @SerializedName("disability_details")
    @Expose
    private String disabilityDetails;
    @SerializedName("policy_type")
    @Expose
    private String policyType;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("payment_reference")
    @Expose
    private String paymentReference;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getTripDuration() {
        return tripDuration;
    }

    public void setTripDuration(String tripDuration) {
        this.tripDuration = tripDuration;
    }

    public String getPlaceDeparture() {
        return placeDeparture;
    }

    public void setPlaceDeparture(String placeDeparture) {
        this.placeDeparture = placeDeparture;
    }

    public String getPlaceArrival() {
        return placeArrival;
    }

    public void setPlaceArrival(String placeArrival) {
        this.placeArrival = placeArrival;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public String getAddressCountryOfVisit() {
        return addressCountryOfVisit;
    }

    public void setAddressCountryOfVisit(String addressCountryOfVisit) {
        this.addressCountryOfVisit = addressCountryOfVisit;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getDisabilityDetails() {
        return disabilityDetails;
    }

    public void setDisabilityDetails(String disabilityDetails) {
        this.disabilityDetails = disabilityDetails;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}