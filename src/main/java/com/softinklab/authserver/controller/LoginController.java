package com.softinklab.authserver.controller;


import com.softinklab.authserver.rest.request.LoginRequest;
import com.softinklab.authserver.rest.response.LoginResponse;
import com.softinklab.authserver.service.AuthenticationService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/authentication")
public class LoginController {

    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/login", produces = "application/json")
    public LoginResponse login(@Valid @RequestBody LoginRequest payload) {
        return this.authenticationService.login(payload);
    }
}
