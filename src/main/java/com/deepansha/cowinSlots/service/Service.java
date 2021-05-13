package com.deepansha.cowinSlots.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.deepansha.cowinSlots.dto.AlertDTO;
import com.deepansha.cowinSlots.entity.Alerts;
import com.deepansha.cowinSlots.repo.AlertRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@org.springframework.stereotype.Service
public class Service{
        private final Logger log = LoggerFactory.getLogger(this.getClass());

        @Autowired
        public AlertRepo alertRepo;

        @Autowired
        public EmailService emailServce;

        // public Alerts(String name, String state, String city, int pincode, int
        // districtId, String phoneNumber,
        // boolean active, int age, String vaccineType)

        public int addAlert(AlertDTO aDto) {

            log.info("Add Alert : " + aDto.toString());
            if (aDto.getPincode() > 0) {
                aDto.setPinCodeSearch(true);
            }

            aDto.setActive(true);
            Alerts alert = new Alerts(aDto.getName(), aDto.getState(), aDto.getCity(), aDto.getPincode(),
                    aDto.getDistrictId(), aDto.getPhoneNumber(), aDto.isActive(), aDto.getAge(), aDto.getVaccineType(),
                    aDto.isPinCodeSearch(), aDto.getNotificationType(), aDto.getEmailAddress());
            alert.setCreatedAt(new Date());
            alert.setModifiedAt(new Date());
            int x = alertRepo.save(alert).getId();
            log.info("User Alert created");
            return x;
        }

        public int removeAlertById(int id) {
            log.info("Remove Alert : for id " + id);
            Optional<Alerts> alerts = alertRepo.findById(id);
            if (null == alerts || !alerts.isPresent()) {
                log.warn("No Such Alert Exists" + id);
                return 0;
            }
            Alerts alert = alerts.get();
            alert.setActive(false);
            alertRepo.save(alert);

            log.info("Successfully Disabld Alert");
            return 1;
        }

        public int removeAlertByPhone(String phone) {
            log.info("Remove Alert for phone " + phone);
            List<Alerts> alerts = alertRepo.findByPhoneNumber(phone);
            if (null == alerts || alerts.size() == 0) {
                log.warn("No Such Alert Exists for phone number " + phone);
                return 0;
            }

            List<Alerts> updatedAlerts = new ArrayList<>();
            log.info("Found Alert count: " + alerts);

            for (int i = 0; i < alerts.size(); i++) {
                Alerts alt = alerts.get(i);
                alt.setActive(false);
                updatedAlerts.add(alt);
            }

            alertRepo.saveAll(updatedAlerts);
            log.info("Successfully disabled alerts");
            return 1;
        }

        public void sendWelcomeEmail(int id) {
            Alerts alert = alertRepo.findById(id).get();
            emailServce.sendWelcomeMessage(alert);
        }

        public List<AlertDTO> getAlerts() {
            List<Alerts> alerts = alertRepo.findAll();
            return convertToDtos(alerts);
        }

        public AlertDTO getAlertsById(int id) {
            Alerts alert = alertRepo.findById(id).get();
            return convertToDto(alert);
        }

        public List<AlertDTO> getAlertByMobileNumber(AlertDTO aDto) {
            List<Alerts> alerts = alertRepo.findByPhoneNumber(aDto.getPhoneNumber());
            return convertToDtos(alerts);
        }

        private List<AlertDTO> convertToDtos(List<Alerts> alerts) {
            List<AlertDTO> dtos = new ArrayList<>();
            for (int i = 0; i < alerts.size(); i++) {

                AlertDTO alert = convertToDto(alerts.get(i));
                dtos.add(alert);
            }
            return dtos;
        }

        private AlertDTO convertToDto(Alerts alert) {

            AlertDTO alertVal = new AlertDTO(alert.getId(), alert.getName(), alert.getState(), alert.getCity(),
                    alert.getPincode(), alert.getDistrictId(), alert.getPhoneNumber(), alert.isActive(), alert.getAge(),
                    alert.getVaccineType(), alert.isPinCodeSearch(), alert.getNotificationType(), alert.getEmail());
            alertVal.setCreatedAt(alert.getCreatedAt());
            alertVal.setModifiedAt(alert.getModifiedAt());
            return alertVal;
        }

}
