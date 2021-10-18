package com.softinklab.authserver.rest.response;

import com.softinklab.authserver.rest.BaseResponse;
import lombok.Data;

@Data
public class LoginResponse extends BaseResponse {
    private String accessToken;
    private String rememberToken;
    private String userData;
}
