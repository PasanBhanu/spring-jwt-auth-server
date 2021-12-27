package com.softinklab.app.service;

import com.softinklab.app.rest.request.ForgetPasswordRequest;
import com.softinklab.app.rest.request.ResetForgetPasswordRequest;
import com.softinklab.rest.response.BaseResponse;

public interface PasswordResetService {
    BaseResponse forgetPassword(ForgetPasswordRequest payload);

    BaseResponse resetForgetPassword(ResetForgetPasswordRequest payload);
}
