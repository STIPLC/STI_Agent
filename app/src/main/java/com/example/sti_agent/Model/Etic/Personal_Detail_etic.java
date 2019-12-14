package com.example.sti_agent.Model.Etic;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Personal_Detail_etic extends RealmObject {

    String first_name;
    String last_name;
    String email;
    String mailing_addr;
    String gender;
    String phone;
    String resident_address;
    String marital_status;

    String next_of_kin;
    String next_of_kin_phone;
    String next_of_kin_address;
    String prefix;
    String business;
    String nationality;
    String employer_name;
    String employer_addr;
    String state;
    String intnded_start_dateCover;
    String end_dateCover;


    String picture;
    RealmList<Travel_Info> travel_infos;


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMailing_addr() {
        return mailing_addr;
    }

    public void setMailing_addr(String mailing_addr) {
        this.mailing_addr = mailing_addr;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getResident_address() {
        return resident_address;
    }

    public void setResident_address(String resident_address) {
        this.resident_address = resident_address;
    }


    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmployer_name() {
        return employer_name;
    }

    public void setEmployer_name(String employer_name) {
        this.employer_name = employer_name;
    }

    public String getEmployer_addr() {
        return employer_addr;
    }

    public void setEmployer_addr(String employer_addr) {
        this.employer_addr = employer_addr;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIntnded_start_dateCover() {
        return intnded_start_dateCover;
    }

    public void setIntnded_start_dateCover(String intnded_start_dateCover) {
        this.intnded_start_dateCover = intnded_start_dateCover;
    }

    public String getEnd_dateCover() {
        return end_dateCover;
    }

    public void setEnd_dateCover(String end_dateCover) {
        this.end_dateCover = end_dateCover;
    }


    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getNext_of_kin() {
        return next_of_kin;
    }

    public void setNext_of_kin(String next_of_kin) {
        this.next_of_kin = next_of_kin;
    }

    public String getNext_of_kin_phone() {
        return next_of_kin_phone;
    }

    public void setNext_of_kin_phone(String next_of_kin_phone) {
        this.next_of_kin_phone = next_of_kin_phone;
    }

    public String getNext_of_kin_address() {
        return next_of_kin_address;
    }

    public void setNext_of_kin_address(String next_of_kin_address) {
        this.next_of_kin_address = next_of_kin_address;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public RealmList<Travel_Info> getTravel_infos() {
        return travel_infos;
    }

    public void setTravel_infos(RealmList<Travel_Info> travel_infos) {
        this.travel_infos = travel_infos;
    }



}
