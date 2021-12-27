package com.softinklab.authentication.service;

import com.softinklab.authentication.rest.request.LoginRequest;
import com.softinklab.authentication.rest.request.LogoutRequest;
import com.softinklab.authentication.rest.request.TokenRefreshRequest;
import com.softinklab.authentication.rest.response.LoginResponse;
import com.softinklab.authentication.rest.response.TokenRefreshResponse;
import com.softinklab.rest.response.BaseResponse;

public interface AuthenticationService {
    LoginResponse login(LoginRequest payload);

    TokenRefreshResponse refresh(TokenRefreshRequest payload);

    BaseResponse logout(LogoutRequest payload);
}
