package com.softinklab.authentication.service;

import com.softinklab.authentication.config.AuthServerConfig;
import com.softinklab.authentication.database.model.AutUser;
import com.softinklab.authentication.database.repository.UserRepository;
import com.softinklab.notification.model.DefaultEmail;
import com.softinklab.notification.model.Email;
import com.softinklab.notification.service.NotificationService;
import com.softinklab.rest.exception.DatabaseValidationException;
import com.softinklab.rest.response.BaseResponse;
import com.softinklab.authentication.rest.request.RegistrationRequest;
import com.softinklab.authentication.rest.request.ValidateEmailRequest;
import com.softinklab.authentication.rest.response.RegistrationResponse;
import com.softinklab.rest.response.validation.ValidationError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthServerConfig authServerConfig;
    private final NotificationService notificationService;

    @Value("${application.url}")
    private String applicationUrl;

    public RegistrationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthServerConfig authServerConfig, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authServerConfig = authServerConfig;
        this.notificationService = notificationService;
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
            this.notificationService.sendEmail(generateValidationEmail(newUser));
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

    private Email generateValidationEmail(AutUser user) {
        DefaultEmail email = new DefaultEmail();
        email.initialise(this.notificationService.getEmailConfig());
        email.setTo(user.getUsername());
        email.setSubject("Verify Your Email");
        email.setGreeting("Hi " + user.getFirstName());
        email.setButtonText("Verify My Email");
        email.setButtonUrl(this.applicationUrl + "/verify-email/" + user.getConfirmationToken());
        email.setPreParagraphs(Collections.singletonList("We excited to have you get started. First, you need to verify your email address. Just press the button below."));
        email.setPostParagraphs(Collections.singletonList("If that doesn't work, copy and paste the following link in your browser. <br>" + this.applicationUrl + "/verify-email/" + user.getConfirmationToken()));
        return email;
    }
}
