package com.softinklab.authentication.service;

import com.softinklab.authentication.rest.request.LoginRequest;
import com.softinklab.authentication.rest.request.TokenRefreshRequest;
import com.softinklab.authentication.rest.response.LoginResponse;
import com.softinklab.authentication.rest.response.TokenRefreshResponse;

public interface AuthenticationService {
    LoginResponse login(LoginRequest payload);

    TokenRefreshResponse refresh(TokenRefreshRequest payload);
}
