package com.softinklab.authentication.controller;


import com.softinklab.authentication.rest.request.LoginRequest;
import com.softinklab.authentication.rest.response.LoginResponse;
import com.softinklab.authentication.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest payload) {
        return ResponseEntity.ok(this.authenticationService.login(payload));
    }
}
