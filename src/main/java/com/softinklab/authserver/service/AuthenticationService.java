package com.softinklab.authserver.service;

import com.softinklab.authserver.rest.request.LoginRequest;
import com.softinklab.authserver.rest.response.LoginResponse;
import org.springframework.stereotype.Service;

public interface AuthenticationService {
    LoginResponse login(LoginRequest payload);
}
