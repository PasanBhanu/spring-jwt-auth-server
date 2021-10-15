package com.softinklab.authserver.rest.request;

import com.softinklab.authserver.rest.validation.annotation.MatchField;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@MatchField(match = "confirmPassword", with = "password", message = "Passwords does not match")
public class RegistrationRequest {
    @NotNull
    @Size(max = 320)
    @Email
    private String username;
    @NotBlank
    @Size(min = 5, max = 25)
    private String password;
    @NotBlank
    private String confirmPassword;

    @NotBlank(message = "First Name is required")
    @Size(max = 255)
    private String firstName;
    @NotBlank
    @Size(max = 255)
    private String lastName;
}
