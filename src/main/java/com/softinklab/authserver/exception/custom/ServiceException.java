package com.softinklab.authserver.exception.custom;

import com.softinklab.authserver.rest.validation.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceException extends RuntimeException{
    private Integer status = 500;
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    private String message = "Internal server error";

    private ArrayList<String> errors = new ArrayList<>();
}