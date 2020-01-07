
package com.sti.sti_agent.Model.CustomerModel;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerModel implements Serializable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<CustomerDetail> data = null;
    @SerializedName("message")
    @Expose
    private String message;
    private final static long serialVersionUID = -9096474505673501606L;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CustomerDetail> getData() {
        return data;
    }

    public void setData(List<CustomerDetail> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
