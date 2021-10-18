package com.softinklab.authserver.rest.response;

import lombok.Data;

@Data
public class RegistrationResponse {
    private Integer userId;
    private String username;
    private Boolean registrationSuccess;
    private Boolean verificationRequired;
}
