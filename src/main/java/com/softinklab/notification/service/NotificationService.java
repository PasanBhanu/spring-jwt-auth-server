package com.softinklab.notification.service;

import com.softinklab.notification.config.EmailConfig;
import com.softinklab.notification.model.Email;

public interface NotificationService {

    void sendEmail(Email email);

    EmailConfig getEmailConfig();
}
