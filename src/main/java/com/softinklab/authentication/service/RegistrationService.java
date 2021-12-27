package com.softinklab.authentication.service;

import com.softinklab.authentication.rest.request.ResendVerificationEmailRequest;
import com.softinklab.rest.response.BaseResponse;
import com.softinklab.authentication.rest.request.RegistrationRequest;
import com.softinklab.authentication.rest.request.EmailVerificationRequest;
import com.softinklab.authentication.rest.response.RegistrationResponse;

public interface RegistrationService {
    RegistrationResponse register (RegistrationRequest payload);

    BaseResponse verifyEmail(EmailVerificationRequest payload);

    BaseResponse resendVerificationEmail(ResendVerificationEmailRequest payload);
}
