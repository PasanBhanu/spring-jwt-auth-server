package com.softinklab.authserver.common;

import lombok.Data;

import java.util.HashMap;

@Data
public class ErrorResponse extends BaseResponse{
    private HashMap<String, String> errors = new HashMap<>();
}
