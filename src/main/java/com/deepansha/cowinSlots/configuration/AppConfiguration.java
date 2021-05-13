package com.deepansha.cowinSlots.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix="app")
public class AppConfiguration {

    @Value("${app.telegramBotName}")
    private String telegramBotName;

    @Value("${app.telegramBotToken}")
    private String telegramBotToken;

    @Value("${app.emailFrom}")
    private String emailFrom;

    @Value("${app.emailHost}")
    private String emailHost;

    @Value("${app.emailSocketFactory}")
    private String emailSocketFactory;

    @Value("${app.emailSocketFactoryClass}")
    private String emailSocketFactoryClass;

    @Value("${app.emailAuth}")
    private String emailAuth;

    @Value("${app.emailSmtpPort}")
    private String emailSmtpPort;

    @Value("${app.emailFromPassword}")
    private String emailFromPassword;

    @Value("${app.checkByPincodeURL}")
    private String checkByPincodeURL;

    @Value("${app.checkByDistrictURL}")
    private String checkByDistrictURL;

    @Value("${app.dateFormat}")
    private String dateFormat;

    @Value("${app.maxNotificationPerAlertPerDay}")
    private int maxNotificationPerAlertPerDay;

    @Value("${app.maxNotificationPerAlertHour}")
    private int maxNotificationPerAlertHour;

    @Value("${app.hostNameURL}")
    private String appHostNameURL;

    @Value("${app.host}")
    private String appHost;

    @Value("${app.port}")
    private int appPort;

    @Value("${app.twiloACCOUNT_SID}")
    private String twiloAccoundSid;

    @Value("${app.twiloAUTH_TOKEN}")
    private String twiloAuthToken;

    @Value("${app.twilosendNumber}")
    private String twiloSendNumber;

    public String getTwiloAccoundSid() {
        return twiloAccoundSid;
    }

    public void setTwiloAccoundSid(String twiloAccoundSid) {
        this.twiloAccoundSid = twiloAccoundSid;
    }

    public String getTwiloAuthToken() {
        return twiloAuthToken;
    }

    public void setTwiloAuthToken(String twiloAuthToken) {
        this.twiloAuthToken = twiloAuthToken;
    }

    public String getTwiloSendNumber() {
        return twiloSendNumber;
    }

    public void setTwiloSendNumber(String twiloSendNumber) {
        this.twiloSendNumber = twiloSendNumber;
    }

    public String getAppHostNameURL() {
        return appHostNameURL;
    }

    public void setAppHostNameURL(String appHostNameURL) {
        this.appHostNameURL = appHostNameURL;
    }

    public String getAppHost() {
        return appHost;
    }

    public void setAppHost(String appHost) {
        this.appHost = appHost;
    }

    public int getAppPort() {
        return appPort;
    }

    public void setAppPort(int appPort) {
        this.appPort = appPort;
    }

    public String getEmailFromPassword() {
        return emailFromPassword;
    }

    public void setEmailFromPassword(String emailFromPassword) {
        this.emailFromPassword = emailFromPassword;
    }

    public String getTelegramBotName() {
        return telegramBotName;
    }

    public void setTelegramBotName(String telegramBotName) {
        this.telegramBotName = telegramBotName;
    }

    public String getTelegramBotToken() {
        return telegramBotToken;
    }

    public void setTelegramBotToken(String telegramBotToken) {
        this.telegramBotToken = telegramBotToken;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailHost() {
        return emailHost;
    }

    public void setEmailHost(String emailHost) {
        this.emailHost = emailHost;
    }

    public String getEmailSocketFactory() {
        return emailSocketFactory;
    }

    public void setEmailSocketFactory(String emailSocketFactory) {
        this.emailSocketFactory = emailSocketFactory;
    }

    public String getEmailSocketFactoryClass() {
        return emailSocketFactoryClass;
    }

    public void setEmailSocketFactoryClass(String emailSocketFactoryClass) {
        this.emailSocketFactoryClass = emailSocketFactoryClass;
    }

    public String getEmailAuth() {
        return emailAuth;
    }

    public void setEmailAuth(String emailAuth) {
        this.emailAuth = emailAuth;
    }

    public String getEmailSmtpPort() {
        return emailSmtpPort;
    }

    public void setEmailSmtpPort(String emailSmtpPort) {
        this.emailSmtpPort = emailSmtpPort;
    }

    public String getCheckByPincodeURL() {
        return checkByPincodeURL;
    }

    public void setCheckByPincodeURL(String checkByPincodeURL) {
        this.checkByPincodeURL = checkByPincodeURL;
    }

    public String getCheckByDistrictURL() {
        return checkByDistrictURL;
    }

    public void setCheckByDistrictURL(String checkByDistrictURL) {
        this.checkByDistrictURL = checkByDistrictURL;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public int getMaxNotificationPerAlertPerDay() {
        return maxNotificationPerAlertPerDay;
    }

    public void setMaxNotificationPerAlertPerDay(int maxNotificationPerAlertPerDay) {
        this.maxNotificationPerAlertPerDay = maxNotificationPerAlertPerDay;
    }

    public int getMaxNotificationPerAlertHour() {
        return maxNotificationPerAlertHour;
    }

    public void setMaxNotificationPerAlertHour(int maxNotificationPerAlertHour) {
        this.maxNotificationPerAlertHour = maxNotificationPerAlertHour;
    }

}
