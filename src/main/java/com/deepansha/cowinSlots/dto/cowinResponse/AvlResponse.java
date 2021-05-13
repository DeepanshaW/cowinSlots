package com.deepansha.cowinSlots.dto.cowinResponse;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class AvlResponse {

    private int centerId;
    private String centerName;
    private String fees;
    private String centerAddress;
    private int pincode;
    private Set<CowinResponseSessions> sessions;

    public AvlResponse() {
    }

    public AvlResponse(int centerId, String centerName, String centerAddress, int pincode,
                       Set<CowinResponseSessions> sessions) {
        this.centerId = centerId;
        this.centerName = centerName;
        this.centerAddress = centerAddress;
        this.pincode = pincode;
        this.sessions = sessions;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public String getCenterName() {
        return this.centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCenterAddress() {
        return this.centerAddress;
    }

    public void setCenterAddress(String centerAddress) {
        this.centerAddress = centerAddress;
    }

    public int getPincode() {
        return this.pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public Set<CowinResponseSessions> getSessions() {
        return this.sessions;
    }

    public void setSessions(Set<CowinResponseSessions> sessions) {
        this.sessions = sessions;
    }

    public AvlResponse centerName(String centerName) {
        setCenterName(centerName);
        return this;
    }

    public AvlResponse centerAddress(String centerAddress) {
        setCenterAddress(centerAddress);
        return this;
    }

    public AvlResponse pincode(int pincode) {
        setPincode(pincode);
        return this;
    }

    public AvlResponse sessions(Set<CowinResponseSessions> sessions) {
        setSessions(sessions);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AvlResponse)) {
            return false;
        }
        AvlResponse avlResponse = (AvlResponse) o;
        return avlResponse.getCenterId() == this.centerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerName, centerAddress, pincode, sessions);
    }

    @Override
    public String toString() {
        return "{" + " centerName='" + getCenterName() + "'" + ", centerAddress='" + getCenterAddress() + "'"
                + ", pincode='" + getPincode() + "'" + ", sessions='" + getSessions() + "'" + "}";
    }

    public String getVaccineAVLResponseString() {
        StringBuilder vaccineResponse = new StringBuilder();
        vaccineResponse.append("Vaccine is available as per your Alert Request");
        vaccineResponse.append("at center : ");
        vaccineResponse.append(this.getCenterName());
        vaccineResponse.append(" located at ");
        vaccineResponse.append(this.getCenterAddress());
        vaccineResponse.append(" ");
        vaccineResponse.append(this.getPincode());
        vaccineResponse.append(" for the following sessions ");

        if (this.getSessions() == null || this.getSessions().size() > 0) {
            return "";
        }

        Iterator<CowinResponseSessions> vldSessions = this.getSessions().iterator();
        while (vldSessions.hasNext()) {
            CowinResponseSessions session = vldSessions.next();
            if (session.getAvailable_capacity() > 0) {
                vaccineResponse.append("\n ");
                vaccineResponse.append(session.getVaccineAVLResponseString());
                vaccineResponse.append(" ");
            }
        }

        vaccineResponse.append("\n");
        return vaccineResponse.toString();
    }
}
