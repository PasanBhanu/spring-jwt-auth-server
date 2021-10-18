package com.softinklab.authserver.service;

import com.softinklab.authserver.database.model.AutJwtApp;
import com.softinklab.authserver.database.model.AutSession;
import com.softinklab.authserver.database.model.AutUser;
import com.softinklab.authserver.database.repository.JwtAppRepository;
import com.softinklab.authserver.database.repository.UserRepository;
import com.softinklab.authserver.exception.custom.DatabaseValidationException;
import com.softinklab.authserver.rest.request.LoginRequest;
import com.softinklab.authserver.rest.response.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtAppRepository jwtAppRepository;
    private final TokenProvider tokenProvider;

    public AuthenticationServiceImpl(UserRepository userRepository, JwtAppRepository jwtAppRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.jwtAppRepository = jwtAppRepository;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public LoginResponse login(LoginRequest payload) {
        Optional<AutJwtApp> jwtApp = this.jwtAppRepository.findById(payload.getAppId());
        if (!jwtApp.isPresent()) {
            throw new DatabaseValidationException(401, HttpStatus.UNAUTHORIZED, "Invalid login attempt");
        }

        Optional<AutUser> user = this.userRepository.findByUsernameEqualsIgnoreCase(payload.getUsername());
        if (!user.isPresent()) {
            throw new DatabaseValidationException(401, HttpStatus.UNAUTHORIZED, "Username not found.");
        }

        AutUser loggedInUser = user.get();

        String jwtToken = this.tokenProvider.generateJwtToken(loggedInUser, payload.getRememberMe());
        String cipheredToken = this.tokenProvider.cipherToken(jwtToken);

        AutSession session = createAutSession(payload, jwtApp.get(), loggedInUser, cipheredToken);
        String sessionToken = this.tokenProvider.generateSession(loggedInUser, session);

        LoginResponse response = new LoginResponse();
        response.setAccessToken(cipheredToken);
        response.setRememberToken(sessionToken);
        response.setUserData("");
        response.setStatus(200);
        response.setMessage("User authenticated");

        return response;
    }

    private AutSession createAutSession(LoginRequest payload, AutJwtApp jwtApp, AutUser loggedInUser, String cipheredToken) {
        AutSession session = new AutSession();
        session.setUserId(loggedInUser);
        session.setBrowser(payload.getBrowser());
        session.setDeviceName(payload.getDeviceName());
        session.setOs(payload.getOs());
        session.setDeviceHash(payload.getDeviceHash());
        session.setRememberMe(payload.getRememberMe());
        session.setAppId(jwtApp);
        session.setToken(cipheredToken);
        return session;
    }
}
