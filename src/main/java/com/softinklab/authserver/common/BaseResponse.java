package com.softinklab.authserver.common;

import lombok.Data;

import java.util.HashMap;

@Data
public class BaseResponse {
    private Integer status = 501;
    private String message = "";
}
