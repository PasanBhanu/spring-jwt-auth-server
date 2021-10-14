package com.softinklab.authserver.rest.response;

import com.softinklab.authserver.common.BaseResponse;
import lombok.Data;

@Data
public class LoginResponse extends BaseResponse {
    private String accessToken;
    private String rememberToken;
}
