package com.softinklab.authentication.rest.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ProfileUpdateRequest {
    @NotBlank(message = "First Name is required")
    @Size(max = 255)
    private String firstName;
    @NotBlank(message = "Last Name is required")
    @Size(max = 255)
    private String lastName;

    @NotBlank(message = "Address is required")
    @Size(max = 255)
    private String addressLine1;
    @Size(max = 255)
    private String addressLine2;
    @NotBlank(message = "State is required")
    @Size(max = 255)
    private String state;
    @NotBlank(message = "City is required")
    @Size(max = 255)
    private String city;
    @NotBlank(message = "Postal code is required")
    @Size(max = 50)
    private String postalCode;
    @NotBlank(message = "Country is required")
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
