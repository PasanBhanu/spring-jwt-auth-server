package com.softinklab.authserver.rest.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
    private String appId;
    private Boolean rememberMe;
    private String deviceType;
    private String sessionId;
}
