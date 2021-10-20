package com.softinklab.authentication.rest.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResendVerificationEmailRequest {
    @NotBlank
    private String username;
}
