package com.deepansha.cowinSlots.service;

import com.deepansha.cowinSlots.entity.Alerts;
import com.deepansha.cowinSlots.repo.AlertRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

import com.deepansha.cowinSlots.configuration.AppConfiguration;
import com.deepansha.cowinSlots.dto.cowinResponse.AvlResponse;
import com.deepansha.cowinSlots.dto.cowinResponse.CowinResponse;
import com.deepansha.cowinSlots.dto.cowinResponse.CowinResponseCenter;
import com.deepansha.cowinSlots.dto.cowinResponse.CowinResponseSessions;
import com.deepansha.cowinSlots.dto.cowinResponse.CowinVaccineFees;

import org.springframework.http.HttpHeaders;
import org.springframework.http.*;

@Service
public class CheckAvailibilityService {

    public String checkByPincode;
    public String checkByDistrict;
    private SimpleDateFormat formatter;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppConfiguration appConfiguration;

    @Autowired
    private AlertRepo alertRepo;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    @Qualifier("generateNotificationService")
    private IGenerateNotificationService generateNotificationService;

    @Autowired
    private TelegramMessagingService telegramMessagingService;

    @PostConstruct
    public void PostConstruct() {
        this.formatter = new SimpleDateFormat(this.appConfiguration.getDateFormat());
        this.checkByPincode = this.appConfiguration.getCheckByPincodeURL();
        this.checkByDistrict = this.appConfiguration.getCheckByDistrictURL();
    }

    public void forceRunCron() {
        checkContiniousAVL();
    }

    @Scheduled(cron = "0 0/17 * * * ?")
    @Async
    // @Scheduled(cron = "0 * * * * *")
    public void checkContiniousAVL() {
        log.info("Cron Job to check avl");
        List<Alerts> alerts = alertRepo.findByActiveTrue();

        if (null == alerts || alerts.size() == 0) {
            log.warn("No Alerts Setup, return empty ");
            return;
        }

        log.warn("Cron running for Alert count: " + alerts.size());

        for (int i = 0; i < alerts.size(); i++) {
            Alerts alert = alerts.get(i);
            log.debug("Running for Alert: " + alert.toString());

            CowinResponse response = getResponseForAlert(alert);
            log.debug("Got response from cowin");

            if (null != response) {
                Set<AvlResponse> avlResponseList = processResponse(alert, response);
                log.warn("List AvlResponse size = " + avlResponseList.size());

                if (avlResponseList.size() > 0) {
                    generateNotificationService.notifyUsers(alert, avlResponseList);
                } else {
                    log.info("Nothing to notify ");
                }
            } else {
                log.error("Response is null");
            }

        }
    }

    public void refreshAvl(int id, boolean debug) {
        Optional<Alerts> alertVal = alertRepo.findById(id);
        if (alertVal.isPresent()) {
            Alerts alert = alertVal.get();
            CowinResponse response = getResponseForAlert(alert);
            Set<AvlResponse> avlResponseList = processResponse(alert, response);
            if (avlResponseList.isEmpty()) {
                notificationService.sendTelegramUpdate(alert,
                        "Currently no vaccine is available as per the for Alert: " + id, debug);
            } else {
                notificationService.sendTelegramMessage(alert, avlResponseList, debug);
            }
        }
    }

    private CowinResponse getResponseForAlert(Alerts alert) {
        CowinResponse response;

        if (alert.isPinCodeSearch()) {
            response = checkByPinCode(alert.getPincode());
        } else {
            response = checkAvlByDistrict(alert.getDistrictId());
        }
        return response;
    }

    public Set<AvlResponse> processResponse(Alerts alert, CowinResponse response) {
        Set<AvlResponse> avlResponseList = new LinkedHashSet<AvlResponse>();

        for (int i = 0; i < response.getCenters().size(); i++) {
            CowinResponseCenter center = response.getCenters().get(i);
            Set<CowinResponseSessions> validSessions = new LinkedHashSet<CowinResponseSessions>();
            String vaccineType = "";

            for (int j = 0; j < center.getSessions().size(); j++) {

                CowinResponseSessions session = center.getSessions().get(j);
                vaccineType = session.getVaccine();
                if ((alert.getVaccineType().trim().equalsIgnoreCase("any")
                        || alert.getVaccineType().trim().equalsIgnoreCase(session.getVaccine()))
                        && alert.getAge() >= session.getMin_age_limit() && session.getAvailable_capacity() > 0) {
                    if (session.getAvailable_capacity() > 1) {
                        validSessions.add(session);
                    } else {
                        log.warn("getAvailable_capacity capacity is 1 only");
                        telegramMessagingService.sendMessageToId(1813358994,
                                center.getName() + "-" + center.getPincode() + "  capacity is 1 only");
                    }

                }
            }

            if (validSessions.size() > 0) {
                AvlResponse avlResponse = new AvlResponse(center.getCenter_id(), center.getName(), center.getAddress(),
                        center.getPincode(), validSessions);

                if (center.getFee_type().equalsIgnoreCase("paid")
                        && !CollectionUtils.isEmpty(center.getVaccineFees())) {
                    for (int k = 0; k < center.getVaccineFees().size(); k++) {
                        CowinVaccineFees fees = center.getVaccineFees().get(i);
                        if (null != fees && fees.getVaccine().trim().equalsIgnoreCase(vaccineType)) {
                            avlResponse.setFees(fees.getFees());
                            break;
                        } else {
                            avlResponse.setFees(center.getFee_type());
                        }
                    }
                } else {
                    avlResponse.setFees(center.getFee_type());
                }

                avlResponseList.add(avlResponse);
            }
        }

        if (avlResponseList.size() == 0) {
            log.debug("No avlResponseList Session found for Alert " + alert);
        }

        return avlResponseList;
    }

    public CowinResponse checkAvlByDistrict(int districtId) {
        try {
            return getData(districtId, false);
        } catch (Exception e) {
            log.error("Exception fetching response by district for district id " + districtId, e);
            return null;
        }

    }

    public CowinResponse checkByPinCode(int pincode) {
        try {
            return getData(pincode, true);
        } catch (Exception e) {
            log.error("Exception fetching response by pincode for pincode id " + pincode, e);
            return null;
        }

    }

    private CowinResponse getData(int districtOrPincode, boolean isPinCode) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<String>(
                "parameters", headers);

        String formattedDate = formatter.format(new Date());
        String url = null;
        if (isPinCode) {
            url = checkByPincode.replace("{pinCode}", String.valueOf(districtOrPincode));
        } else {
            url = checkByDistrict.replace("{districtId}", String.valueOf(districtOrPincode));
        }

        url = url.replace("{dateVal}", formattedDate);
        log.info("Initiaing API Call: " + url);
        ResponseEntity<CowinResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                CowinResponse.class);

        return response.getBody();
    }

}
