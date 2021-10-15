package com.softinklab.authserver.service;

import com.softinklab.authserver.rest.request.RegistrationRequest;
import com.softinklab.authserver.rest.response.RegistrationResponse;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService{

    @Override
    public RegistrationResponse register(RegistrationRequest payload) {
        return null;
    }
}
