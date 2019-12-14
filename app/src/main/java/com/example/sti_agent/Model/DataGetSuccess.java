package com.example.sti_agent.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DataGetSuccess implements Serializable
{

    @SerializedName("policy")
    @Expose
    private List<VehiclePoliciesGet> policy = null;
  /*  @SerializedName("customer_details")
    @Expose
    private CustomerDetails customerDetails;
    @SerializedName("wallet")
    @Expose
    private List<Wallet> wallet = null;
    @SerializedName("transactions")
    @Expose
    private List<Transaction> transactions = null;
    @SerializedName("unit_price")
    @Expose*/
    private String unitPrice;
    @SerializedName("total_price")
    @Expose
    private Integer totalPrice;


    public List<VehiclePoliciesGet> getVehiclePoliciesGet() {
        return policy;
    }

    public void setVehiclePoliciesGet(List<VehiclePoliciesGet> policy) {
        this.policy = policy;
    }

   /* public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

    public List<Wallet> getWallet() {
        return wallet;
    }

    public void setWallet(List<Wallet> wallet) {
        this.wallet = wallet;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
*/
    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

}