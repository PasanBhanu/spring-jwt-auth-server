package com.softinklab.authserver.service;

import com.softinklab.authserver.config.AuthServerConfig;
import com.softinklab.authserver.database.model.AutUser;
import com.softinklab.authserver.database.repository.UserRepository;
import com.softinklab.authserver.exception.custom.DatabaseValidationException;
import com.softinklab.authserver.rest.BaseResponse;
import com.softinklab.authserver.rest.request.RegistrationRequest;
import com.softinklab.authserver.rest.request.ValidateEmailRequest;
import com.softinklab.authserver.rest.response.RegistrationResponse;
import com.softinklab.authserver.rest.validation.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthServerConfig authServerConfig;

    public RegistrationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthServerConfig authServerConfig) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authServerConfig = authServerConfig;
    }

    @Override
    public RegistrationResponse register(RegistrationRequest payload) {
        Optional<AutUser> user = this.userRepository.findByUsernameEqualsIgnoreCase(payload.getUsername());
        if (user.isPresent()) {
            ArrayList<ValidationError> validationErrors = new ArrayList<>();
            validationErrors.add(new ValidationError("username", "Username already taken"));
            throw new DatabaseValidationException(400, HttpStatus.BAD_REQUEST, "User already exists for this email.", validationErrors);
        }

        AutUser newUser = new AutUser();
        newUser.setUsername(payload.getUsername());
        newUser.setPassword(this.passwordEncoder.encode(payload.getPassword()));
        newUser.setFirstName(payload.getFirstName());
        newUser.setLastName(payload.getLastName());
        newUser.setConfirmationToken(UUID.randomUUID().toString());
        if (this.authServerConfig.getRegistration().getVerification()) {
            newUser.setEmailConfirmedAt(new Date());
        }
        newUser = userRepository.save(newUser);

        RegistrationResponse response = new RegistrationResponse();
        response.setUserId(newUser.getUserId());
        response.setUsername(newUser.getUsername());
        response.setRegistrationSuccess(true);
        if (this.authServerConfig.getRegistration().getVerification()) {
            response.setVerificationRequired(true);
        } else {
            response.setVerificationRequired(false);
        }

        return response;
    }

    @Override
    public BaseResponse validateEmail(ValidateEmailRequest payload) {
        Optional<AutUser> user = this.userRepository.findByConfirmationToken(payload.getToken());
        if (user.isPresent()) {
            if (user.get().getEmailConfirmedAt() == null) {
                user.get().setEmailConfirmedAt(new Date());
                userRepository.save(user.get());

                return new BaseResponse(200, "Email successfully validated.");
            }

            throw new DatabaseValidationException(400, HttpStatus.BAD_REQUEST, "User already confirmed the email");
        }
        throw new DatabaseValidationException(401, HttpStatus.NOT_FOUND, "Invalid validation link. Please check your email and click on the newest validation mail link.");
    }
}
