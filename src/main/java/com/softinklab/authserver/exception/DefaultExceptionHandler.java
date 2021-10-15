package com.softinklab.authserver.exception;

import com.softinklab.authserver.rest.ErrorResponse;
import com.softinklab.authserver.rest.validation.ValidationError;
import com.softinklab.authserver.rest.validation.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected <T extends ErrorResponse, E extends MethodArgumentNotValidException> ResponseEntity<T> handleValidationExceptions(E ex) {
        log.info("Request validation failed");

        ArrayList<ValidationError> validationErrors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            validationErrors.add(new ValidationError(((FieldError) error).getField(), error.getDefaultMessage()));
        });

        ValidationErrorResponse response = new ValidationErrorResponse(400, "Request validation failed", validationErrors);
        return new ResponseEntity<T>((T) response, HttpStatus.BAD_REQUEST);
    }
}
