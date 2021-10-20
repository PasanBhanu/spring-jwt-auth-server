package com.softinklab.authentication.controller;


import com.softinklab.authentication.config.TokenConfig;
import com.softinklab.authentication.rest.request.LoginRequest;
import com.softinklab.authentication.rest.request.TokenRefreshRequest;
import com.softinklab.authentication.rest.response.LoginResponse;
import com.softinklab.authentication.rest.response.TokenRefreshResponse;
import com.softinklab.authentication.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/authentication")
public class LoginController {

    private final AuthenticationService authenticationService;
    private final TokenConfig tokenConfig;

    public LoginController(AuthenticationService authenticationService, TokenConfig tokenConfig) {
        this.authenticationService = authenticationService;
        this.tokenConfig = tokenConfig;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/login", produces = "application/json")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest payload, HttpServletResponse response) {
        LoginResponse loginResponse = this.authenticationService.login(payload);
        Cookie cookie = new Cookie("session", loginResponse.getRememberToken());
        cookie.setMaxAge(this.tokenConfig.getCookieMaxDays() * 24 * 60 * 60);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath(this.tokenConfig.getPath());
        cookie.setDomain(this.tokenConfig.getDomain());
        response.addCookie(cookie);
        return ResponseEntity.ok(loginResponse);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/refresh", produces = "application/json")
    public ResponseEntity<TokenRefreshResponse> refresh(@Valid @RequestBody TokenRefreshRequest payload) {
        return ResponseEntity.ok(this.authenticationService.refresh(payload));
    }
}
