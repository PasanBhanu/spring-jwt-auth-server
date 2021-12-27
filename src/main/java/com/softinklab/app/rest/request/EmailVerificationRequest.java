package com.softinklab.app.rest.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EmailVerificationRequest {
    @NotBlank
    private String token;
}
