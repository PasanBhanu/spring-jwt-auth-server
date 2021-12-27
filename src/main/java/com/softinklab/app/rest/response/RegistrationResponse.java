package com.softinklab.app.rest.response;

import com.softinklab.rest.response.BaseResponse;
import lombok.Data;

@Data
public class RegistrationResponse extends BaseResponse {
    private Integer userId;
    private String username;
    private Boolean registrationSuccess;
    private Boolean verificationRequired;
}
