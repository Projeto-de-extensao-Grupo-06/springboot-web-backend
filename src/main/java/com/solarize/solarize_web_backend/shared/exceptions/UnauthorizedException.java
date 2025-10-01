package com.solarize.solarize_web_backend.shared.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException(String message, HttpStatus status) {
        super(message, status);
    }
}
