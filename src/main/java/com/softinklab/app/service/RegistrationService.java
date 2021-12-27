package com.softinklab.app.service;

import com.softinklab.app.rest.request.ResendVerificationEmailRequest;
import com.softinklab.rest.response.BaseResponse;
import com.softinklab.app.rest.request.RegistrationRequest;
import com.softinklab.app.rest.request.EmailVerificationRequest;
import com.softinklab.app.rest.response.RegistrationResponse;

public interface RegistrationService {
    RegistrationResponse register (RegistrationRequest payload);

    BaseResponse verifyEmail(EmailVerificationRequest payload);

    BaseResponse resendVerificationEmail(ResendVerificationEmailRequest payload);
}
