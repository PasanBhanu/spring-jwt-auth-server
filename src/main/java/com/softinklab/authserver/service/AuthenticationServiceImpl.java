package com.softinklab.authserver.service;

import com.softinklab.authserver.rest.request.LoginRequest;
import com.softinklab.authserver.rest.response.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public LoginResponse login(LoginRequest payload) {
        return null;
    }
}
