package com.softinklab.app.controller;

import com.softinklab.app.config.AuthServerConfig;
import com.softinklab.app.rest.request.ResendVerificationEmailRequest;
import com.softinklab.rest.action.Authentication;
import com.softinklab.rest.exception.ServiceException;
import com.softinklab.rest.response.BaseResponse;
import com.softinklab.app.rest.request.RegistrationRequest;
import com.softinklab.app.rest.request.EmailVerificationRequest;
import com.softinklab.app.rest.response.RegistrationResponse;
import com.softinklab.app.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping(path = "/authentication")
public class RegisterController {

    private final RegistrationService registrationService;
    private final AuthServerConfig authServerConfig;

    public RegisterController(RegistrationService registrationService, AuthServerConfig authServerConfig) {
        this.registrationService = registrationService;
        this.authServerConfig = authServerConfig;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/register", produces = "application/json")
    public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegistrationRequest payload) {
        if (!this.authServerConfig.getRegistration().getEnable()) {
            throw new ServiceException(401, HttpStatus.NOT_FOUND, "User registration disabled.", Collections.singletonList("User registration disabled."), Authentication.REGISTRATION_DISABLED);
        }

        return ResponseEntity.ok(this.registrationService.register(payload));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/verify-email", produces = "application/json")
    public ResponseEntity<BaseResponse> verifyEmail(@Valid @RequestBody EmailVerificationRequest payload) {
        return ResponseEntity.ok(this.registrationService.verifyEmail(payload));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/resend-verification-email", produces = "application/json")
    public ResponseEntity<BaseResponse> resendEmail(@Valid @RequestBody ResendVerificationEmailRequest payload) {
        return ResponseEntity.ok(this.registrationService.resendVerificationEmail(payload));
    }
}
