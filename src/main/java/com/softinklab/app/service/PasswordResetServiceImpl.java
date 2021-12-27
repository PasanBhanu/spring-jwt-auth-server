package com.softinklab.app.service;

import com.softinklab.app.database.model.AutUser;
import com.softinklab.app.database.repository.UserRepository;
import com.softinklab.app.rest.request.ForgetPasswordRequest;
import com.softinklab.app.rest.request.ResetForgetPasswordRequest;
import com.softinklab.notification.model.DefaultEmail;
import com.softinklab.notification.model.Email;
import com.softinklab.notification.service.NotificationService;
import com.softinklab.rest.exception.DatabaseValidationException;
import com.softinklab.rest.response.BaseResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Value("${application.url}")
    private String applicationUrl;

    public PasswordResetServiceImpl(UserRepository userRepository, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Override
    public BaseResponse forgetPassword(ForgetPasswordRequest payload) {
        Optional<AutUser> user = this.userRepository.findByUsernameEqualsIgnoreCase(payload.getUsername());
        if (user.isPresent()) {
            user.get().setPasswordResetToken(UUID.randomUUID().toString());
            this.userRepository.save(user.get());
            this.notificationService.sendEmail(generatePasswordResetEmail(user.get()));

            return new BaseResponse(200, "Password reset email successfully sent.");
        }
        throw new DatabaseValidationException(401, HttpStatus.NOT_FOUND, "Username not found. Please check your username and try.");
    }

    @Override
    public BaseResponse resetForgetPassword(ResetForgetPasswordRequest payload) {
        return  new BaseResponse();
    }

    private Email generatePasswordResetEmail(AutUser user) {
        DefaultEmail email = new DefaultEmail();
        email.initialise(this.notificationService.getEmailConfig());
        email.setTo(user.getUsername());
        email.setSubject("Reset Your Password");
        email.setGreeting("Hi " + user.getFirstName());
        email.setButtonText("Reset My Password");
        email.setButtonUrl(this.applicationUrl + "/reset-forget-password/" + user.getPasswordResetToken());
        email.setPreParagraphs(Collections.singletonList("We have received your request to reset your password. Please click the link below to complete the reset."));
        email.setPostParagraphs(Collections.singletonList("If that doesn't work, copy and paste the following link in your browser. <br>" + this.applicationUrl + "/reset-forget-password/" + user.getPasswordResetToken()));
        return email;
    }
}
