package com.softinklab.authserver.rest.response;

import com.softinklab.authserver.rest.BaseResponse;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Data
public class LoginResponse extends BaseResponse {
    private String accessToken;
    private String rememberToken;
    private Map<String, Object> userData;
}
