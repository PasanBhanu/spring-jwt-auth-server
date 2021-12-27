package com.softinklab.app.rest.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ForgetPasswordRequest {
    @NotBlank
    @Size(max = 320)
    @Email(message = "Please enter a valid email")
    private String username;
}
