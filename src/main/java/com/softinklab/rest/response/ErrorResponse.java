package com.softinklab.rest.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ErrorResponse extends BaseResponse {
    protected String actionCode = null;
    private List<String> errors = null;

    public ErrorResponse(Integer status, String message, List<String> errors, String actionCode) {
        this.setStatus(status);
        this.setMessage(message);
        this.errors = errors;
        this.actionCode = actionCode;
    }

    public ErrorResponse(Integer status, String message, List<String> errors) {
        this.setStatus(status);
        this.setMessage(message);
        this.errors = errors;
    }

    public ErrorResponse(Integer status, String message) {
        this.setStatus(status);
        this.setMessage(message);
        this.errors = errors;
    }
}
