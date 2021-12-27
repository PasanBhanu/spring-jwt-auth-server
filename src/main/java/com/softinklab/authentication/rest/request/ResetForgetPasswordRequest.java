package com.softinklab.authentication.rest.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ResetForgetPasswordRequest {
    @NotBlank
    private String token;
    @NotNull
    @Size(max = 320)
    @Email(message = "Please enter a valid email")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 5, max = 25, message = "Password length should be 5 - 25 length")
    private String password;
    @NotBlank
    private String confirmPassword;
}
