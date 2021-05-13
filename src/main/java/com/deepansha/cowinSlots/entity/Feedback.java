package com.deepansha.cowinSlots.entity;

import javax.persistence.*;

@Entity
@Table(name = "feedback")
public class Feedback {


    private String characterFilter = "[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\s]";

    public Feedback() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

    public Feedback(String phoneNumber, String feedback) {
        this.phoneNumber = phoneNumber;
        this.feedback = getFeedBackFormatted(feedback);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = getFeedBackFormatted(feedback);
    }

    private String getFeedBackFormatted(String feedback) {
        if (null == feedback) {
            return "";
        }

        return feedback.replaceAll(characterFilter, "__EMOJI__");
    }

}
