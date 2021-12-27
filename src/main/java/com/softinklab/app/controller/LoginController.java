package com.softinklab.app.controller;


import com.softinklab.app.config.TokenConfig;
import com.softinklab.app.rest.request.LoginRequest;
import com.softinklab.app.rest.request.LogoutRequest;
import com.softinklab.app.rest.request.TokenRefreshRequest;
import com.softinklab.app.rest.response.LoginResponse;
import com.softinklab.app.rest.response.TokenRefreshResponse;
import com.softinklab.app.service.AuthenticationService;
import com.softinklab.rest.response.BaseResponse;
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

    @RequestMapping(method = RequestMethod.POST, path = "/logout", produces = "application/json")
    public ResponseEntity<BaseResponse> refresh(@Valid @RequestBody LogoutRequest payload) {
        return ResponseEntity.ok(this.authenticationService.logout(payload));
    }
}
