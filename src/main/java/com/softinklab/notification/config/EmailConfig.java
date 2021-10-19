package com.softinklab.notification.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.mail")
public class EmailConfig {
    protected String from;
    protected String fromName;
    protected String sender;
    protected String applicationName;
}
