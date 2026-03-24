package com.cognizant.agriserve.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// This tells Spring to return a 400 BAD REQUEST status
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ApiException extends RuntimeException {

    @Getter
    private final HttpStatus status;
    private final String message;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage() { return message; }
}