package com.softinklab.authentication.rest.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
    private Integer appId;
    private Boolean rememberMe;
    private String browser;
    private String os;
    private String deviceName;
    private String deviceHash;      // Browser + OS + Device + Vendor + CPU

}
