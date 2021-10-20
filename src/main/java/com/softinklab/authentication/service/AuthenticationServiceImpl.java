package com.softinklab.authentication.service;

import com.softinklab.authentication.config.AuthServerConfig;
import com.softinklab.authentication.database.model.AutJwtApp;
import com.softinklab.authentication.database.model.AutSession;
import com.softinklab.authentication.database.model.AutUser;
import com.softinklab.authentication.database.repository.JwtAppRepository;
import com.softinklab.authentication.database.repository.SessionRepository;
import com.softinklab.authentication.database.repository.UserRepository;
import com.softinklab.authentication.exception.custom.AuthenticationFailedException;
import com.softinklab.authentication.model.UserPrincipal;
import com.softinklab.authentication.rest.request.LoginRequest;
import com.softinklab.authentication.rest.request.TokenRefreshRequest;
import com.softinklab.authentication.rest.response.LoginResponse;
import com.softinklab.authentication.rest.response.TokenRefreshResponse;
import com.softinklab.rest.action.Authentication;
import com.softinklab.rest.exception.DatabaseValidationException;
import com.softinklab.rest.exception.LogicViolationException;
import com.softinklab.rest.response.validation.ValidationError;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtAppRepository jwtAppRepository;
    private final TokenProvider tokenProvider;
    private final AuthServerConfig authServerConfig;
    private final PasswordEncoder passwordEncoder;
    private final SessionRepository sessionRepository;

    public AuthenticationServiceImpl(UserRepository userRepository, JwtAppRepository jwtAppRepository, TokenProvider tokenProvider, AuthServerConfig authServerConfig, PasswordEncoder passwordEncoder, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.jwtAppRepository = jwtAppRepository;
        this.tokenProvider = tokenProvider;
        this.authServerConfig = authServerConfig;
        this.passwordEncoder = passwordEncoder;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public LoginResponse login(LoginRequest payload) {
        Optional<AutJwtApp> jwtApp = this.jwtAppRepository.findById(payload.getAppId());
        if (!jwtApp.isPresent()) {
            throw new DatabaseValidationException(401, HttpStatus.UNAUTHORIZED, "Invalid login attempt.");
        }

        Optional<AutUser> user = this.userRepository.findByUsernameEqualsIgnoreCase(payload.getUsername());
        if (!user.isPresent()) {
            ArrayList<ValidationError> errors = new ArrayList<>();
            errors.add(new ValidationError("username", "Username or Password invalid"));
            throw new DatabaseValidationException(401, HttpStatus.UNAUTHORIZED, "Username or Password invalid. Please try again.", errors);
        }

        AutUser loggedInUser = user.get();
        if (!this.passwordEncoder.matches(payload.getPassword(), loggedInUser.getPassword())) {
            ArrayList<ValidationError> errors = new ArrayList<>();
            errors.add(new ValidationError("username", "Username or Password invalid"));
            throw new DatabaseValidationException(401, HttpStatus.UNAUTHORIZED, "Username or Password invalid. Please try again.", errors);
        }

        if (this.authServerConfig.getLogin().getVerification()) {
            if (loggedInUser.getEmailConfirmedAt() == null) {
                throw new LogicViolationException(400, HttpStatus.BAD_REQUEST, "Email verification required. Please check your inbox for verification email.", Authentication.RESEND_VERIFICATION_EMAIL);
            }
        }

        String jwtToken = this.tokenProvider.generateJwtToken(loggedInUser, jwtApp.get(), payload.getRememberMe());

        AutSession session = createAutSession(payload, jwtApp.get(), loggedInUser, jwtToken);
        String rememberToken = this.tokenProvider.generateSession(loggedInUser, session);

        LoginResponse response = new LoginResponse();
        response.setAccessToken(jwtToken);
        response.setRememberToken(rememberToken);
        response.setUserData(getUserData(loggedInUser));
        response.setStatus(200);
        response.setMessage("User authenticated successfully.");

        return response;
    }

    public TokenRefreshResponse refresh(TokenRefreshRequest payload) {
        Jws<Claims> claims = this.tokenProvider.validateToken(payload.getToken());
        UserPrincipal user = this.tokenProvider.getUserPrincipalFromClaims(claims);

        String decipherRememberToken = this.tokenProvider.decipherToken(payload.getRememberToken());
        String[] tokenContent = decipherRememberToken.split(":");
        if (tokenContent.length != 2){
            throw new AuthenticationFailedException(401, HttpStatus.UNAUTHORIZED, "Remember token not recognised. Invalid refresh attempt.");
        }
        String userIdFromRememberToken = tokenContent[1];

        if (Integer.parseInt(userIdFromRememberToken) != user.getUserId()) {
            throw new AuthenticationFailedException(401, HttpStatus.UNAUTHORIZED, "Remember token not recognised. Invalid login attempt.");
        }
        Optional<AutSession> session = this.sessionRepository.findByUserId_UserIdAndRememberTokenAndTokenAndDeviceHash(user.getUserId(), decipherRememberToken, payload.getToken(), payload.getDeviceHash());
        if (!session.isPresent()) {
            throw new DatabaseValidationException(401, HttpStatus.UNAUTHORIZED, "Invalid session! User already logged out. Please login again.", Authentication.LOGIN);
        }

        String jwtToken = this.tokenProvider.generateJwtToken(session.get().getUserId(), session.get().getAppId(), session.get().getRememberMe());

        session.get().setToken(jwtToken);
        this.sessionRepository.save(session.get());

        return new TokenRefreshResponse(jwtToken);
    }

    private AutSession createAutSession(LoginRequest payload, AutJwtApp jwtApp, AutUser loggedInUser, String jwtToken) {
        AutSession session = new AutSession();
        session.setUserId(loggedInUser);
        session.setBrowser(payload.getBrowser());
        session.setDeviceName(payload.getDeviceName());
        session.setOs(payload.getOs());
        session.setDeviceHash(payload.getDeviceHash());
        session.setRememberMe(payload.getRememberMe());
        session.setAppId(jwtApp);
        session.setToken(jwtToken);
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
