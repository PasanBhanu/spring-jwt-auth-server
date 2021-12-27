package com.softinklab.app.controller;

import com.softinklab.app.rest.request.ForgetPasswordRequest;
import com.softinklab.app.rest.request.ResetForgetPasswordRequest;
import com.softinklab.app.service.PasswordResetService;
import com.softinklab.rest.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/authentication")
public class PasswordController {
    private final PasswordResetService passwordResetService;

    public PasswordController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/forget-password", produces = "application/json")
    public ResponseEntity<BaseResponse> forgetPassword(@Valid @RequestBody ForgetPasswordRequest payload) {
        return ResponseEntity.ok(this.passwordResetService.forgetPassword(payload));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/reset-forget-password", produces = "application/json")
    public ResponseEntity<BaseResponse> resetForgetPassword(@Valid @RequestBody ResetForgetPasswordRequest payload) {
        return ResponseEntity.ok(this.passwordResetService.resetForgetPassword(payload));
    }
}
