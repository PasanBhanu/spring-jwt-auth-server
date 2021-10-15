package com.softinklab.authserver.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseValidationException extends RuntimeException{
    private Integer status = 400;
    private String message = "Database validation failed";
    private ArrayList<com.softinklab.authserver.rest.validation.ValidationError> validationErrors;
}
