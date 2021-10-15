package com.softinklab.authserver.service;

import com.softinklab.authserver.rest.BaseResponse;
import com.softinklab.authserver.rest.request.RegistrationRequest;
import com.softinklab.authserver.rest.request.ValidateEmailRequest;
import com.softinklab.authserver.rest.response.RegistrationResponse;
import org.springframework.stereotype.Service;

public interface RegistrationService {
    RegistrationResponse register (RegistrationRequest payload);

    BaseResponse validateEmail(ValidateEmailRequest payload);
}
