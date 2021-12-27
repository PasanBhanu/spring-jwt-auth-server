package com.softinklab.app.service;

import com.softinklab.app.rest.request.LoginRequest;
import com.softinklab.app.rest.request.LogoutRequest;
import com.softinklab.app.rest.request.TokenRefreshRequest;
import com.softinklab.app.rest.response.LoginResponse;
import com.softinklab.app.rest.response.TokenRefreshResponse;
import com.softinklab.rest.response.BaseResponse;

public interface AuthenticationService {
    LoginResponse login(LoginRequest payload);

    TokenRefreshResponse refresh(TokenRefreshRequest payload);

    BaseResponse logout(LogoutRequest payload);
}
