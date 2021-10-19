package com.softinklab.rest.response.validation;

import com.softinklab.rest.response.ErrorResponse;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ValidationErrorResponse extends ErrorResponse {
    public ValidationErrorResponse(Integer status, String message, ArrayList<ValidationError> validationErrors) {
        this.setStatus(status);
        this.setMessage(message);
        this.validationErrors = validationErrors;
    }

    private ArrayList<ValidationError> validationErrors;
}
