package com.softinklab.rest.response.validation;

import com.softinklab.rest.response.ErrorResponse;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ValidationErrorResponse extends ErrorResponse {
    public ValidationErrorResponse(Integer status, String message, ArrayList<ValidationError> validationErrors, String actionCode) {
        this.setStatus(status);
        this.setMessage(message);
        this.validationErrors = validationErrors;
        this.actionCode = actionCode;
    }

    private ArrayList<ValidationError> validationErrors;
}
