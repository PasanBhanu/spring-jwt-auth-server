package com.softinklab.authserver.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ErrorResponse extends BaseResponse {

    public ErrorResponse(Integer status, String message, List<String> errors) {
        this.setStatus(status);
        this.setMessage(message);
        this.errors = errors;
    }

    private List<String> errors = new ArrayList<>();
}
