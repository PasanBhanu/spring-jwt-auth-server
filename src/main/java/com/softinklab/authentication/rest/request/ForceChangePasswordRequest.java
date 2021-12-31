package com.softinklab.authentication.rest.request;

import com.softinklab.rest.response.validation.annotation.MatchField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@MatchField(match = "confirmNewPassword", with = "newPassword", message = "Passwords does not match")
public class ForceChangePasswordRequest {
    @NotBlank(message = "Password is required")
    @Size(min = 5, max = 25, message = "Password length should be 5 - 25 length")
    private String newPassword;
    @NotBlank
    private String confirmNewPassword;
}
