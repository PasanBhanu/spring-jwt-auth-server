package com.softinklab.authentication.rest.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {
    @NotNull
    @Size(max = 320)
    @Email(message = "Please enter a valid email")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @NotNull
    private Integer appId;
    private Boolean rememberMe = false;
    private String browser;
    private String os;
    private String deviceName;
    @NotNull
    private String deviceHash;      // Browser + OS + Device + Vendor + CPU

}
