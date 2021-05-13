package com.deepansha.cowinSlots.service;

import com.deepansha.cowinSlots.dto.cowinResponse.AvlResponse;
import com.deepansha.cowinSlots.entity.Alerts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;

import com.deepansha.cowinSlots.configuration.AppConfiguration;
import com.deepansha.cowinSlots.entity.Notifications;
import com.deepansha.cowinSlots.repo.NotificationsRepo;

@Component("generateNotificationService")
public class GenerateNotificationService implements IGenerateNotificationService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppConfiguration appConfiguration;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationsRepo notificationsRepo;

    @Async
    public void notifyUsers(Alerts alert, Set<AvlResponse> avlResponseList) {
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, -1);

        List<Notifications> notifications = notificationsRepo.findByAlertIdAndCreatedAtAfter(alert.getId(),
                cal.getTime());

        if (null == notifications) {
            log.debug("No Notification found for alert id : " + alert.getId());
            notifications = new ArrayList<Notifications>();
        }

        cal.add(Calendar.DATE, 1);

        Collections.sort(notifications);
        int notificationSentToday = 0;

        for (int i = 0; i < notifications.size(); i++) {
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(notifications.get(i).getCreatedAt());

            if (cal2.get(Calendar.DATE) == cal.get(Calendar.DATE) && cal2.get(Calendar.MONTH) == cal.get(Calendar.MONTH)
                    && cal2.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
                notificationSentToday++;
            }
        }

        if (notificationSentToday >= this.appConfiguration.getMaxNotificationPerAlertPerDay()) {
            log.info("Already getMaxNotificationPerAlertPerDay notification issued to this mobile number:  for alert "
                    + alert.toString() + " Not issuing current one");
            return;
        } else if (notificationSentToday > 0) {
            Notifications notification = notifications.get(0); // Latest Notificiation
            Calendar latestNotification = Calendar.getInstance();

            if (latestNotification.get(Calendar.DATE) == cal.get(Calendar.DATE)
                    && latestNotification.get(Calendar.MONTH) == cal.get(Calendar.MONTH)
                    && latestNotification.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
                long td = currentDate.getTime() - notification.getCreatedAt().getTime();
                long timeinMinutes = (td) / 1000 / 60;
                log.info("Last Notification sent at : " + notification.getCreatedAt() + " and current time is "
                        + cal.getTime() + " and their time difference in millis is " + td + " and in minutes is "
                        + timeinMinutes);

                if (timeinMinutes < 30) {
                    log.info("Max notification is 1 every 30 minutes, not sending notification for " + alert);
                    return;
                }
            }
        }

        String alerts = alert.getNotificationType();
        if (null != alerts) {
            String alertList[] = alerts.split(",");
            for (int i = 0; i < alertList.length; i++) {
                notify(alert, avlResponseList, Integer.parseInt(alertList[i]), currentDate);
            }
        }

        log.info("Successfully returning from Notifications");
    }

    private void notify(Alerts alert, Set<AvlResponse> avlResponseList, int notificationType, Date date) {
        String cost = "";
        log.info("Notifaction for Alert for notification type: " + notificationType);
        switch (notificationType) {

            case 0:
                cost = notificationService.sendWhatsAppMessage(alert, avlResponseList);
                break;

            case 1:
                cost = notificationService.sendTestMessage(alert, avlResponseList);
                break;

            case 2:
                cost = notificationService.sendEmail(alert, avlResponseList);
                break;

            case 3:
                cost = notificationService.sendTelegramMessage(alert, avlResponseList, false);
                break;
        }

        Notifications not = new Notifications(date, alert.getPhoneNumber(), alert.getId(), cost, notificationType);
        notificationsRepo.save(not);
        log.info("Notifiication saved");

        log.info("Successfully returning from Notifications");
    }
}
