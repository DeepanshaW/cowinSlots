package com.deepansha.cowinSlots.service.chatbot;

import com.deepansha.cowinSlots.configuration.AppConfiguration;
import com.deepansha.cowinSlots.dto.cowinResponse.AvlResponse;
import com.deepansha.cowinSlots.dto.cowinResponse.CowinResponseSessions;
import com.deepansha.cowinSlots.entity.Alerts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

@Component("TelegramSlotPoller")
public class TelegramSlotPoller extends TelegramLongPollingBot implements ITelegramSlotPoller {

    @Autowired
    private AppConfiguration appConfiguration;

    @Autowired
    private CowinTelegramChatBot cowinTelegramChatBot;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public TelegramSlotPoller() {
    }

    @Override
    public String getBotUsername() {
        return appConfiguration.getTelegramBotName();
    }

    @Override
    public String getBotToken() {
        return appConfiguration.getTelegramBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String messageText = update.getMessage().getText();
            log.info("Input Message", messageText);
            log.info("updateId - " + update.getUpdateId() + " - getChatId " + update.getMessage().getChatId()
                    + " isCommand- " + update.getMessage().isCommand() + " - getMessageId "
                    + update.getMessage().getMessageId());

            List<String> response = cowinTelegramChatBot.getResponseForMessage(messageText,
                    update.getMessage().getChatId());
            log.debug("Got Message for the input - " + messageText);

            String chatId = String.valueOf(update.getMessage().getChatId());
            log.debug("response --> " + response);
            for (int i = 0; i < response.size(); i++) {
                sendResponse(chatId, response.get(i), true, false);
            }
        } else {
            sendResponse(update.getMessage().getChatId().toString(), "Please send text message", true, false);
        }
    }

    public void sendResponse(String chatId, String response, boolean enableMarkdown, boolean enableHtml) {
        SendMessage message = new SendMessage(chatId, response);
        message.enableMarkdown(enableMarkdown);
        if (enableHtml) {
            message.enableHtml(enableHtml);
        }

        try {
            execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Not Used
    public void registerTelegram() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (org.telegram.telegrambots.meta.exceptions.TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendVaccineUpdates(Alerts alert, String message) {
        try {
            log.debug("Response to publish ", message);
            String chatId = alert.getPhoneNumber().substring(alert.getPhoneNumber().indexOf(":") + 1);
            sendVaccineUpdatestoSelf(message);
            sendResponse(chatId, message, true, false);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Async
    public void sendVaccineUpdates(Alerts alert, Set<AvlResponse> avlResponseList) {
        String message = getAlertMessage(alert, avlResponseList);
        String chatId = alert.getPhoneNumber().substring(alert.getPhoneNumber().indexOf(":") + 1);
        sendVaccineUpdates(chatId, message);
        sendVaccineUpdatestoSelf(message);
    }

    @Override
    public void sendVaccineUpdatestoSelf(Alerts alert, Set<AvlResponse> avlResponseList) {
        String message = getAlertMessage(alert, avlResponseList);
        String chatId = "1813358994";
        log.debug("Sending message " + message + "\n to chat id " + chatId);
        sendVaccineUpdates(chatId, message);
    }

    private void sendVaccineUpdates(String chatId, String message) {
        try {
            sendResponse(chatId, message, true, false);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Async
    public void sendVaccineUpdatestoSelf(String message) {
        String chatId = "1813358994";
        try {
            sendResponse(chatId, message, true, false);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public String getAlertMessage(Alerts alert, Set<AvlResponse> avlResponseList) {
        StringBuilder updatedMessage = new StringBuilder();
        updatedMessage.append("Hi ");
        if (null != alert.getName()) {
            updatedMessage.append(StringUtils.capitalize(alert.getName()));
        }
        updatedMessage.append(", following slots are available as per your alert: ");
        updatedMessage.append(alert.getAge());
        updatedMessage.append("+");

        if (alert.isPinCodeSearch()) {
            updatedMessage.append(" for pincode: ");
            updatedMessage.append(alert.getPincode());
        } else {
            if (null != alert.getCity() && null != alert.getState()) {
                updatedMessage.append(" for ");
                updatedMessage.append(StringUtils.capitalize(alert.getCity()));
                updatedMessage.append(", ");
                updatedMessage.append(StringUtils.capitalize(alert.getState()));
            }
        }
        updatedMessage.append("\n");

        Iterator<AvlResponse> itr = avlResponseList.iterator();

        while (itr.hasNext()) {
            AvlResponse res = itr.next();
            Set<CowinResponseSessions> set = res.getSessions();
            updatedMessage.append("\n\n");
            updatedMessage.append("🚑").append(res.getCenterName()).append(" - ").append(res.getCenterAddress())
                    .append("-").append(res.getPincode()).append("\n");

            if (null != set && set.size() > 0) {
                Iterator<CowinResponseSessions> itr2 = set.iterator();
                while (itr2.hasNext()) {
                    CowinResponseSessions session = itr2.next();
                    updatedMessage.append("-------------------\n");
                    updatedMessage.append("Type: ").append(session.getVaccine()).append("\n");
                    updatedMessage.append("Date: ").append(session.getDate()).append("\n");
                    updatedMessage.append("Age: ").append(session.getMin_age_limit()).append("\n");
                    updatedMessage.append("Fee: ").append(res.getFees()).append("\n");
                    updatedMessage.append("Available Count: ").append(session.getAvailable_capacity()).append("\n");
                    updatedMessage.append("-------------------");
                }
            }
            updatedMessage.append("-------------------");
        }

        updatedMessage.append("\n\n");
        updatedMessage.append(
                "Click here to stop recieving updates for this alert:  /stopUpdatesForAlert" + alert.getId() + " \n");
        updatedMessage.append("Click here to stop recieving updates for all alerts:  /stopUpdates \n");
        updatedMessage.append("Click fetch Latest Update on this:  /fetchLatestUpdateFor" + alert.getId());

        return updatedMessage.toString();
    }

}
