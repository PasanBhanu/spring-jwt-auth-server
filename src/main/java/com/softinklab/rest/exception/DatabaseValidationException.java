package com.softinklab.rest.exception;

import com.softinklab.rest.response.validation.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseValidationException extends RuntimeException {
    private Integer status = 400;
    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    private String message = "Database validation failed";
    private ArrayList<ValidationError> validationErrors = new ArrayList<>();

    public DatabaseValidationException(Integer status, HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.status = status;
        this.message = message;
    }
}
