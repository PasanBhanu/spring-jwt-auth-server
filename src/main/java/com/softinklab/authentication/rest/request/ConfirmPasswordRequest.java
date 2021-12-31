package com.softinklab.authentication.rest.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ConfirmPasswordRequest {
    @NotBlank
    private String password;
}
