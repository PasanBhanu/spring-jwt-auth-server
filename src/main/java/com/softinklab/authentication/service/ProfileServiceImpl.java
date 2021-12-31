package com.softinklab.authentication.service;

import com.softinklab.authentication.database.model.AutUser;
import com.softinklab.authentication.database.repository.UserRepository;
import com.softinklab.authentication.model.UserPrincipal;
import com.softinklab.authentication.rest.request.ChangePasswordRequest;
import com.softinklab.authentication.rest.request.ConfirmPasswordRequest;
import com.softinklab.authentication.rest.request.ForceChangePasswordRequest;
import com.softinklab.authentication.rest.request.ProfileUpdateRequest;
import com.softinklab.authentication.rest.response.ProfileResponse;
import com.softinklab.rest.exception.DatabaseValidationException;
import com.softinklab.rest.response.BaseResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;
    private final UserService userService;

    public ProfileServiceImpl(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public ProfileResponse getProfile() {
        UserPrincipal userPrincipal = this.userService.getAuthenticatedUser();
        Optional<AutUser> user = this.userRepository.findById(userPrincipal.getUserId());
        if (user.isPresent()) {
            ProfileResponse response = this.setProfileResponse(user.get());
            response.setStatus(200);
            response.setMessage("User retrieved successfully");
            return response;
        }
        throw new DatabaseValidationException(404, HttpStatus.NOT_FOUND, "User not found");
    }

    @Override
    public ProfileResponse updateProfile(ProfileUpdateRequest payload) {
        UserPrincipal userPrincipal = this.userService.getAuthenticatedUser();
        Optional<AutUser> user = this.userRepository.findById(userPrincipal.getUserId());
        if (user.isPresent()) {
            AutUser updatedUser = user.get();
            updatedUser.setFirstName(payload.getFirstName());
            updatedUser.setLastName(payload.getLastName());
            updatedUser.setAddressLine1(payload.getAddressLine1());
            updatedUser.setAddressLine2(payload.getAddressLine2());
            updatedUser.setCity(payload.getCity());
            updatedUser.setState(payload.getState());
            updatedUser.setCountry(payload.getCountry());
            updatedUser.setPostalCode(payload.getPostalCode());
            updatedUser.setMobileNo(payload.getMobileNo());
            updatedUser.setSex(payload.getSex());
            updatedUser.setPrefix(payload.getPrefix());
            updatedUser.setVatNo(payload.getVatNo());
            updatedUser.setNicNo(payload.getNicNo());
            updatedUser.setCompanyName(payload.getCompanyName());
            this.userRepository.save(updatedUser);

            ProfileResponse response = this.setProfileResponse(updatedUser);
            response.setStatus(200);
            response.setMessage("User retrieved successfully");
            return response;
        }
        throw new DatabaseValidationException(404, HttpStatus.NOT_FOUND, "User not found");
    }

    @Override
    public BaseResponse changePassword(ChangePasswordRequest payload) {
        return null;
    }

    @Override
    public BaseResponse forceChangePassword(ForceChangePasswordRequest payload) {
        return null;
    }

    @Override
    public BaseResponse confirmPassword(ConfirmPasswordRequest payload) {
        return null;
    }

    private ProfileResponse setProfileResponse(AutUser user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, ProfileResponse.class);
    }
}
