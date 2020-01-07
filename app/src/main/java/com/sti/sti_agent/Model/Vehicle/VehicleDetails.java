package com.sti.sti_agent.Model.Vehicle;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import static java.util.UUID.randomUUID;

public class VehicleDetails extends RealmObject {

    String period;
    String startDate;


    String private_com_type;
    String enhanced_third_party;

    String policy_select_type;
    String vehicle_make;
    String vehicle_type;
    String body_type;
    String year;
    String registration_number;
    String chasis_number;
    String engine_number;
    String vehicle_value;
    String price;



    String motorcylce_value;

    public VehicleDetails(){

    }

    VehiclePictures vehiclePictures;

    @PrimaryKey
    public String id=randomUUID().toString();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPrivate_com_type() {
        return private_com_type;
    }

    public void setPrivate_com_type(String private_com_type) {
        this.private_com_type = private_com_type;
    }

    public String getPolicy_select_type() {
        return policy_select_type;
    }

    public void setPolicy_select_type(String policy_select_type) {
        this.policy_select_type = policy_select_type;
    }

    public String getEnhanced_third_party() {
        return enhanced_third_party;
    }

    public void setEnhanced_third_party(String enhanced_third_party) {
        this.enhanced_third_party = enhanced_third_party;
    }


    public String getVehicle_make() {
        return vehicle_make;
    }

    public void setVehicle_make(String vehicle_make) {
        this.vehicle_make = vehicle_make;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getBody_type() {
        return body_type;
    }

    public void setBody_type(String body_type) {
        this.body_type = body_type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }

    public String getChasis_number() {
        return chasis_number;
    }

    public void setChasis_number(String chasis_number) {
        this.chasis_number = chasis_number;
    }

    public String getEngine_number() {
        return engine_number;
    }

    public void setEngine_number(String engine_number) {
        this.engine_number = engine_number;
    }

    public String getMotorcylce_value() {
        return motorcylce_value;
    }

    public void setMotorcylce_value(String motorcylce_value) {
        this.motorcylce_value = motorcylce_value;
    }

    public String getVehicle_value() {
        return vehicle_value;
    }

    public void setVehicle_value(String vehicle_value) {
        this.vehicle_value = vehicle_value;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public VehiclePictures getVehiclePictures() {
        return vehiclePictures;
    }

    public void setVehiclePictures(VehiclePictures vehiclePictures) {
        this.vehiclePictures = vehiclePictures;
    }
}
