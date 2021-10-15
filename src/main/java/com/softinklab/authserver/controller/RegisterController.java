package com.softinklab.authserver.controller;

import com.softinklab.authserver.rest.BaseResponse;
import com.softinklab.authserver.rest.request.RegistrationRequest;
import com.softinklab.authserver.rest.request.ValidateEmailRequest;
import com.softinklab.authserver.rest.response.RegistrationResponse;
import com.softinklab.authserver.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/authentication")
public class RegisterController {

    private final RegistrationService registrationService;

    public RegisterController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/register", produces = "application/json")
    public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegistrationRequest payload) {
        return ResponseEntity.ok(this.registrationService.register(payload));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/validate-email", produces = "application/json")
    public ResponseEntity<BaseResponse> validateEmail(@Valid @RequestBody ValidateEmailRequest payload) {
        return ResponseEntity.ok(this.registrationService.validateEmail(payload));
    }
}
