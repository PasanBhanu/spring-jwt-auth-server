package com.softinklab.authentication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "authserver.jwt")
public class TokenConfig {
    private String secretKey;
    private String authKey;
    private String nonce;
    private String aad;
    private Integer cookieMaxDays;
    private String path;
    private String domain;
}
