package com.softinklab.authentication.service;

import com.softinklab.rest.response.BaseResponse;
import com.softinklab.authentication.rest.request.RegistrationRequest;
import com.softinklab.authentication.rest.request.ValidateEmailRequest;
import com.softinklab.authentication.rest.response.RegistrationResponse;

public interface RegistrationService {
    RegistrationResponse register (RegistrationRequest payload);

    BaseResponse validateEmail(ValidateEmailRequest payload);
}
