package com.softinklab.authentication.service;

import com.softinklab.authentication.rest.request.ChangePasswordRequest;
import com.softinklab.authentication.rest.request.ConfirmPasswordRequest;
import com.softinklab.authentication.rest.request.ForceChangePasswordRequest;
import com.softinklab.authentication.rest.request.ProfileUpdateRequest;
import com.softinklab.authentication.rest.response.ProfileResponse;
import com.softinklab.rest.response.BaseResponse;

public interface ProfileService {
    ProfileResponse getProfile();

    ProfileResponse updateProfile(ProfileUpdateRequest payload);

    BaseResponse changePassword(ChangePasswordRequest payload);

    BaseResponse forceChangePassword(ForceChangePasswordRequest payload);

    BaseResponse confirmPassword(ConfirmPasswordRequest payload);
}
