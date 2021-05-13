package com.deepansha.cowinSlots.dto;

import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AlertDTO {

    private int id;

    private String name;
    private String state;
    private String city;
    private int pincode;
    private int districtId;
    private String phoneNumber;
    private boolean active;
    private int age;
    private String vaccineType;
    private boolean isPinCodeSearch;
    private String notificationType; // 0 Whatsapp, 1 sms, 2 Email, 3 Telegram {Comma Seperated List Expected}
    private String emailAddress;
    private Date createdAt;
    private Date modifiedAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getId() {
        return id;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getVaccineType() {
        return vaccineType;
    }

    public void setVaccineType(String vaccineType) {
        this.vaccineType = vaccineType;
    }

    public boolean isPinCodeSearch() {
        return isPinCodeSearch;
    }

    public void setPinCodeSearch(boolean isPinCodeSearch) {
        this.isPinCodeSearch = isPinCodeSearch;
    }

    @Override
    public String toString() {
        return "AlertDTO [active=" + active + ", age=" + age + ", city=" + city + ", districtId=" + districtId
                + ", emailAddress=" + emailAddress + ", id=" + id + ", isPinCodeSearch=" + isPinCodeSearch + ", name="
                + name + ", notificationType=" + notificationType + ", phoneNumber=" + phoneNumber + ", pincode="
                + pincode + ", state=" + state + ", vaccineType=" + vaccineType + "]";
    }

    public AlertDTO(String name, String state, String city, int pincode, int districtId, String phoneNumber,
                    boolean active, int age, String vaccineType, boolean isPinCodeSearch, String notificationType,
                    String emailAddress) {
        this.name = name;
        this.state = state;
        this.city = city;
        this.pincode = pincode;
        this.districtId = districtId;
        this.phoneNumber = phoneNumber;
        this.active = active;
        this.age = age;
        this.vaccineType = vaccineType;
        this.isPinCodeSearch = isPinCodeSearch;
        this.notificationType = notificationType;
        this.emailAddress = emailAddress;
    }

    public AlertDTO(int id, String name, String state, String city, int pincode, int districtId, String phoneNumber,
                    boolean active, int age, String vaccineType, boolean isPinCodeSearch, String notificationType,
                    String emailAddress) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.city = city;
        this.pincode = pincode;
        this.districtId = districtId;
        this.phoneNumber = phoneNumber;
        this.active = active;
        this.age = age;
        this.vaccineType = vaccineType;
        this.isPinCodeSearch = isPinCodeSearch;
        this.notificationType = notificationType;
        this.emailAddress = emailAddress;
    }

    public AlertDTO() {
    }
}
