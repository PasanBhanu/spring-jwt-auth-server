package com.softinklab.authserver.service;

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

    public RegistrationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        newUser = userRepository.save(newUser);

        RegistrationResponse response = new RegistrationResponse();

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
