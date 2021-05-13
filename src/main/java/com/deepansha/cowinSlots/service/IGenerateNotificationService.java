package com.deepansha.cowinSlots.service;

import com.deepansha.cowinSlots.dto.cowinResponse.AvlResponse;
import com.deepansha.cowinSlots.entity.Alerts;

import java.util.Set;

public interface IGenerateNotificationService {
    public void notifyUsers(Alerts alert, Set<AvlResponse> avlResponseList);
}
