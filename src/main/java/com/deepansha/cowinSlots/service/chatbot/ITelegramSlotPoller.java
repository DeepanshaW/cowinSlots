package com.deepansha.cowinSlots.service.chatbot;

import com.deepansha.cowinSlots.dto.cowinResponse.AvlResponse;
import com.deepansha.cowinSlots.entity.Alerts;

import java.util.Set;

public interface ITelegramSlotPoller {
    public void sendVaccineUpdates(Alerts alert, String message);

    public void sendVaccineUpdates(Alerts alert, Set<AvlResponse> avlResponseList);

    public void sendVaccineUpdatestoSelf(Alerts alert, Set<AvlResponse> avlResponseList);

    public void sendVaccineUpdatestoSelf(String message);

    public void sendResponse(String chatId, String response, boolean enableMarkdown, boolean enableHtml);
}
