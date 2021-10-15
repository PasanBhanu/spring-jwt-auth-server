package com.softinklab.authserver.service;

import com.softinklab.authserver.database.model.AutUser;
import com.softinklab.authserver.database.repository.UserRepository;
import com.softinklab.authserver.exception.custom.DatabaseValidationException;
import com.softinklab.authserver.rest.request.RegistrationRequest;
import com.softinklab.authserver.rest.response.RegistrationResponse;
import com.softinklab.authserver.rest.validation.ValidationError;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

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
            throw new DatabaseValidationException(400, "User already exists for this email", validationErrors);
        }

        AutUser newUser = new AutUser();
        newUser.setUsername(payload.getUsername());
        newUser.setPassword(this.passwordEncoder.encode(payload.getPassword()));
        newUser.setFirstName(payload.getFirstName());
        newUser.setLastName(payload.getLastName());
        newUser = userRepository.save(newUser);

        RegistrationResponse response = new RegistrationResponse();

        return response;
    }
}
