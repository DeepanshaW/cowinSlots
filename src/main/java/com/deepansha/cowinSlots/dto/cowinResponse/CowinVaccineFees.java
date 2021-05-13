package com.deepansha.cowinSlots.dto.cowinResponse;

public class CowinVaccineFees {

    private String vaccine;
    private String fees;

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public CowinVaccineFees(String vaccine, String fees) {
        this.vaccine = vaccine;
        this.fees = fees;
    }

    public CowinVaccineFees() {
    }
}
