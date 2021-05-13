package com.deepansha.cowinSlots.dto.cowinResponse;

import java.util.List;

public class CowinResponseCenter {

    private int center_id;
    private String name;
    private String address;
    private String state_name;
    private String district_name;
    private String block_name;
    private int pincode;
    private int lat;
    // private int long;
    private String from;
    private String to;
    private String fee_type;
    private List<CowinResponseSessions> sessions;
    private List<CowinVaccineFees> vaccineFees;

    @Override
    public String toString() {
        return "CowinResponseCenter [address=" + address + ", block_name=" + block_name + ", center_id=" + center_id
                + ", district_name=" + district_name + ", fee_type=" + fee_type + ", from=" + from + ", lat=" + lat
                + ", name=" + name + ", pincode=" + pincode + ", sessions=" + sessions + ", state_name=" + state_name
                + ", to=" + to + "]";
    }

    public List<CowinVaccineFees> getVaccineFees() {
        return vaccineFees;
    }

    public void setVaccineFees(List<CowinVaccineFees> vaccineFees) {
        this.vaccineFees = vaccineFees;
    }

    public int getCenter_id() {
        return center_id;
    }

    public void setCenter_id(int center_id) {
        this.center_id = center_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getBlock_name() {
        return block_name;
    }

    public void setBlock_name(String block_name) {
        this.block_name = block_name;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public List<CowinResponseSessions> getSessions() {
        return sessions;
    }

    public void setSessions(List<CowinResponseSessions> sessions) {
        this.sessions = sessions;
    }

}
