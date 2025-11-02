package com.solarize.solarizeWebBackend.shared.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidDocumentException extends BaseException {
    public InvalidDocumentException(String message) {

        super(message, HttpStatus.BAD_REQUEST);
    }
}
