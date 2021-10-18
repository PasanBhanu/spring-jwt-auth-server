package com.softinklab.authserver.controller;

import com.softinklab.authserver.config.AuthServerConfig;
import com.softinklab.authserver.exception.custom.ServiceException;
import com.softinklab.authserver.rest.BaseResponse;
import com.softinklab.authserver.rest.request.RegistrationRequest;
import com.softinklab.authserver.rest.request.ValidateEmailRequest;
import com.softinklab.authserver.rest.response.RegistrationResponse;
import com.softinklab.authserver.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
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
            throw new ServiceException(401, HttpStatus.NOT_FOUND, "User registration disabled.", Collections.singletonList("User registration disabled."));
        }

        return ResponseEntity.ok(this.registrationService.register(payload));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/validate-email", produces = "application/json")
    public ResponseEntity<BaseResponse> validateEmail(@Valid @RequestBody ValidateEmailRequest payload) {
        return ResponseEntity.ok(this.registrationService.validateEmail(payload));
    }
}
