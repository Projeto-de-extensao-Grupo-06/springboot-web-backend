package com.solarize.solarizeWebBackend.shared.exceptions;

import org.springframework.http.HttpStatus;

public class ServerErrorException extends BaseException {

    public ServerErrorException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
