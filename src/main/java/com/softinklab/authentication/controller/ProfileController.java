package com.softinklab.authentication.controller;

import com.softinklab.authentication.rest.request.ChangePasswordRequest;
import com.softinklab.authentication.rest.request.ConfirmPasswordRequest;
import com.softinklab.authentication.rest.request.ForceChangePasswordRequest;
import com.softinklab.authentication.rest.request.ProfileUpdateRequest;
import com.softinklab.authentication.rest.response.ProfileResponse;
import com.softinklab.authentication.service.ProfileService;
import com.softinklab.rest.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/authenticated")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping(path = "/profile", produces = "application/json")
    public ResponseEntity<ProfileResponse> getProfile() {
        return ResponseEntity.ok(this.profileService.getProfile());
    }

    @PostMapping(path = "/profile", produces = "application/json")
    public ResponseEntity<ProfileResponse> updateProfile(@Valid @RequestBody ProfileUpdateRequest payload) {
        return ResponseEntity.ok(this.profileService.updateProfile(payload));
    }

    @PostMapping(path = "/change-password", produces = "application/json")
    public ResponseEntity<BaseResponse> changePassword(@Valid @RequestBody ChangePasswordRequest payload) {
        return ResponseEntity.ok(this.profileService.changePassword(payload));
    }

    @PostMapping(path = "/force-change-password", produces = "application/json")
    public ResponseEntity<BaseResponse> forceChangePassword(@Valid @RequestBody ForceChangePasswordRequest payload) {
        return ResponseEntity.ok(this.profileService.forceChangePassword(payload));
    }

    @PostMapping(path = "/confirm-password", produces = "application/json")
    public ResponseEntity<BaseResponse> confirmPassword(@Valid @RequestBody ConfirmPasswordRequest payload) {
        return ResponseEntity.ok(this.profileService.confirmPassword(payload));
    }
}
