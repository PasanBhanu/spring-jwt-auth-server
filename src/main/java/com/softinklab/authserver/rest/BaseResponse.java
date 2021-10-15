package com.softinklab.authserver.rest;

import lombok.Data;

import java.util.HashMap;

@Data
public class BaseResponse {
    private Integer status = 501;
    private String message = "";
}
