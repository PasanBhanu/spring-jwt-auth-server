package com.softinklab.notification.model;

import com.softinklab.notification.config.EmailConfig;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Data
public class Email {
    protected String to;
    protected String from;
    protected String fromName;
    protected String replyTo;
    protected String replyToName;
    protected String subject;
    protected String applicationName;

    protected String greeting;
    protected String sender;
    protected Integer year = Calendar.getInstance().get(Calendar.YEAR);

    public Map<String, Object> getModel() {
        return new HashMap<>();
    }

    public void initialise(EmailConfig emailConfig) {
        this.from = emailConfig.getFrom();
        this.fromName = emailConfig.getFromName();
        this.replyTo = emailConfig.getFrom();
        this.replyToName = emailConfig.getFromName();
        this.sender = emailConfig.getSender();
        this.applicationName = emailConfig.getApplicationName();
    }
}
