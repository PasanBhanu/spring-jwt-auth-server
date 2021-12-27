package com.softinklab.app.rest.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LogoutRequest {
    @NotNull
    private String username;
    @NotNull
    private String userId;
    @NotNull
    private String rememberToken;
    @NotNull
    private String token;
    @NotNull
    private String deviceHash;
}
