package com.project.stock_exchange.util.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class RestException {
    private HttpStatus status;
    private String message;

    public RestException() {};

    public RestException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}