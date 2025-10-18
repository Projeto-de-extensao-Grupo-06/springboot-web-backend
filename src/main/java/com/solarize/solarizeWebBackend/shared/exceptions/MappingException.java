package com.solarize.solarizeWebBackend.shared.exceptions;

import org.springframework.http.HttpStatus;

public class MappingException extends RuntimeException {
    public MappingException(String message) {
        super(message);
    }
}
