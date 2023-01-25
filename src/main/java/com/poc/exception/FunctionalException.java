package com.poc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FunctionalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FunctionalException(String message) {
        super(message);
    }
}
