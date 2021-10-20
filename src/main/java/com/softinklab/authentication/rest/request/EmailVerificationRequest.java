package com.softinklab.authentication.rest.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EmailVerificationRequest {
    @NotBlank
    private String token;
}
