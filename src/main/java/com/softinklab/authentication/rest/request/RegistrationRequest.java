package com.softinklab.authentication.rest.request;

import com.softinklab.rest.response.validation.annotation.MatchField;
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

    @Size(max = 255)
    private String addressLine1;
    @Size(max = 255)
    private String addressLine2;
    @Size(max = 255)
    private String state;
    @Size(max = 255)
    private String city;
    @Size(max = 50)
    private String postalCode;
    @Size(max = 100)
    private String country;

    @Size(max = 10)
    private String sex;
    @Size(max = 10)
    private String prefix;

    @Size(max = 50)
    private String mobileNo;
    @Size(max = 50)
    private String vatNo;
    @Size(max = 20)
    private String nicNo;
    @Size(max = 255)
    private String companyName;
}
