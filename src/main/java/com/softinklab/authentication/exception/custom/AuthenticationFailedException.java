package com.softinklab.authentication.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationFailedException extends RuntimeException {
    private Integer status = 401;
    private HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
    private String message = "Unauthorised";
}
