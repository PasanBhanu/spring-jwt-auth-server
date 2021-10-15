package com.softinklab.authserver.rest.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ValidateEmailRequest {
    @NotBlank
    private String token;
}
