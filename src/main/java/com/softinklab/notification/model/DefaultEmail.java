package com.softinklab.notification.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Data
public class DefaultEmail {
    @Value("${spring.mail.from}")
    private String defaultFrom;
    @Value("${spring.mail.from}")
    private String defaultFromName;
    @Value("${spring.mail.sender}")
    private String defaultSender;

    private String to;
    private String from = defaultFrom;
    private String fromName = defaultFromName;
    private String replyTo = from;
    private String replyToName = fromName;
    private String subject;

    private String greeting;
    private String sender = defaultSender;
    private String buttonText;
    private String buttonUrl;
    private List<String> preParagraphs;
    private List<String> postParagraphs;
}
