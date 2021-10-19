package com.softinklab.authentication.service;

import com.softinklab.authentication.rest.request.LoginRequest;
import com.softinklab.authentication.rest.response.LoginResponse;

public interface AuthenticationService {
    LoginResponse login(LoginRequest payload);
}
