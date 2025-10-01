package com.solarize.solarize_web_backend.shared.exceptions;

import org.springframework.http.HttpStatus;

public class InternalServerError extends BaseException {
    public InternalServerError() {
        super("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
