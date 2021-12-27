package com.softinklab.authentication.service;

import com.softinklab.authentication.rest.request.ForgetPasswordRequest;
import com.softinklab.authentication.rest.request.ResetForgetPasswordRequest;
import com.softinklab.rest.response.BaseResponse;

public interface PasswordResetService {
    BaseResponse forgetPassword(ForgetPasswordRequest payload);

    BaseResponse resetForgetPassword(ResetForgetPasswordRequest payload);
}
