package com.softinklab.authserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "authserver.jwt")
public class TokenConfig {
    private String secretKey;
    private String authKey;
    private Long tokenValidity;
    private Integer rememberDays;
    private String audience;
    private String issuer;
    private String subject;
}