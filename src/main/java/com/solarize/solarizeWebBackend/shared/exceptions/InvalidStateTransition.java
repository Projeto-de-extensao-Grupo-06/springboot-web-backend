package com.solarize.solarizeWebBackend.shared.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidStateTransition extends BaseException {
    public InvalidStateTransition(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
