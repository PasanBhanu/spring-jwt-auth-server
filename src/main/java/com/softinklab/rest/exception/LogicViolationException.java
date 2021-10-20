package com.softinklab.rest.exception;

import com.softinklab.rest.action.Action;
import com.softinklab.rest.action.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogicViolationException extends RuntimeException{
    private Integer status = 406;
    private HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
    private String message = "Internal server error";
    private List<String> errors = null;
    private Action actionCode = Default.NULL;

    public LogicViolationException(Integer status, HttpStatus httpStatus, String message, List<String> errors) {
        this.status = status;
        this.httpStatus = httpStatus;
        this.message = message;
        this.errors = errors;
    }

    public LogicViolationException(Integer status, HttpStatus httpStatus, String message, Action actionCode) {
        this.status = status;
        this.httpStatus = httpStatus;
        this.message = message;
        this.actionCode = actionCode;
    }
}
