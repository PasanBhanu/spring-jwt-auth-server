package com.softinklab.authentication.service;

import com.softinklab.authentication.config.AuthServerConfig;
import com.softinklab.authentication.database.model.AutJwtApp;
import com.softinklab.authentication.database.model.AutSession;
import com.softinklab.authentication.database.model.AutUser;
import com.softinklab.authentication.database.repository.JwtAppRepository;
import com.softinklab.authentication.database.repository.UserRepository;
import com.softinklab.rest.action.Authentication;
import com.softinklab.rest.exception.DatabaseValidationException;
import com.softinklab.authentication.rest.request.LoginRequest;
import com.softinklab.authentication.rest.response.LoginResponse;
import com.softinklab.rest.exception.LogicViolationException;
import com.softinklab.rest.response.validation.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtAppRepository jwtAppRepository;
    private final TokenProvider tokenProvider;
    private final AuthServerConfig authServerConfig;

    public AuthenticationServiceImpl(UserRepository userRepository, JwtAppRepository jwtAppRepository, TokenProvider tokenProvider, AuthServerConfig authServerConfig) {
        this.userRepository = userRepository;
        this.jwtAppRepository = jwtAppRepository;
        this.tokenProvider = tokenProvider;
        this.authServerConfig = authServerConfig;
    }

    @Override
    public LoginResponse login(LoginRequest payload) {
        Optional<AutJwtApp> jwtApp = this.jwtAppRepository.findById(payload.getAppId());
        if (!jwtApp.isPresent()) {
            throw new DatabaseValidationException(401, HttpStatus.UNAUTHORIZED, "Invalid login attempt.");
        }

        Optional<AutUser> user = this.userRepository.findByUsernameEqualsIgnoreCaseAndPassword(payload.getUsername(), payload.getPassword());
        if (!user.isPresent()) {
            ArrayList<ValidationError> errors = new ArrayList<>();
            errors.add(new ValidationError("username", "Username or Password invalid"));
            throw new DatabaseValidationException(401, HttpStatus.UNAUTHORIZED, "Username or Password invalid. Please try again.", errors);
        }

        AutUser loggedInUser = user.get();
        if (this.authServerConfig.getLogin().getVerification()) {
            if (loggedInUser.getEmailConfirmedAt() == null) {
                throw new LogicViolationException(400, HttpStatus.BAD_REQUEST, "Email verification required. Please check your inbox for verification email.", Authentication.RESEND_VERIFICATION_EMAIL);
            }
        }

        String jwtToken = this.tokenProvider.generateJwtToken(loggedInUser, jwtApp.get(), payload.getRememberMe());
        String cipheredToken = this.tokenProvider.cipherToken(jwtToken);

        AutSession session = createAutSession(payload, jwtApp.get(), loggedInUser, cipheredToken);
        String sessionToken = this.tokenProvider.generateSession(loggedInUser, session);

        LoginResponse response = new LoginResponse();
        response.setAccessToken(cipheredToken);
        response.setRememberToken(sessionToken);
        response.setUserData(getUserData(loggedInUser));
        response.setStatus(200);
        response.setMessage("User authenticated successfully.");

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

    private HashMap<String, Object> getUserData(AutUser user) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", user.getUserId());
        map.put("username", user.getUsername());
        map.put("firstName", user.getFirstName());
        map.put("lastName", user.getLastName());
        map.put("registeredAt", user.getRegisteredAt());
        map.put("emailBlocked", user.getEmailBlocked());
        map.put("smsBlocked", user.getSmsBlocked());
        map.put("roles", user.getRoles());
        map.put("permissions", user.getPermissions());

        return map;
    }
}
