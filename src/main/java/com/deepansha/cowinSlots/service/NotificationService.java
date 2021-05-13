package com.deepansha.cowinSlots.service;

import com.deepansha.cowinSlots.dto.cowinResponse.AvlResponse;
import com.deepansha.cowinSlots.entity.Alerts;
import com.deepansha.cowinSlots.service.chatbot.ITelegramSlotPoller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.deepansha.cowinSlots.configuration.AppConfiguration;

@Service
public class NotificationService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppConfiguration appConfiguration;

    @Autowired
    private EmailService emailServce;

    @Autowired
    @Qualifier("telegramSlotPoller")
    private ITelegramSlotPoller telegramService;

    public String sendWhatsAppMessage(Alerts alert, Set<AvlResponse> avlResponseList) {

        log.info("Initiating Whatsapp Message for Alert " + alert.toString());
        return splitSendMessage(alert, avlResponseList, true);
    }

    public String sendTestMessage(Alerts alert, Set<AvlResponse> avlResponseList) {
        log.info("Initiating Text Message for Alert " + alert.toString());
        return splitSendMessage(alert, avlResponseList, false);
    }

    public String sendEmail(Alerts alert, Set<AvlResponse> avlResponseList) {
        log.info("Initiating Email for Alert " + alert.toString());
        emailServce.sendEmail(alert, avlResponseList);
        return "";
    }

    public String sendTelegramMessage(Alerts alert, Set<AvlResponse> avlResponseList, boolean debug) {
        if (debug) {
            telegramService.sendVaccineUpdatestoSelf(alert, avlResponseList);
        } else {
            telegramService.sendVaccineUpdates(alert, avlResponseList);
        }

        return "0";
    }

    public String sendTelegramUpdate(Alerts alert, String data, boolean debug) {
        if (debug) {
            telegramService.sendVaccineUpdatestoSelf(data);
        } else {
            telegramService.sendVaccineUpdates(alert, data);
        }
        return "0";
    }

    private String splitSendMessage(Alerts alert, Set<AvlResponse> avlResponseList, boolean isWhatsapp) {
        StringBuilder buidBuilder = new StringBuilder();
        Iterator<AvlResponse> itr = avlResponseList.iterator();
        while (itr.hasNext()) {
            buidBuilder.append(itr.next().getVaccineAVLResponseString());
            buidBuilder.append("\n");
        }

        String message = buidBuilder.toString();
        List<String> splitStrings = splitToNChar(message, 1600);
        String messageReturn = null;

        for (int i = 0; i < splitStrings.size(); i++) {
            String str = splitStrings.get(i);

            if (null == str || str.trim().length() == 0) {
                log.debug("Empty Message skipping text");
                continue;
            }

            if (null == messageReturn) {
                messageReturn = sendMessage(alert, str.trim(), false);
            } else {
                messageReturn = messageReturn + "__AND__" + sendMessage(alert, str.trim(), false);
            }

        }
        return messageReturn;
    }

    private String sendMessage(Alerts alert, String whatsAppMessage, boolean isWhatsapp) {
        String prefix = "";
        if (isWhatsapp) {
            prefix = "whatsapp:";
        }

        Twilio.init(appConfiguration.getTwiloAccoundSid(), appConfiguration.getTwiloAuthToken());

        Message message = Message.creator(new com.twilio.type.PhoneNumber(prefix + alert.getPhoneNumber()),
                new com.twilio.type.PhoneNumber(prefix + appConfiguration.getTwiloSendNumber()), whatsAppMessage)
                .create();

        log.info("Message Send for Alert " + alert.toString() + " with SID: " + message.getSid());
        log.debug(message.getAccountSid() + "-" + message.getApiVersion() + "-" + message.getPrice() + "-"
                + message.getPriceUnit() + "-" + message.getMessagingServiceSid());
        if (message.getErrorMessage() != null && message.getErrorMessage().length() > 0) {
            log.error("Message Failed to send : " + message.getErrorMessage());
        } else {
            log.info("Message sent successfully");
        }

        return message.getPrice() + "-" + message.getPriceUnit();
    }

    private List<String> splitToNChar(String text, int size) {
        List<String> parts = new ArrayList<>();

        int length = text.length();
        for (int i = 0; i < length; i += size) {
            parts.add(text.substring(i, Math.min(length, i + size)));
        }
        return parts;
        // return parts.toArray(new String[0]);
    }
}
