package com.softinklab.app.rest.response;

import com.softinklab.rest.response.BaseResponse;
import lombok.Data;

import java.util.Map;

@Data
public class LoginResponse extends BaseResponse {
    private String accessToken;
    private String rememberToken;
    private Map<String, Object> userData;
}
