package com.softinklab.authserver.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class ErrorResponse extends BaseResponse {

    public ErrorResponse(Integer status, String message, ArrayList<String> errors) {
        this.setStatus(status);
        this.setMessage(message);
        this.errors = errors;
    }

    private ArrayList<String> errors = new ArrayList<>();
}
