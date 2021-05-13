package com.deepansha.cowinSlots.dto.cowinResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CowinResponseSessions {

    private String session_id;
    private String date;
    private int available_capacity;
    private int min_age_limit;
    private String vaccine;
    private List<String> slots;

    @Override
    public String toString() {
        return "CowinResponseSessions [available_capacity=" + available_capacity + ", date=" + date + ", min_age_limit="
                + min_age_limit + ", session_id=" + session_id + ", slots=" + slots + ", vaccine=" + vaccine + "]";
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAvailable_capacity() {
        return available_capacity;
    }

    public void setAvailable_capacity(int available_capacity) {
        this.available_capacity = available_capacity;
    }

    public int getMin_age_limit() {
        return min_age_limit;
    }

    public void setMin_age_limit(int min_age_limit) {
        this.min_age_limit = min_age_limit;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public List<String> getSlots() {
        return slots;
    }

    public void setSlots(List<String> slots) {
        this.slots = slots;
    }

    public String getVaccineAVLResponseString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Slot - ");
        builder.append(this.getDate());
        builder.append(" for Type - ");
        builder.append(this.getVaccine());
        builder.append(" with quantity available - ");
        builder.append(this.getAvailable_capacity());
        builder.append(" for Age Limit - ");
        builder.append(this.getMin_age_limit());
        return builder.toString();
    }
}
